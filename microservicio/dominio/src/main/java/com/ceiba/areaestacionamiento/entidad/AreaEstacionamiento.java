package com.ceiba.areaestacionamiento.entidad;

import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;

public class AreaEstacionamiento {
    private  Long id;
    private final String nombre;
    private final TipoVehiculo tipoVehiculo;
    private final Long capacidadMaxima;
    private final Long capacidadActual;

    private AreaEstacionamiento(Long id, String nombre, TipoVehiculo tipoVehiculo, Long capacidadMaxima, Long capacidadActual) {
        this.id = id;
        this.nombre = nombre;
        this.tipoVehiculo = tipoVehiculo;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadActual;
    }

    private AreaEstacionamiento(String nombre, TipoVehiculo tipoVehiculo, Long capacidadMaxima) {
        this.nombre = nombre;
        this.tipoVehiculo = tipoVehiculo;
        this.capacidadMaxima = capacidadMaxima;
        this.capacidadActual = capacidadMaxima;
    }

    public static AreaEstacionamiento crear(String nombre, TipoVehiculo tipoVehiculo, Long capacidadMaxima) {
        ValidadorArgumento.validarObligatorio(nombre, "El nombre de AreaEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(tipoVehiculo, "La tipoVehiculo del AreaEstacionamiento es requerida");
        ValidadorArgumento.validarObligatorio(capacidadMaxima, "La capacidadMaxima del AreaEstacionamiento es requerida");
        return new AreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);
    }

    public static AreaEstacionamiento reconstruir(Long id, String nombre, TipoVehiculo tipoVehiculo, Long capacidadMaxima, Long capacidadActual) {
        ValidadorArgumento.validarObligatorio(id, "El id de AreaEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(nombre, "El nombre de AreaEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(tipoVehiculo, "La tipoVehiculo del AreaEstacionamiento es requerida");
        ValidadorArgumento.validarObligatorio(capacidadMaxima, "La capacidadMaxima del AreaEstacionamiento es requerida");
        ValidadorArgumento.validarObligatorio(capacidadActual, "La capacidadActual del AreaEstacionamiento es requerida");
        return new AreaEstacionamiento(id, nombre, tipoVehiculo, capacidadMaxima, capacidadActual);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public Long getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public Long getCapacidadActual() {
        return capacidadActual;
    }
}
