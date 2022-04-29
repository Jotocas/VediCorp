package com.torresj.cliente.vedicorp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.DateSerializer;
import com.torresj.cliente.vedicorp.utils.TimeSerializer;
import com.torresj.cliente.vedicorp.utils.UBigDecimal;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.viewModel.VentasResumenViewModel;
import com.torresj.cliente.vedicorp.viewModel.adapter.ProductoCarritoAdapter;
import com.torresj.cliente.vedicorp.viewModel.adapter.VentasResumenAdapter;
import com.torresj.cliente.vedicorp.viewModel.utils.Carrito;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DetalleComprasActivity extends AppCompatActivity {
    private RecyclerView rcvBolsaComprasVentas;
    private VentasResumenAdapter adapter;
    private VentasResumenViewModel ventasResumenViewModel;
    private ImageView btnRegresarVentas;
    private TextView txtNameClientesVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_compras);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init() {
        btnRegresarVentas = (ImageView) findViewById(R.id.BtnRegresarVentas);

        btnRegresarVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_tv);
                btnRegresarVentas.startAnimation(animFadein);
                finish();
            }
        });

        rcvBolsaComprasVentas = findViewById(R.id.rcvBolsaComprasVentas);
        txtNameClientesVentas = findViewById(R.id.TxtNameClientesVentas);

    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.ventasResumenViewModel = vmp.get(VentasResumenViewModel.class);
    }

    private void initAdapter() {
        adapter = new VentasResumenAdapter( new ArrayList<>());
        rcvBolsaComprasVentas.setLayoutManager(new LinearLayoutManager(this));
        rcvBolsaComprasVentas.setAdapter(adapter);
    }

    private void loadData() {
        DtoClienteProducto dto = new DtoClienteProducto();
        dto.setCliente(new Cliente());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if (usuarioJson != null) {
            final Vendedor u = g.fromJson(usuarioJson, Vendedor.class);
            String cliente = getIntent().getStringExtra("cliente");
            dto.setVen(u.getVen());
            dto.getCliente().setNclte(cliente);
            this.ventasResumenViewModel.detallePedidoPorCliente(dto).observe(this, response -> {
                List<DtoPedidoClienteResumen> resumen=response.getBody();
                if(!UValidador.esListaVacia(resumen)){

                   txtNameClientesVentas.setText(resumen.get(0).getClienteNombre());
                }
                 adapter.updateItems(resumen);
            });
        }
    }


}