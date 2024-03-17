package com.ceiba.areaestacionamiento.entidad;

import com.ceiba.vehiculo.entidad.TipoVehiculo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SolicitudCrearAreaEstacionamiento {
    private final String nombre;
    private final TipoVehiculo tipoVehiculo;
    private final Long capacidadMaxima;
}
