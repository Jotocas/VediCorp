package com.torresj.cliente.vedicorp.viewModel.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;
import com.torresj.cliente.vedicorp.utils.UBigDecimal;
import com.torresj.cliente.vedicorp.utils.UValidador;
import com.torresj.cliente.vedicorp.viewModel.Communication.CarritoCommunication;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductoCarritoAdapter extends RecyclerView.Adapter<ProductoCarritoAdapter.ViewHolder>  implements View.OnClickListener {
    private final DtoClienteProducto detalles;
    private final CarritoCommunication c;
    private View.OnClickListener listener;
    private ClickListener clickListener;


    public ProductoCarritoAdapter(DtoClienteProducto detalles, CarritoCommunication c,ClickListener clickListener) {
        this.detalles = detalles;
        this.clickListener=clickListener;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_carrito, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v, this.c);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.detalles.getDetalle().get(position));
    }

    @Override
    public int getItemCount() {
        if(!UValidador.esNulo(this.detalles)){
            if(!UValidador.esListaVacia(this.detalles.getDetalle())){
                return this.detalles.getDetalle().size();
            }
        }
        return 0;
    }

    public void setOnclickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgPlatilloDC, btnDecrease, btnAdd, btnEliminarCarrito;
        private final EditText edtCantidad;
        private final TextView tvNombrePlatilloDC, tvPrecioPDC;
        private final CarritoCommunication c;

        public ViewHolder(@NonNull View itemView, CarritoCommunication c) {
            super(itemView);
            this.c = c;
            this.imgPlatilloDC = itemView.findViewById(R.id.imgPlatilloDC);
            this.btnEliminarCarrito = itemView.findViewById(R.id.btnEliminarCarrito);
            this.btnAdd = itemView.findViewById(R.id.btnAdd);
            this.btnDecrease = itemView.findViewById(R.id.btnDecrease);
            this.edtCantidad = itemView.findViewById(R.id.edtCantidad);
            this.tvNombrePlatilloDC = itemView.findViewById(R.id.tvNombrePlatilloDC);
            this.tvPrecioPDC = itemView.findViewById(R.id.tvPrecioPDC);
        }

        public void setItem(final PedidoDetalle dp) {
            this.tvNombrePlatilloDC.setText(dp.getDescri());
            this.tvPrecioPDC.setText(String.format(Locale.ENGLISH, "S/%.2f", dp.getPrecio()));
            BigDecimal cant = dp.getCant();
            this.edtCantidad.setText(cant.toString());

            //-------------Actualizar Cantidad del Carrito-------------------------
          /*  btnAdd.setOnClickListener(v -> {
                 dp.addOne();
                ProductoCarritoAdapter.this.notifyDataSetChanged();

            });
             */

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dp.addOne();
                    ProductoCarritoAdapter.this.notifyDataSetChanged();
                    clickListener.clickProductoItem(dp,v);
                }
            });

            /*  btnDecrease.setOnClickListener(v -> {
                if(UBigDecimal.esMenorQue(dp.getCant(),BigDecimal.ONE)){
                    dp.removeOne();
                }

                ProductoCarritoAdapter.this.notifyDataSetChanged();

            });

             */

            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UBigDecimal.esMenorQue(dp.getCant(),BigDecimal.ONE)){
                        dp.removeOne();
                    }
                    ProductoCarritoAdapter.this.notifyDataSetChanged();
                    clickListener.clickProductoItem(dp,v);
                }
            });

            //------------------Eliminar item del carrito-----------------------
            this.btnEliminarCarrito.setOnClickListener(v -> {
                showMsg(dp.getCodigo());
            });
        }

        private void showMsg(String idPlatillo) {
            new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Aviso del sistema !")
                    .setContentText("¿Estás seguro de eliminar el Producto de tu bolsa de compras?")
                    .setCancelText("No, Cancelar!").setConfirmText("Sí, Confirmar")
                    .showCancelButton(true).setCancelClickListener(sDialog -> {
                sDialog.dismissWithAnimation();
                new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                        .setContentText("No eliminaste ningún Producto de tu bolsa de compras")
                        .show();
            }).setConfirmClickListener(sweetAlertDialog -> {
                c.eliminarDetalle(idPlatillo);
                sweetAlertDialog.dismissWithAnimation();
                new SweetAlertDialog(itemView.getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo !")
                        .setContentText("Excelente, el Producto acaba de ser eliminado de tu bolsa de compras")
                        .show();
            }).show();
        }
    }
}
