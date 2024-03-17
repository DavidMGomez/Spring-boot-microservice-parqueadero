package com.ceiba.vehiculo.entidad;

import com.ceiba.dominio.ValidadorArgumento;

public class Vehiculo {
    private  Long id;
    private final TipoVehiculo tipo;
    private final String placa;

    private Vehiculo(Long id, TipoVehiculo tipo, String placa) {
        this.id = id;
        this.tipo = tipo;
        this.placa = placa;
    }

    private Vehiculo(TipoVehiculo tipo, String placa) {
        this.tipo = tipo;
        this.placa = placa;
    }

    public static Vehiculo reconstruir(Long id, TipoVehiculo tipo, String placa) {
        ValidadorArgumento.validarObligatorio(id, "El id del vehiculo es requerido");
        ValidadorArgumento.validarObligatorio(tipo, "El tipo del vehiculo es requerido");
        ValidadorArgumento.validarObligatorio(placa, "La placa del vehiculo es requerida");
        return new Vehiculo(id, tipo, placa);
    }

    public static Vehiculo crear(TipoVehiculo tipo, String placa) {
        ValidadorArgumento.validarObligatorio(tipo, "El tipo del vehiculo es requerido");
        ValidadorArgumento.validarObligatorio(placa, "La placa del vehiculo es requerida");
        return new Vehiculo( tipo, placa);
    }


    public Long getId() {
        return id;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }
}
