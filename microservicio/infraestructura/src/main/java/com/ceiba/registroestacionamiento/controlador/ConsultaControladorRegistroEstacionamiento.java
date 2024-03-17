package com.ceiba.registroestacionamiento.controlador;

import com.ceiba.excepcion.ExcepcionValorObligatorio;
import com.ceiba.registroestacionamiento.consulta.ComandoConsultaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.consulta.manejador.ManejadorConsultarRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/registro-estacionamiento")
@Tag(name = "Controlador consulta Registro Estacionamiento")
public class ConsultaControladorRegistroEstacionamiento {

    private final ManejadorConsultarRegistroEstacionamiento manejadorConsultarRegistroEstacionamiento;

    public ConsultaControladorRegistroEstacionamiento(ManejadorConsultarRegistroEstacionamiento manejadorConsultarRegistroEstacionamiento) {
        this.manejadorConsultarRegistroEstacionamiento = manejadorConsultarRegistroEstacionamiento;
    }

    @GetMapping("")
    @Operation(summary = "Listar Por estado", description = "Metodo utilizado para consultar los registros de estacionamiento por estado")
    public List<ResumenRegistroEstacionamientoDTO> obtenerRegistrosEstacionamientoPorEstado(ComandoConsultaRegistroEstacionamiento comandoConsultaRegistroEstacionamiento) {
        if(Objects.isNull(comandoConsultaRegistroEstacionamiento.getEstadoRegistro()))
            throw new ExcepcionValorObligatorio("Estado de registro es obligatorio");
        return manejadorConsultarRegistroEstacionamiento.consultarResumenEstacionamiento(comandoConsultaRegistroEstacionamiento);
    }
}
