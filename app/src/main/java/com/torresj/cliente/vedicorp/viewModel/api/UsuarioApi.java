package com.torresj.cliente.vedicorp.viewModel.api;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.GenericResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface UsuarioApi {
    //RUTA DEL CONTROLADOR USUARIO
    String base = "vedicorp/mobil/vendedor";

    //RUTA DEL CONTROLADOR USUARIO + LA RUTA DEL MÉTODO
    @POST(base + "/login")
    Call<GenericResponse<Vendedor>> obtenerVendedorPorLogin(@Body DtoObtenerVendedorPorLogin dto);


}
