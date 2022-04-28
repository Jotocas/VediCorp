package com.torresj.cliente.vedicorp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.torresj.cliente.vedicorp.model.DtoClienteProducto;
import com.torresj.cliente.vedicorp.model.DtoPedidoClienteResumen;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.repository.VentasResumenRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VentasResumenViewModel extends AndroidViewModel {
 private final VentasResumenRepository repository;
    public VentasResumenViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = VentasResumenRepository.getInstance();
    }
    public LiveData<GenericResponse<List<DtoPedidoClienteResumen>>> pedidoPorCliente(DtoClienteProducto dto){
        return this.repository.pedidoPorCliente(dto);
    }

    public LiveData<GenericResponse<DtoClienteProducto>> confirmarCompra(DtoClienteProducto dto){
        return this.repository.confirmarCompra(dto);
    }

    public LiveData<GenericResponse<List<DtoPedidoClienteResumen>>> detallePedidoPorCliente(DtoClienteProducto dto){
        return this.repository.detallePedidoPorCliente(dto);
    }

}
