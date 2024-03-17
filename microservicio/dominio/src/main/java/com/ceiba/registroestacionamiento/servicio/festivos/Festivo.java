package com.ceiba.registroestacionamiento.servicio.festivos;

import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;

@Builder
@Getter
public class Festivo {
    private Integer mes;
    private Integer diaDelMes;
    /**
     * Se usa para los festivos que tienen que movere
     * a un dia especifico de la semana (e.g LUNES)
     * use {@code  null} cuando no sea aplicable
     */
    private DayOfWeek moverADia;

    /**
     * Si el festivo es calculado respecto al dia de pascua,
     * este indica el numero de dias que deben sumarse
     * Por defecto es 0
     */
    @Builder.Default
    private Integer diasASumarAPascua = 0;
}
