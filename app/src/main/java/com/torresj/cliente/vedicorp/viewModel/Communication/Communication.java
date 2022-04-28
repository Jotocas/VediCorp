package com.torresj.cliente.vedicorp.viewModel.Communication;

import android.content.Intent;

import com.torresj.cliente.vedicorp.model.Cliente;

public interface Communication {
   // void showDetails(Intent i);

    //esta interface se encarga de realizar la comunicacion entre la lista de Clientes y los pedidos
    public void enviarPersona(Cliente cliente); //se transportara un objeto de tipo Cliente
    //(En la clase Persona se implementa Serializable para poder transportar un objeteo a otro)
}
