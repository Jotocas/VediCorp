package com.torresj.cliente.vedicorp.viewModel.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.api.VentasResumenApi;
import com.torresj.cliente.vedicorp.viewModel.api.ConfigApi;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VentasResumenRepository {
    private static VentasResumenRepository repository;
    private final VentasResumenApi api;

    public VentasResumenRepository() {
        this.api = ConfigApi.getComprasResumenApi();
    }

    public static VentasResumenRepository getInstance(){
        if(repository == null){
            repository = new VentasResumenRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<List<DtoPedidoClienteResumen>>> pedidoPorCliente(DtoClienteProducto dto){
        final MutableLiveData<GenericResponse<List<DtoPedidoClienteResumen>>> mld = new MutableLiveData<>();

        this.api.pedidoPorCliente(dto).enqueue(new Callback<GenericResponse<List<DtoPedidoClienteResumen>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<DtoPedidoClienteResumen>>> call, Response<GenericResponse<List<DtoPedidoClienteResumen>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<DtoPedidoClienteResumen>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener el Resumen: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }



    public LiveData<GenericResponse<List<DtoPedidoClienteResumen>>> detallePedidoPorCliente(DtoClienteProducto dto){
        final MutableLiveData<GenericResponse<List<DtoPedidoClienteResumen>>> mld = new MutableLiveData<>();

        this.api.detallePedidoPorCliente(dto).enqueue(new Callback<GenericResponse<List<DtoPedidoClienteResumen>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<DtoPedidoClienteResumen>>> call, Response<GenericResponse<List<DtoPedidoClienteResumen>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<DtoPedidoClienteResumen>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener el Resumen: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<DtoClienteProducto>> confirmarCompra(DtoClienteProducto dto){
        final MutableLiveData<GenericResponse<DtoClienteProducto>> mld = new MutableLiveData<>();

        this.api.confirmarCompra(dto).enqueue(new Callback<GenericResponse<DtoClienteProducto>>() {
            @Override
            public void onResponse(Call<GenericResponse<DtoClienteProducto>> call, Response<GenericResponse<DtoClienteProducto>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<DtoClienteProducto>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener el Resumen: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
