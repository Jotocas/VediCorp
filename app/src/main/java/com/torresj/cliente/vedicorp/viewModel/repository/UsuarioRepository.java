package com.torresj.cliente.vedicorp.viewModel.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.api.ConfigApi;
import com.torresj.cliente.vedicorp.viewModel.api.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static UsuarioRepository getInstance(){
        if(repository == null){
            repository = new UsuarioRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Vendedor>> obtenerVendedorPorLogin(DtoObtenerVendedorPorLogin dto){
        final MutableLiveData<GenericResponse<Vendedor>> mld = new MutableLiveData<>();
        this.api.obtenerVendedorPorLogin(dto).enqueue(new Callback<GenericResponse<Vendedor>>() {
            @Override
            public void onResponse(Call<GenericResponse<Vendedor>> call, Response<GenericResponse<Vendedor>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Vendedor>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


}
