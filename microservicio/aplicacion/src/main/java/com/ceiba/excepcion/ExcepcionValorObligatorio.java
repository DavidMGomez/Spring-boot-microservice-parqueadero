package com.ceiba.excepcion;

public class ExcepcionValorObligatorio extends RuntimeException {
    public ExcepcionValorObligatorio(String mensaje) {
        super(mensaje);
    }
}
