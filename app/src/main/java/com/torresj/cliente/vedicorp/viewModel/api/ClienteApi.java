package com.torresj.cliente.vedicorp.viewModel.api;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ClienteApi {
    //RUTA DEL CONTROLADOR CLIENTE
    String base = "vedicorp/mobil/cliente";

    //RUTA DEL CONTROLADOR CLIENTE + LA RUTA DEL MÉTODO
    @GET(base + "/obtenerCliente/{codigo}")
    Call<GenericResponse<List<Cliente>>> obtenerCliente(@Path("codigo") String codigo);

    @GET(base + "/validarZonaVentas/{cliente}/{vendedor}")
    Call<GenericResponse<String>> validarZonaVentas(@Path("cliente") String cliente,  @Path("vendedor")  String vendedor);


}
