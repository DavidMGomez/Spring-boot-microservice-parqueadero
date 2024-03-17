package com.ceiba.registroestacionamiento.exceptions;

public class ExcepcionRegistroYaFinalizado extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcepcionRegistroYaFinalizado(String message) {
        super(message);
    }
}
