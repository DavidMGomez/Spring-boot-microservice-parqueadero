package com.ceiba.registroestacionamiento.servicio.festivos;

import java.time.LocalDate;

public class ButcherAlgorithm {
    private ButcherAlgorithm() {
        // Non-instantiable class
    }

    public static LocalDate obtenerDiaDePascua(Integer year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = (b + 8) / 25;
        int e = (b - d + 1) / 3;
        int f = (19 * a + b - (b / 4) - e + 15) % 30;
        int g = (32 + 2 * (b % 4) + 2 * (c / 4) - f - (c % 4)) % 7;
        int h = (a + 11 * f + 22 * g) / 451;
        int i = f + g - 7 * h + 114;
        int month = i / 31;
        int day = 1 + (i % 31);
        return LocalDate.of(year, month, day);
    }

}
