package com.torresj.cliente.vedicorp.utils;

import java.util.List;

public class UValidador {
    public static boolean esNulo(Object objeto) {
        if (objeto == null)
            return true;
        return false;
    }

    public static boolean estaVacio(Object objeto) {
        if (objeto != null)
            return false;
        return true;
    }

    public static boolean esListaVacia(@SuppressWarnings("rawtypes") List lista) {
        if (lista == null || lista.size() == 0)
            return true;
        return false;
    }
}
