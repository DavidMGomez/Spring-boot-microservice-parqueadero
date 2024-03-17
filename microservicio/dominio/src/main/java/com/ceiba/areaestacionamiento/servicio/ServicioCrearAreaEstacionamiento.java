package com.ceiba.areaestacionamiento.servicio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.entidad.SolicitudCrearAreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServicioCrearAreaEstacionamiento {
    private final RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    public Long ejecutar(SolicitudCrearAreaEstacionamiento solicitudCrearAreaEstacionamiento) {
        if (solicitudCrearAreaEstacionamiento == null) {
            throw new ExcepcionValorObligatorio("SolicitudAreaEstacionamiento es obligatoria");
        }
        if (solicitudCrearAreaEstacionamiento.getCapacidadMaxima() <= 0) {
            throw new ExcepcionValorInvalido("Capacidad Maxima Debe ser mayor a 0");
        }
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear(solicitudCrearAreaEstacionamiento.getNombre(),
                solicitudCrearAreaEstacionamiento.getTipoVehiculo(),
                solicitudCrearAreaEstacionamiento.getCapacidadMaxima());
        return repositorioAreaEstacionamiento.crear(areaEstacionamiento);
    }
}

