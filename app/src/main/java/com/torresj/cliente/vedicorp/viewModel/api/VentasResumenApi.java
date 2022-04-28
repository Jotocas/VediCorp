package com.torresj.cliente.vedicorp.viewModel.api;

import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface VentasResumenApi {
    //RUTA DEL CONTROLADOR PRODUCTO
    String base = "vedicorp/mobil/pedido";

    //RUTA DEL CONTROLADOR PEDIDO + LA RUTA DEL MÉTODO
    @PUT(base + "/pedidoPorCliente")
    Call<GenericResponse<List<DtoPedidoClienteResumen>>> pedidoPorCliente(@Body DtoClienteProducto dto);

    @PUT(base + "/confirmarCompra")
    Call<GenericResponse<DtoClienteProducto>> confirmarCompra(@Body DtoClienteProducto dto);

    @PUT(base + "/detallePedidoPorCliente")
    Call<GenericResponse<List<DtoPedidoClienteResumen>>> detallePedidoPorCliente(@Body DtoClienteProducto dto);

}
