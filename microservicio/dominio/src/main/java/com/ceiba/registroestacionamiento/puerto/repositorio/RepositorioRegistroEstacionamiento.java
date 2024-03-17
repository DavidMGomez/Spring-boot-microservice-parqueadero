package com.ceiba.registroestacionamiento.puerto.repositorio;


import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;

import java.util.List;

public interface RepositorioRegistroEstacionamiento {
    Long guardar(RegistroEstacionamiento registroEstacionamiento);

    RegistroEstacionamiento obtener(Long id);

    void actualizarEstadoYMontoFechaSalida(RegistroEstacionamiento registroEstacionamiento);

    List<RegistroEstacionamiento> obtenerRegistrosPorEstado(EstadoRegistro estadoRegistro);

}
