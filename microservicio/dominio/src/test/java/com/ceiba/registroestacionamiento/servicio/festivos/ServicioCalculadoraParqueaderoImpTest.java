package com.ceiba.registroestacionamiento.servicio.festivos;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.entidad.SolicitudRegistrarEntrada;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServicioCalculadoraParqueaderoImpTest {
    private ServicioCalculadoraParqueaderoImp servicioCalculadoraParqueaderoImp;
    private Tarifa tarifa;

    @BeforeEach
    void setUp() {
        servicioCalculadoraParqueaderoImp = new ServicioCalculadoraParqueaderoImp();
        tarifa = Tarifa.reconstruir(1L, new BigDecimal(5), new BigDecimal(3), new BigDecimal(2));
    }

    @Test
    @DisplayName("Debería calcular el monto correcto para fechas mixtas (días festivos y no festivos)")
    void calcularMontoParaFechasMixtas() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 8, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 3, 10, 0);
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = new SolicitudRegistrarEntrada(
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                fechaHoraIngreso,
                areaEstacionamiento
        );
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        BigDecimal monto = servicioCalculadoraParqueaderoImp.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);
        assertEquals(BigDecimal.valueOf(330).setScale(5, RoundingMode.HALF_EVEN), monto.setScale(5, RoundingMode.HALF_EVEN));
    }


    @Test
    @DisplayName("Debería calcular el monto correcto para motocicleta")
    void calcularMontoParaMotocicleta() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 8, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.MOTOCICLETA, 10L, 5L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = SolicitudRegistrarEntrada.builder()
                .vehiculoRegistro(Vehiculo.crear(TipoVehiculo.MOTOCICLETA, "ABC123"))
                .fechaHoraIngreso(fechaHoraIngreso)
                .areaRegistro(areaEstacionamiento)
                .build();
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        BigDecimal monto = servicioCalculadoraParqueaderoImp.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);

        assertEquals(BigDecimal.valueOf(12).setScale(5, RoundingMode.HALF_EVEN), monto.setScale(5, RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("Debería calcular el monto correcto para fechas de días festivos")
    void calcularMontoParaFechasFestivas() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0);
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = SolicitudRegistrarEntrada.builder()
                .vehiculoRegistro(Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123"))
                .fechaHoraIngreso(fechaHoraIngreso)
                .areaRegistro(areaEstacionamiento)
                .build();
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        BigDecimal monto = servicioCalculadoraParqueaderoImp.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);

        assertEquals(BigDecimal.valueOf(20).setScale(5, RoundingMode.HALF_EVEN), monto.setScale(5, RoundingMode.HALF_EVEN));
    }


    @Test
    @DisplayName("Debería calcular el monto correcto para fechas no festivas")
    void calcularMontoParaFechasNoFestivas() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.APRIL, 1, 8, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.APRIL, 1, 10, 0);
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L);
        Vehiculo vehiculoRegistro = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = SolicitudRegistrarEntrada.builder()
                .vehiculoRegistro(vehiculoRegistro)
                .fechaHoraIngreso(fechaHoraIngreso)
                .areaRegistro(areaEstacionamiento)
                .build();
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        BigDecimal monto = servicioCalculadoraParqueaderoImp.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);

        assertEquals(BigDecimal.valueOf(10).setScale(5, RoundingMode.HALF_EVEN), monto.setScale(5, RoundingMode.HALF_EVEN));
    }


    @Test
    @DisplayName("Debería calcular el monto correcto para automóvil")
    void calcularMontoParaAutomovil() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 8, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = SolicitudRegistrarEntrada.builder()
                .vehiculoRegistro(Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123"))
                .fechaHoraIngreso(fechaHoraIngreso)
                .areaRegistro(areaEstacionamiento)
                .build();
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        BigDecimal monto = servicioCalculadoraParqueaderoImp.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);

        assertEquals(BigDecimal.valueOf(20).setScale(5, RoundingMode.HALF_EVEN), monto.setScale(5, RoundingMode.HALF_EVEN));
    }

}