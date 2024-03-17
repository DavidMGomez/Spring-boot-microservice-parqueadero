package com.ceiba.registroestacionamiento.servicio.festivos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FestivoUtil {
    private List<LocalDate> festivosPorAnio;
    private Integer anio;

    public FestivoUtil(Integer anio,Festivo[] festivos){
        this.anio = anio;
        llenarFestivos(festivos);
    }

    public boolean esFestivo(LocalDate fecha) {
        return festivosPorAnio.contains(fecha);
    }

    private void llenarFestivos(Festivo[] holidays) {
        this.festivosPorAnio = new ArrayList<>(holidays.length);
        LocalDate diaDePascua = ButcherAlgorithm.obtenerDiaDePascua(this.anio);
        for (Festivo festivo : holidays) {
            LocalDate holidayDate;

            if (festivo.getDiasASumarAPascua() == 0) {
                holidayDate = LocalDate.of(this.anio, festivo.getMes(), festivo.getDiaDelMes());
            } else {
                holidayDate = diaDePascua.plusDays(festivo.getDiasASumarAPascua());
            }
            if (festivo.getMoverADia() != null) {
                moverAlSiguienteDiaDeLaSemana(holidayDate, festivo.getMoverADia());
            }
            festivosPorAnio.add(holidayDate);
        }
    }

    private LocalDate moverAlSiguienteDiaDeLaSemana(LocalDate date, DayOfWeek nextDayOfWeek) {
        DayOfWeek diaDeLaSemanaOriginal = date.getDayOfWeek();
        Integer diferencia = nextDayOfWeek.getValue() - diaDeLaSemanaOriginal.getValue();
        Integer diasASumar = diferencia + (diaDeLaSemanaOriginal.getValue() <= nextDayOfWeek.getValue() ? 0 : 7);
        return date.plusDays(diasASumar);
    }

}
