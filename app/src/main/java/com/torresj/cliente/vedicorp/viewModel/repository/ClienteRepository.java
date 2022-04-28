package com.torresj.cliente.vedicorp.viewModel.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.api.ClienteApi;
import com.torresj.cliente.vedicorp.viewModel.api.ConfigApi;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {
    private static ClienteRepository repository;
    private final ClienteApi api;

    public ClienteRepository() {
        this.api = ConfigApi.getClienteApi();
    }

    public static ClienteRepository getInstance(){
        if(repository == null){
            repository = new ClienteRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<List<Cliente>>> obtenerCliente(String  codigo){
        final MutableLiveData<GenericResponse<List<Cliente>>> mld = new MutableLiveData<>();
        this.api.obtenerCliente(codigo).enqueue(new Callback<GenericResponse<List<Cliente>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Cliente>>> call, Response<GenericResponse<List<Cliente>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Cliente>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener los Clientes: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


    public LiveData<GenericResponse<String>> validarZonaVentas(String  cliente, String vendedor){
        final MutableLiveData<GenericResponse<String>> mld = new MutableLiveData<>();
        this.api.validarZonaVentas(cliente,vendedor).enqueue(new Callback<GenericResponse<String>>() {
            @Override
            public void onResponse(Call<GenericResponse<String>> call, Response<GenericResponse<String>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<String>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al validar zonas de ventas: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
