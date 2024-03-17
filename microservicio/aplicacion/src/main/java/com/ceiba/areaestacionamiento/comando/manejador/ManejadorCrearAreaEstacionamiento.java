package com.ceiba.areaestacionamiento.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.areaestacionamiento.comando.ComandoSolicitudCreacionArea;
import com.ceiba.areaestacionamiento.entidad.SolicitudCrearAreaEstacionamiento;
import com.ceiba.areaestacionamiento.servicio.ServicioCrearAreaEstacionamiento;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorCrearAreaEstacionamiento implements ManejadorComandoRespuesta<ComandoSolicitudCreacionArea, ComandoRespuesta<Long>> {
    private final ServicioCrearAreaEstacionamiento servicioCrearAreaEstacionamiento;

    public ManejadorCrearAreaEstacionamiento(ServicioCrearAreaEstacionamiento servicioCrearAreaEstacionamiento) {
        this.servicioCrearAreaEstacionamiento = servicioCrearAreaEstacionamiento;
    }

    public ComandoRespuesta<Long> ejecutar(ComandoSolicitudCreacionArea comando) {
        SolicitudCrearAreaEstacionamiento solicitudCrearAreaEstacionamiento =
                new SolicitudCrearAreaEstacionamiento(comando.getNombre(), comando.getTipoVehiculo(), comando.getCapacidadMaxima());
        return new ComandoRespuesta<>(servicioCrearAreaEstacionamiento.ejecutar(solicitudCrearAreaEstacionamiento));
    }

}
