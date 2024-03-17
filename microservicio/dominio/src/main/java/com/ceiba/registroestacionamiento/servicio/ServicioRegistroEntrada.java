package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.entidad.SolicitudRegistrarEntrada;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;

public class ServicioRegistroEntrada {
    private final RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    private final RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;


    public ServicioRegistroEntrada(RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento,
                                   RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        this.repositorioRegistroEstacionamiento = repositorioRegistroEstacionamiento;
        this.repositorioAreaEstacionamiento = repositorioAreaEstacionamiento;
    }

    public Long ejecutar(SolicitudRegistrarEntrada solicitudRegistrarEntrada) {
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);
        if (solicitudRegistrarEntrada.getAreaRegistro().getCapacidadActual() == 0) {
            throw new ExcepcionValorInvalido("El area no tiene capacidad");
        }
        repositorioAreaEstacionamiento.restarCapacidadActual(registroEstacionamiento.getAreaRegistro());
        return repositorioRegistroEstacionamiento.guardar(registroEstacionamiento);
    }
}
