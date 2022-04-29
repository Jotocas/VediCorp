package com.torresj.cliente.vedicorp.view.activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.utils.UBigDecimal;
import com.torresj.cliente.vedicorp.utils.UString;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.viewModel.Communication.CarritoCommunication;
import com.torresj.cliente.vedicorp.viewModel.VentasResumenViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ProductoCarritoAdapter;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;
import com.torresj.cliente.vedicorp.viewModel.utils.Carrito;

import java.sql.Date;
import java.sql.Time;


public class ProductosCarritoActivity extends BaseActivity implements CarritoCommunication, ClickListener {
    private RecyclerView rcvBolsaCompras;
    private ProductoCarritoAdapter adapter;
    private VentasResumenViewModel comprasResumenViewModel;
    private ImageView imageView, BtnRegresar;
    private TextView txtvaluesubtotal, txtValueIGV, txtValueTotal, txtNameClientesss;
    private DtoClienteProducto detalles;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_carrito);
        init();
        initViewModel();
        initAdapter();
    }

    private void init() {
        BtnRegresar = (ImageView) findViewById(R.id.BtnRegresar);

        BtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_tv);
                BtnRegresar.startAnimation(animFadein);
                finish();
            }
        });

        rcvBolsaCompras = findViewById(R.id.rcvBolsaCompras);
        imageView = findViewById(R.id.BtnCheck);
        txtvaluesubtotal = findViewById(R.id.Txtvaluesubtotal);
        txtValueIGV = findViewById(R.id.TxtValueIGV);
        txtValueTotal = findViewById(R.id.TxtValueTotal);
        txtNameClientesss = findViewById(R.id.TxtNameClientesss);

        if (UValidador.esListaVacia(Carrito.getDetallePedidos().getDetalle())) {
            toastIncorrecto("¡Ups!, La bolsa de compras está vacia.");
            return;
        } else {
            txtvaluesubtotal.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getSubtotal()).toString());
            txtValueIGV.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getIgv()).toString());
            txtValueTotal.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getTotal()).toString());
            txtNameClientesss.setText(Carrito.getDetallePedidos().getCliente().getNomb());

            imageView.setOnClickListener(v -> {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String pref = preferences.getString("UsuarioJson", "");
                Vendedor u = g.fromJson(pref, Vendedor.class);
                String idC = u.getVen();
                if (!UString.esNuloVacio(idC)) {
                    if (Carrito.getDetallePedidos().getDetalle().isEmpty()) {
                        toastIncorrecto("¡Ups!, La bolsa de compras está vacia.");
                    } else {
                        toastCorrecto("Procesando pedido...");
                        registrarPedido(idC);
                    }
                } else {
                    toastIncorrecto("No ha iniciado sesión, se le redirigirá al login");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }
            });
        }
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.comprasResumenViewModel = vmp.get(VentasResumenViewModel.class);
    }

    private void initAdapter() {
        detalles = Carrito.getDetallePedidos();
        adapter = new ProductoCarritoAdapter(detalles, this, this);
        rcvBolsaCompras.setLayoutManager(new LinearLayoutManager(this));
        rcvBolsaCompras.setAdapter(adapter);
    }

    @Override
    public void eliminarDetalle(String idP) {
        Carrito.eliminar(idP);
        this.adapter.notifyDataSetChanged();
    }

    private void registrarPedido(String idC) {
        DtoClienteProducto Pedido = Carrito.getDetallePedidos();
        Pedido.setVen(idC);
        // java.util.Date date = new java.util.Date();
        // Pedido.set(new Date(date.getTime()));

        this.comprasResumenViewModel.confirmarCompra(Pedido).observe(this, response -> {
            if (response.getRpta() == 1) {
                toastCorrecto("Pedido registrado con éxito");
                Carrito.limpiar();
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            } else {
                toastIncorrecto("Demonios ! , No se pudo registrar el pedido");
            }
        });

    }

    @Override
    public void clickItem(Cliente cliente, View v) {

    }

    @Override
    public void clickProductoItem(PedidoDetalle pedidoDetalle, View v) {
       // toastIncorrecto("Detalle==>" + pedidoDetalle.getCant());

        //detalles.setDetalle();

        detalles.getDetalle().stream()
                .filter((p) -> pedidoDetalle.getCodigo().equals(p.getCodigo()))
                .map(temp -> {
                    temp.setCant(pedidoDetalle.getCant());
                    return temp;
                })
                .findAny().orElse(null);

        Carrito.calcularDesdeActivity(detalles);
        txtvaluesubtotal.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getSubtotal()).toString());
        txtValueIGV.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getIgv()).toString());
        txtValueTotal.setText(UBigDecimal.obtenerValorSinNulo(Carrito.getDetallePedidos().getTotal()).toString());

    }

    @Override
    public void clickBoton(Cliente cliente, View v, String tipo) {

    }
}