package com.torresj.cliente.vedicorp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Producto;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.repository.ProductoRepository;
import com.torresj.cliente.vedicorp.viewModel.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {
 private final ProductoRepository repository;
    public ProductoViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = ProductoRepository.getInstance();
    }
    public LiveData<GenericResponse<List<Producto>>> obtenerProducto(String codigo){
        return this.repository.obtenerProducto(codigo);
    }


}
