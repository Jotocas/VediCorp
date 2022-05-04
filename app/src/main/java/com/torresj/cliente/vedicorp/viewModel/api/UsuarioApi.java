package com.torresj.cliente.vedicorp.viewModel.api;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.GenericResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface UsuarioApi {
    //RUTA DEL CONTROLADOR USUARIO
    String base = "vedicorp/mobil/vendedor";

    //RUTA DEL CONTROLADOR USUARIO + LA RUTA DEL MÉTODO
    @POST(base + "/login")
    Call<GenericResponse<Vendedor>> obtenerVendedorPorLogin(@Body DtoObtenerVendedorPorLogin dto);

    @Multipart
    @POST(base+ "/guardarImagen")
    Call<GenericResponse<Vendedor>> save(@Part MultipartBody.Part file, @Part("ven") RequestBody requestBody);


}
