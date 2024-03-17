package com.ceiba.vehiculo.entidad;

import com.ceiba.core.BasePrueba;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VehiculoTest {

    @Test
    void deberiaCrearVehiculoExitoso() {
        var vehiculo = Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123");

        Assertions.assertEquals(1L, vehiculo.getId());
        Assertions.assertEquals(TipoVehiculo.PARTICULAR, vehiculo.getTipo());
        Assertions.assertEquals("ABC123", vehiculo.getPlaca());
    }

    @Test
    void reconstruirVehiculoSinIdDeberiaLanzarError() {
        BasePrueba.assertThrows(() -> Vehiculo.reconstruir(null, TipoVehiculo.PARTICULAR, "ABC123"),
                ExcepcionValorObligatorio.class,
                "El id del vehiculo es requerido");
    }

    @Test
    void reconstruirVehiculoSinTipoDeberiaLanzarError() {
        BasePrueba.assertThrows(() -> Vehiculo.reconstruir(1L, null, "ABC123"),
                ExcepcionValorObligatorio.class,
                "El tipo del vehiculo es requerido");
    }

    @Test
    void reconstruirVehiculoSinPlacaDeberiaLanzarError() {
        BasePrueba.assertThrows(() -> Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, null),
                ExcepcionValorObligatorio.class,
                "La placa del vehiculo es requerida");
    }
}