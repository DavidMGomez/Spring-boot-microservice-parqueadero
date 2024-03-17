package com.ceiba.registroestacionamiento.servicio.festivos;

import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.vehiculo.entidad.TipoVehiculo;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class ServicioCalculadoraParqueaderoImp implements ServicioCalculadoraParqueadero {
    private Integer anioCalculado;
    private FestivoUtil festivoUtil;

    private final static Festivo[] FESTIVOS = {
            Festivo.builder().mes(1).diaDelMes(1).build(),
            Festivo.builder().mes(5).diaDelMes(1).build(),
            Festivo.builder().mes(7).diaDelMes(20).build(),
            Festivo.builder().mes(8).diaDelMes(07).build(),
            Festivo.builder().mes(12).diaDelMes(8).build(),
            Festivo.builder().mes(12).diaDelMes(25).build(),
            Festivo.builder().mes(01).diaDelMes(06).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(03).diaDelMes(19).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(06).diaDelMes(29).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(8).diaDelMes(15).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(10).diaDelMes(12).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(11).diaDelMes(01).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().mes(11).diaDelMes(11).moverADia(DayOfWeek.MONDAY).build(),
            // festivos respecto a el dia de pascua 
            Festivo.builder().diasASumarAPascua(-3).build(),
            Festivo.builder().diasASumarAPascua(-2).build(),
            Festivo.builder().diasASumarAPascua(39).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().diasASumarAPascua(60).moverADia(DayOfWeek.MONDAY).build(),
            Festivo.builder().diasASumarAPascua(68).moverADia(DayOfWeek.MONDAY).build()
    };


    @Override
    public BigDecimal calcularMonto(RegistroEstacionamiento registroEstacionamiento, LocalDateTime salida, Tarifa tarifa) {
        BigDecimal monto = BigDecimal.ZERO;
        BigDecimal valorTarifa = getValorTarifa(registroEstacionamiento, tarifa);
        LocalDateTime entrada = registroEstacionamiento.getFechaHoraIngreso();
        LocalDate fechaActual = entrada.truncatedTo(ChronoUnit.DAYS).toLocalDate();
        LocalDateTime fachaHoraActual = entrada;
        while (fechaActual.isBefore(salida.toLocalDate()) || fechaActual.isEqual(salida.toLocalDate())) {
            Double horas;
            if (fechaActual.isEqual(salida.toLocalDate())) {

                horas = Math.ceil((double) Duration.between(fachaHoraActual, salida).toMillis() / 3600000);
            } else {
                horas = Math.ceil(((double) Duration.between(fachaHoraActual, fechaActual.plusDays(1).atStartOfDay()).toMillis() / 3600000));
            }
            BigDecimal valorHora = valorTarifa;
            if (esFestivo(fechaActual)) {
                valorHora = valorHora.multiply(tarifa.getTarifaFestivoMultiplicador());
            }
            BigDecimal valor = BigDecimal.valueOf(horas).multiply(valorHora);
            monto = monto.add(valor);
            fechaActual = fechaActual.plusDays(1);
            fachaHoraActual = fechaActual.atStartOfDay();
        }
        return monto;
    }

    public boolean esFestivo(LocalDate fecha) {
        if (this.anioCalculado == null || this.anioCalculado != fecha.getYear()) {
            this.anioCalculado = fecha.getYear();
            this.festivoUtil = new FestivoUtil(fecha.getYear(), FESTIVOS);
        }

        return festivoUtil.esFestivo(fecha);
    }

    private static BigDecimal getValorTarifa(RegistroEstacionamiento registroEstacionamiento, Tarifa tarifa) {
        return registroEstacionamiento.getVehiculoRegistro().getTipo().equals(TipoVehiculo.MOTOCICLETA) ? tarifa.getTarifaMotocicleta() : tarifa.getTarifaParticular();
    }
}
