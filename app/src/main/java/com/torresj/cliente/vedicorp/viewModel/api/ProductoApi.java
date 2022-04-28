package com.torresj.cliente.vedicorp.viewModel.api;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ProductoApi {
    //RUTA DEL CONTROLADOR PRODUCTO
    String base = "vedicorp/mobil/producto";

    //RUTA DEL CONTROLADOR PRODUCTO + LA RUTA DEL MÉTODO
    @GET(base + "/obtenerProducto/{codigo}")
    Call<GenericResponse<List<Producto>>> obtenerProducto(@Path("codigo") String codigo);


}
