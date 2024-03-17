package com.ceiba.registroestacionamiento.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarIngreso;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarSalida;
import com.ceiba.registroestacionamiento.comando.manejador.ManejadorEntradaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.comando.manejador.ManejadorSalidaRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registro-estacionamiento")
@Tag(name = "Controlador comando registro estacionamiento")
public class ComandoControladorRegistroEstacionamiento {

    private final ManejadorEntradaRegistroEstacionamiento manejadorEntradaRegistroEstacionamiento;
    private final ManejadorSalidaRegistroEstacionamiento manejadorSalidaRegistroEstacionamiento;

    public ComandoControladorRegistroEstacionamiento(ManejadorEntradaRegistroEstacionamiento manejadorEntradaRegistroEstacionamiento,
                                                     ManejadorSalidaRegistroEstacionamiento manejadorSalidaRegistroEstacionamiento) {
        this.manejadorEntradaRegistroEstacionamiento = manejadorEntradaRegistroEstacionamiento;
        this.manejadorSalidaRegistroEstacionamiento = manejadorSalidaRegistroEstacionamiento;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "ingresar", description = "Metodo utilizado para ingresar un registro al parqueadero ")
    public ComandoRespuesta<Long> ingresarRegistro(@RequestBody ComandoSolicitudRegistrarIngreso comandoSolicitudRegistrarIngreso) {
        return manejadorEntradaRegistroEstacionamiento.ejecutar(comandoSolicitudRegistrarIngreso);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registrar-salida")
    @Operation(summary = "registrar salida", description = "Metodo utilizado para registrar la salida un registro al parqueadero ")
    public ComandoRespuesta<ResumenRegistroEstacionamientoDTO> registrarSalida(@RequestBody ComandoSolicitudRegistrarSalida comandoSolicitudRegistrarSalida) {
        return manejadorSalidaRegistroEstacionamiento.ejecutar(comandoSolicitudRegistrarSalida);
    }

}
