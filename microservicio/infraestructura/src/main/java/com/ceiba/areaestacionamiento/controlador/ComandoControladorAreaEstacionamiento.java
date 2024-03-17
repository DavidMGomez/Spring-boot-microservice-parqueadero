package com.ceiba.areaestacionamiento.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.areaestacionamiento.comando.ComandoSolicitudCreacionArea;
import com.ceiba.areaestacionamiento.comando.manejador.ManejadorCrearAreaEstacionamiento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area-estacionamiento")
@Tag(name = "Controlador comando area estacionamiento")
public class ComandoControladorAreaEstacionamiento {

   private final ManejadorCrearAreaEstacionamiento manejadorCrearAreaEstacionamiento;

    public ComandoControladorAreaEstacionamiento(ManejadorCrearAreaEstacionamiento manejadorCrearAreaEstacionamiento) {
        this.manejadorCrearAreaEstacionamiento = manejadorCrearAreaEstacionamiento;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "crear area", description = "Metodo utilizado para crear un area de estacionamiento ")
    public ComandoRespuesta<Long> ingresarRegistro(@RequestBody ComandoSolicitudCreacionArea comandoSolicitudCreacionArea) {
        return manejadorCrearAreaEstacionamiento.ejecutar(comandoSolicitudCreacionArea);
    }
}
