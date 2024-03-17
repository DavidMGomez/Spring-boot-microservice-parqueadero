package com.ceiba.tarifa.entidad;

import com.ceiba.dominio.ValidadorArgumento;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Tarifa {
    private final Long id;
    private final BigDecimal tarifaParticular;
    private final BigDecimal tarifaMotocicleta;
    private final BigDecimal tarifaFestivoMultiplicador;

    private Tarifa(Long id, BigDecimal tarifaParticular, BigDecimal tarifaMotocicleta, BigDecimal tarifaFestivoMultiplicador) {
        this.id = id;
        this.tarifaParticular = tarifaParticular;
        this.tarifaMotocicleta = tarifaMotocicleta;
        this.tarifaFestivoMultiplicador = tarifaFestivoMultiplicador;
    }

    public static Tarifa reconstruir(Long id, BigDecimal tarifaParticular, BigDecimal tarifaMotocicleta, BigDecimal tarifaFestivoMultiplicador) {
        ValidadorArgumento.validarObligatorio(id, "El id de Tarifa es requerido");
        ValidadorArgumento.validarObligatorio(tarifaParticular, "La tarifaParticular de Tarifa es requerida");
        ValidadorArgumento.validarObligatorio(tarifaMotocicleta, "La tarifaMotocicleta del Tarifa es requerida");
        ValidadorArgumento.validarObligatorio(tarifaFestivoMultiplicador, "La tarifaFestivoMultiplicador del Tarifa es requerida");
        return new Tarifa(id, tarifaParticular, tarifaMotocicleta, tarifaFestivoMultiplicador);
    }

}
