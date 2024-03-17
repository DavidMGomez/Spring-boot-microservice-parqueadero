package com.ceiba.registroestacionamiento.comando;

import com.ceiba.vehiculo.entidad.TipoVehiculo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComandoSolicitudRegistrarIngreso {
    private String placaVehiculo;
    private TipoVehiculo tipoVehiculo;
}
