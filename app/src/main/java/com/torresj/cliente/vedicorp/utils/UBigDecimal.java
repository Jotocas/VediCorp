package com.torresj.cliente.vedicorp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UBigDecimal {
    public static boolean esCeroOrNulo(BigDecimal numero) {
        if (numero == null) {
            return true;
        }
        if (numero.compareTo(BigDecimal.ZERO)==0) {
            return true;
        }
        return false;
    }

    public static BigDecimal obtenerValorSinNulo(BigDecimal numero) {
        if (numero == null) {
            numero= new BigDecimal(0);
            return numero.setScale(2, RoundingMode.HALF_UP);
        }

        if (numero.compareTo(BigDecimal.ZERO)==0) {
            numero= numero.setScale(2, RoundingMode.HALF_UP);
        }

        return numero;
    }

    public static Boolean esMayorIgualque(BigDecimal valor1, BigDecimal valor2) {
        Integer valor = valor1.compareTo(valor2);
        if (valor == 0 || valor == 1) {
            return true;
        }
        return false;
    }

    public static Boolean esMayorQue(BigDecimal valor1, BigDecimal valor2) {
        Integer valor = valor1.compareTo(valor2);
        if (valor == 1) {
            return true;
        }
        return false;
    }

    public static Boolean esMenorQue(BigDecimal valor1, BigDecimal valor2) {
        Integer valor = valor1.compareTo(valor2);
        if ( valor == -1) {
            return true;
        }
        return false;
    }


    public static Boolean esMenorIgualque(BigDecimal valor1, BigDecimal valor2) {
        Integer valor = valor1.compareTo(valor2);
        if (valor == 0 || valor == -1) {
            return true;
        }
        return false;
    }

    public static Boolean esDiferente(BigDecimal valor1, BigDecimal valor2) {
        Integer valor = valor1.compareTo(valor2);
        if (valor == 1 || valor == -1) {
            return true;
        }
        return false;
    }

}
