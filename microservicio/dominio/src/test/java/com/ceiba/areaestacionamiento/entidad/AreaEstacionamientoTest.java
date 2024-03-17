package com.ceiba.areaestacionamiento.entidad;

import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AreaEstacionamientoTest {


    @Test
    @DisplayName("Should throw an exception when the vehicle type parameter is not provided")
    void crearWhenVehicleTypeIsNotProvidedThenThrowException() {
        String nombre = "Estacionamiento 1";
        Long capacidadMaxima = 10L;

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            AreaEstacionamiento.crear(nombre, null, capacidadMaxima);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the maximum capacity parameter is not provided")
    void crearWhenMaxCapacityIsNotProvidedThenThrowException() {
        String nombre = "Estacionamiento A";
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = null;

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            AreaEstacionamiento.crear(nombre, tipoVehiculo, capacidadMaxima);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the name parameter is not provided")
    void crearWhenNameIsNotProvidedThenThrowException() {
        String nombre = null;
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 10L;

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            AreaEstacionamiento.crear(nombre, tipoVehiculo, capacidadMaxima);
        });
    }

    @Test
    @DisplayName("Should create a new parking area when all required parameters are provided")
    void crearWithAllRequiredParameters() {
        String nombre = "Area 1";
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 100L;

        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear(nombre, tipoVehiculo, capacidadMaxima);

        assertNotNull(areaEstacionamiento);
        assertNull(areaEstacionamiento.getId());
        assertEquals(nombre, areaEstacionamiento.getNombre());
        assertEquals(tipoVehiculo, areaEstacionamiento.getTipoVehiculo());
        assertEquals(capacidadMaxima, areaEstacionamiento.getCapacidadMaxima());
        assertEquals(capacidadMaxima, areaEstacionamiento.getCapacidadActual());
    }
}