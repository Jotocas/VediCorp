package com.torresj.cliente.vedicorp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.torresj.cliente.vedicorp.model.DtoObtenerVendedorPorLogin;
import com.torresj.cliente.vedicorp.model.Vendedor;
import com.torresj.cliente.vedicorp.utils.GenericResponse;
import com.torresj.cliente.vedicorp.viewModel.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

public class UsuarioViewModel extends AndroidViewModel {
 private final UsuarioRepository repository;
    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }
    public LiveData<GenericResponse<Vendedor>> obtenerVendedorPorLogin(DtoObtenerVendedorPorLogin dto){
        return this.repository.obtenerVendedorPorLogin(dto);
    }


}
