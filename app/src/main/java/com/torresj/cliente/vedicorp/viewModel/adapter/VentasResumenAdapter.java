package com.torresj.cliente.vedicorp.viewModel.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class VentasResumenAdapter extends RecyclerView.Adapter<VentasResumenAdapter.ViewHolder> {

    private final List<DtoPedidoClienteResumen> resumen;

    public VentasResumenAdapter(List<DtoPedidoClienteResumen> resumen) {
        this.resumen = resumen;
     }

    @NonNull
    @Override public VentasResumenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resumen, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.resumen.get(position));
        holder.imgfoto.setBackgroundResource(R.drawable.ic_dinero);
    }

    @Override
    public int getItemCount() {
        return this.resumen.size();
    }

    public void updateItems(List<DtoPedidoClienteResumen> resumen) {
        this.resumen.clear();
        this.resumen.addAll(resumen);

        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgfoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgfoto = itemView.findViewById(R.id.imgArticleProductoVenta);
        }

        public void setItem(final DtoPedidoClienteResumen dto) {
                     final TextView txtValueCodCompra = this.itemView.findViewById(R.id.txtValueCodCliente),
                    txtValueBruto = this.itemView.findViewById(R.id.txtValueBruto),
                    txtValueIGV = this.itemView.findViewById(R.id.txtValueIGV),
                    txtValueTotal = this.itemView.findViewById(R.id.txtValueTotal);

            txtValueCodCompra.setText(dto.getClienteCodigo());
            txtValueBruto.setText(String.format(Locale.ENGLISH, "S/.%.2f", dto.getMontoBruto()));
            txtValueIGV.setText(String.format(Locale.ENGLISH, "S/.%.2f", dto.getMontoIgv()));
            txtValueTotal.setText(String.format(Locale.ENGLISH, "S/.%.2f", dto.getMontoTotal()));
        }
    }


}
