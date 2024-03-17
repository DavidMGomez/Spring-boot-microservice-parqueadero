package com.ceiba.registroestacionamiento.consulta.manejador;


import com.ceiba.registroestacionamiento.consulta.ComandoConsultaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.servicio.ServicioConsultaRegistroEstacionamiento;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManejadorConsultarRegistroEstacionamiento {
    private final ServicioConsultaRegistroEstacionamiento servicioConsultaRegistroEstacionamiento;

    public ManejadorConsultarRegistroEstacionamiento(ServicioConsultaRegistroEstacionamiento servicioConsultaRegistroEstacionamiento) {
        this.servicioConsultaRegistroEstacionamiento = servicioConsultaRegistroEstacionamiento;
    }

    public List<ResumenRegistroEstacionamientoDTO> consultarResumenEstacionamiento(ComandoConsultaRegistroEstacionamiento comandoConsultaRegistroEstacionamiento) {
        return servicioConsultaRegistroEstacionamiento.consultarRegistrosEstacionamientoPorEstado(comandoConsultaRegistroEstacionamiento.getEstadoRegistro());
    }
}
