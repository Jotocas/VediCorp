package com.torresj.cliente.vedicorp.viewModel.adapter;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.DtoTabla;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.UBigDecimal;
import com.torresj.cliente.vedicorp.utils.UString;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;
import com.torresj.cliente.vedicorp.viewModel.utils.Carrito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Producto> productos;
    private List<Producto> originalProductos;
    private Boolean esVenta;
    private Cliente cliente;
    private final LayoutInflater mInflater;
    private Context mContext;//hacer manual

    private View.OnClickListener listener;
    private ClickListener clickListener;

    public ProductoAdapter(List<Producto> productos, Boolean esVenta, Cliente cliente, Context context, ClickListener clickListener) {

        this.productos = productos;
        this.esVenta = esVenta;
        this.cliente = cliente;
        this.clickListener = clickListener;
        this.originalProductos = new ArrayList<>();
        originalProductos.addAll(productos);

        mContext = context;
        mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        if (esVenta) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_venta, parent, false);
            v.setOnClickListener(this);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.productos.get(position));
        holder.imgfoto.setBackgroundResource(R.drawable.box);
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

    public void updateItems(List<Producto> producto) {
        this.productos.clear();
        this.productos.addAll(producto);
        originalProductos.addAll(productos);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public Button btnAgregarCarrito;
        public EditText cantidad;
        public ImageView imgfoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnAgregarCarrito = this.itemView.findViewById(R.id.btnAgregarCarrito);
            this.cantidad = this.itemView.findViewById(R.id.txtValueCantidadVenta);
            imgfoto = itemView.findViewById(R.id.imgArticleProductoVenta);
        }

        public void setItem(final Producto dto) {
            if (esVenta) {
                final TextView txtValueCodProductoVenta = this.itemView.findViewById(R.id.txtValueCodProductoVenta),
                        txtValueDescripcionVenta = this.itemView.findViewById(R.id.txtValueDescripcionVenta),
                        txtValueStockVenta = this.itemView.findViewById(R.id.txtValueStockVenta),
                        txtValuePrecioVenta = this.itemView.findViewById(R.id.txtValuePrecioVenta);

                txtValueCodProductoVenta.setText(dto.getCodigo());
                txtValueDescripcionVenta.setText(dto.getDesc());
                txtValueStockVenta.setText(dto.getStock().toString());
                txtValuePrecioVenta.setText(String.format(Locale.ENGLISH, "S/.%.2f", dto.getPrecio()));

                btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cantidadString = cantidad.getText().toString();

                        if (UString.esNuloVacio(cantidadString)) {
                            cantidadString = "0";
                        }

                        dto.setCantidad(new BigDecimal(cantidadString));

                        if (validarIngresoProducto(dto, v)) {
                            DtoClienteProducto productoAEnviar = new DtoClienteProducto();
                            productoAEnviar.setCliente(cliente);
                            productoAEnviar.setProducto(dto);

                            DtoTabla retorno = Carrito.agregarProducto(productoAEnviar);
                            if ("ERROR".equals(retorno.getCodigo())) {
                                successMessage(retorno.getDescripcion(), retorno.getCodigo());
                            } else {
                                successMessage(retorno.getDescripcion(), retorno.getCodigo());
                                ((EditText) itemView.findViewById(R.id.txtValueCantidadVenta)).setText("");
                                clickListener.clickIncrementarItem(v);
                            }
                        }
                    }
                });
            } else {
                final TextView txtValueCodProducto = this.itemView.findViewById(R.id.txtValueCodProducto),
                        txtValueDescripcion = this.itemView.findViewById(R.id.txtValueDescripcion),
                        txtValueStock = this.itemView.findViewById(R.id.txtValueStock),
                        txtValuePrecio = this.itemView.findViewById(R.id.txtValuePrecio);

                txtValueCodProducto.setText(dto.getCodigo());
                txtValueDescripcion.setText(dto.getDesc());
                txtValueStock.setText(dto.getStock().toString());
                txtValuePrecio.setText(String.format(Locale.ENGLISH, "S/.%.2f", dto.getPrecio()));
            }
        }


        public void successMessage(String message, String tipo) {
            if ("ERROR".equals(tipo)) {
                new SweetAlertDialog(itemView.getContext(),
                        SweetAlertDialog.ERROR_TYPE).setTitleText("Error al Ingresar Items!")
                        .setContentText(message).show();
            } else {
                new SweetAlertDialog(itemView.getContext(),
                        SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                        .setContentText(message).show();
            }
        }
    }

    private Boolean validarIngresoProducto(Producto dto, View v) {
        if (UBigDecimal.esCeroOrNulo(dto.getCantidad())) {
            toastIncorrecto("La cantidad debe ser mayor a 0", v);
            return false;
        } else {
            if (UBigDecimal.esMayorQue(dto.getCantidad(), dto.getStock())) {
                toastIncorrecto("La cantidad solicitada no puede ser mayor a la del Stock", v);
                return false;
            }
        }
        return true;
    }

    public void toastIncorrecto(String msg, View v) {
        LayoutInflater layoutInflater = mInflater;
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) v.findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToast2);
        txtMensaje.setText(msg);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            productos.clear();
            productos.addAll(originalProductos);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                productos.clear();
                List<Producto> collect = originalProductos.stream()
                        .filter(i -> i.getDesc().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                productos.addAll(collect);
            } else {
                productos.clear();
                for (Producto i : originalProductos) {
                    if (i.getDesc().toLowerCase().contains(strSearch)) {
                        productos.add(i);
                    }
                }
            }
        }
        this.notifyDataSetChanged();
    }


}
