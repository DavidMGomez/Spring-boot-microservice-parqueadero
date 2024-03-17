package com.ceiba.tarifa.entidad;

import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tarifa")
class TarifaTest {

    @Test
    @DisplayName("Should throw an exception when the id is not provided")
    void reconstruirWhenIdIsNotProvidedThenThrowException() {
        Long id = null;
        BigDecimal tarifaParticular = BigDecimal.valueOf(10);
        BigDecimal tarifaMotocicleta = BigDecimal.valueOf(5);
        BigDecimal tarifaFestivoMultiplicador = BigDecimal.valueOf(2);

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            Tarifa.reconstruir(id, tarifaParticular, tarifaMotocicleta, tarifaFestivoMultiplicador);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the tarifaMotocicleta is not provided")
    void reconstruirWhenTarifaMotocicletaIsNotProvidedThenThrowException() {
        Long id = 1L;
        BigDecimal tarifaParticular = new BigDecimal("10.00");
        BigDecimal tarifaFestivoMultiplicador = new BigDecimal("1.5");

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            Tarifa.reconstruir(id, tarifaParticular, null, tarifaFestivoMultiplicador);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the tarifaParticular is not provided")
    void reconstruirWhenTarifaParticularIsNotProvidedThenThrowException() {
        Long id = 1L;
        BigDecimal tarifaMotocicleta = new BigDecimal("10.00");
        BigDecimal tarifaFestivoMultiplicador = new BigDecimal("1.5");

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            Tarifa.reconstruir(id, null, tarifaMotocicleta, tarifaFestivoMultiplicador);
        });
    }

    @Test
    @DisplayName("Should throw an exception when the tarifaFestivoMultiplicador is not provided")
    void reconstruirWhenTarifaFestivoMultiplicadorIsNotProvidedThenThrowException() {
        Long id = 1L;
        BigDecimal tarifaParticular = new BigDecimal("10.00");
        BigDecimal tarifaMotocicleta = new BigDecimal("5.00");
        BigDecimal tarifaFestivoMultiplicador = null;

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            Tarifa.reconstruir(id, tarifaParticular, tarifaMotocicleta, tarifaFestivoMultiplicador);
        });
    }

    @Test
    @DisplayName("Should reconstruct the Tarifa object when all parameters are provided")
    void reconstruirWhenAllParametersAreProvided() {
        Long id = 1L;
        BigDecimal tarifaParticular = new BigDecimal("10.00");
        BigDecimal tarifaMotocicleta = new BigDecimal("5.00");
        BigDecimal tarifaFestivoMultiplicador = new BigDecimal("2.00");

        Tarifa tarifa = Tarifa.reconstruir(id, tarifaParticular, tarifaMotocicleta, tarifaFestivoMultiplicador);

        assertNotNull(tarifa);
        assertEquals(id, tarifa.getId());
        assertEquals(tarifaParticular, tarifa.getTarifaParticular());
        assertEquals(tarifaMotocicleta, tarifa.getTarifaMotocicleta());
        assertEquals(tarifaFestivoMultiplicador, tarifa.getTarifaFestivoMultiplicador());
    }

}