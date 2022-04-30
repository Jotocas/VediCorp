package com.torresj.cliente.vedicorp.viewModel.interfaces;

import android.view.View;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.PedidoDetalle;

public interface ClickListener {
    void clickItem(Cliente cliente, View v);
    void clickProductoItem(PedidoDetalle pedidoDetalle, View v);
    void clickIncrementarItem(View v);
    void clickBoton(Cliente cliente, View v,String tipo);
}
