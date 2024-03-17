package com.ceiba.registroestacionamiento.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarSalida;
import com.ceiba.registroestacionamiento.exceptions.ExcepcionRegistroNoEncontrado;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.ServicioRegistrarSalida;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ManejadorSalidaRegistroEstacionamiento implements ManejadorComandoRespuesta<ComandoSolicitudRegistrarSalida, ComandoRespuesta<ResumenRegistroEstacionamientoDTO>> {
    private final ServicioRegistrarSalida servicioRegistrarSalida;

    private final RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;

    public ManejadorSalidaRegistroEstacionamiento(ServicioRegistrarSalida servicioRegistrarSalida, RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento) {
        this.servicioRegistrarSalida = servicioRegistrarSalida;
        this.repositorioRegistroEstacionamiento = repositorioRegistroEstacionamiento;
    }

    @Override
    public ComandoRespuesta<ResumenRegistroEstacionamientoDTO> ejecutar(ComandoSolicitudRegistrarSalida comando) {
        LocalDateTime fechaHoraSalida = LocalDateTime.now();
        RegistroEstacionamiento registroEstacionamiento = repositorioRegistroEstacionamiento.obtener(comando.getIdRegistro());
        if (registroEstacionamiento == null) {
            throw new ExcepcionRegistroNoEncontrado("El registro no existe");
        }
        return new ComandoRespuesta<>(servicioRegistrarSalida.ejecutar(registroEstacionamiento, fechaHoraSalida));
    }
}
