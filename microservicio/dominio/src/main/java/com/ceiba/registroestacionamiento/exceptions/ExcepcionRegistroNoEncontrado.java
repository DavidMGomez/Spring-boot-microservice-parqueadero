package com.ceiba.registroestacionamiento.exceptions;

public class ExcepcionRegistroNoEncontrado extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExcepcionRegistroNoEncontrado(String message) {
        super(message);
    }
}
