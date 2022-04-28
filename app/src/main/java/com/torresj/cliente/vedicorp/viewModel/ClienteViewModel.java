package com.torresj.cliente.vedicorp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.torresj.cliente.vedicorp.model.Cliente;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.repository.ClienteRepository;
import com.torresj.cliente.vedicorp.viewModel.repository.ProductoRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClienteViewModel extends AndroidViewModel {
 private final ClienteRepository repository;
    public ClienteViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Cliente>>> obtenerCliente(String codigo){
        return this.repository.obtenerCliente(codigo);
    }

    public LiveData<GenericResponse<String>> validarZonaVentas(String cliente, String vendedor){
        return this.repository.validarZonaVentas(cliente,vendedor);
    }
}
