package com.ceiba.areaestacionamiento.comando;

import com.ceiba.vehiculo.entidad.TipoVehiculo;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComandoSolicitudCreacionArea {
    private  String nombre;
    private  TipoVehiculo tipoVehiculo;
    private  Long capacidadMaxima;
}
