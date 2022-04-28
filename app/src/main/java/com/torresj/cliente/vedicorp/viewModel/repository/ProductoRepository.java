package com.torresj.cliente.vedicorp.viewModel.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.api.ConfigApi;
import com.torresj.cliente.vedicorp.viewModel.api.ProductoApi;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoRepository {
    private static ProductoRepository repository;
    private final ProductoApi api;

    public ProductoRepository() {
        this.api = ConfigApi.getProductoApi();
    }

    public static ProductoRepository getInstance(){
        if(repository == null){
            repository = new ProductoRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<List<Producto>>> obtenerProducto(String  codigo){
        final MutableLiveData<GenericResponse<List<Producto>>> mld = new MutableLiveData<>();
        this.api.obtenerProducto(codigo).enqueue(new Callback<GenericResponse<List<Producto>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Producto>>> call, Response<GenericResponse<List<Producto>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Producto>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener los productos: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


}
