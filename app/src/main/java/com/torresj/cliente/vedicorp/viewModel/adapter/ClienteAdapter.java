package com.torresj.cliente.vedicorp.viewModel.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.torresj.cliente.vedicorp.R;
import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.viewModel.interfaces.ClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Cliente> clientes;
    private List<Cliente> originalClientes;
    private View.OnClickListener listener;
    private ClickListener clickListener;
    private Button btnRutas;

    
    public ClienteAdapter(List<Cliente> clientes,ClickListener clickListener ) {
        this.clientes = clientes;
        this.clickListener=clickListener;
        this.originalClientes = new ArrayList<>();
        originalClientes.addAll(clientes);
    }

    @NonNull
    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setItem(this.clientes.get(position));
        Cliente clienteSeleccionado = this.clientes.get(position);
        System.out.println("Cliente Seleccionado==>" + clienteSeleccionado.getNomb());

        holder.imgfoto.setBackgroundResource(R.drawable.ic_custumer);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.clickItem(clienteSeleccionado,v);
            }
        });


        btnRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.clickBoton(clienteSeleccionado,v,"Rutas");
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.clientes.size();
    }

    public void updateItems(List<Cliente> clientes) {
        this.clientes.clear();
        this.clientes.addAll(clientes);
        originalClientes.addAll(clientes);

        this.notifyDataSetChanged();
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

        public ImageView imgfoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgfoto = itemView.findViewById(R.id.imgArticle);
            btnRutas = this.itemView.findViewById(R.id.btnRutas);
        }

        public void setItem(final Cliente dto) {

            final TextView txtValueCodCliente = this.itemView.findViewById(R.id.txtValueCodCliente),
                    txtValueNombre = this.itemView.findViewById(R.id.txtValueNombre),
                    txtValueDireccion = this.itemView.findViewById(R.id.txtValueDireccion),
                    txtValueTipoDoc = this.itemView.findViewById(R.id.txtValueTipoDoc);
            imgfoto = itemView.findViewById(R.id.imgArticle);

            txtValueCodCliente.setText(dto.getNclte());
            txtValueNombre.setText(dto.getNomb());
            txtValueDireccion.setText(dto.getDire());
            txtValueTipoDoc.setText(dto.getTdoc());


        }
    }

    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            clientes.clear();
            clientes.addAll(originalClientes);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clientes.clear();
                List<Cliente> collect = originalClientes.stream()
                        .filter(i -> i.getNomb().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                clientes.addAll(collect);
            } else {
                clientes.clear();
                for (Cliente i : originalClientes) {
                    if (i.getNomb().toLowerCase().contains(strSearch)) {
                        clientes.add(i);
                    }
                }
            }
        }
        this.notifyDataSetChanged();
    }

}
