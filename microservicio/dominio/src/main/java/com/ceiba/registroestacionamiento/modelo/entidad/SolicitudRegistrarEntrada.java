package com.ceiba.registroestacionamiento.modelo.entidad;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.vehiculo.entidad.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class SolicitudRegistrarEntrada {

    @Setter
    private Vehiculo vehiculoRegistro;
    private final LocalDateTime fechaHoraIngreso;
    private final AreaEstacionamiento areaRegistro;

}
