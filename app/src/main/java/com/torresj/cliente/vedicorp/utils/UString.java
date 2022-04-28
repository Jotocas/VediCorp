package com.torresj.cliente.vedicorp.utils;

public class UString {

    public static boolean esNuloVacio(Object varObject) {
        if (varObject == null) {
            return true;
        }
        String variable = (String) varObject;

        if (variable == null || variable.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean esNuloVacio(String variable) {
        if (variable == null || variable.length() == 0) {
            return true;
        }
        return false;
    }
}
