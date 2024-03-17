package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueadero;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.tarifa.puerto.RepositorioTarifa;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioRegistrarSalidaTest {

    @Mock
    private RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    @Mock
    private ServicioCalculadoraParqueadero servicioCalculadoraParqueadero;
    @Mock
    private RepositorioTarifa repositorioTarifa;
    @Mock
    private RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    @InjectMocks
    private ServicioRegistrarSalida servicioRegistrarSalida;

    @Test
    @DisplayName("Debería lanzar una excepción cuando la fecha de salida es anterior a la fecha de ingreso")
    void ejecutarCuandoFechaSalidaEsAntesDeFechaIngresoDeberiaLanzarExcepcion() {
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L,
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0),
                null,
                AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L),
                BigDecimal.ZERO, EstadoRegistro.EN_USO
        );
        LocalDateTime salida = LocalDateTime.of(2021, Month.DECEMBER, 31, 10, 0);

        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> {
            servicioRegistrarSalida.ejecutar(registroEstacionamiento, salida);
        });

        verifyNoInteractions(repositorioRegistroEstacionamiento);
        verifyNoInteractions(servicioCalculadoraParqueadero);
        verifyNoInteractions(repositorioTarifa);
        verifyNoInteractions(repositorioAreaEstacionamiento);
    }

    @Test
    @DisplayName("Debería calcular correctamente la tarifa del estacionamiento")
    void ejecutarDeberiaCalcularTarifaEstacionamientoCorrectamente() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0);
        BigDecimal montoPagar = new BigDecimal(10);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L,
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                fechaHoraIngreso,
                null,
                AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L),
                BigDecimal.ZERO, EstadoRegistro.EN_USO
        );
        Tarifa tarifa = Tarifa.reconstruir(1L, new BigDecimal(5), new BigDecimal(3), new BigDecimal(2));
        when(repositorioTarifa.obtenerTarifa()).thenReturn(tarifa);
        when(servicioCalculadoraParqueadero.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa)).thenReturn(montoPagar);
        ResumenRegistroEstacionamientoDTO resumen = servicioRegistrarSalida.ejecutar(registroEstacionamiento, fechaHoraSalida);
        assertNotNull(resumen);
        assertEquals(registroEstacionamiento.getId(), resumen.getId());
        assertEquals(registroEstacionamiento.getVehiculoRegistro().getId(), resumen.getIdVehiculo());
        assertEquals(registroEstacionamiento.getVehiculoRegistro().getPlaca(), resumen.getPlaca());
        assertEquals(registroEstacionamiento.getFechaHoraIngreso(), resumen.getFechaHoraIngreso());
        assertEquals(registroEstacionamiento.getFechaHoraSalida(), resumen.getFechaHoraSalida());
        assertEquals(registroEstacionamiento.getMontoPagar(), resumen.getMontoAPagar());
        assertEquals(registroEstacionamiento.getEstadoRegistro(), resumen.getEstadoRegistro());
        verify(repositorioTarifa, times(1)).obtenerTarifa();
        verify(servicioCalculadoraParqueadero, times(1)).calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);
        verify(repositorioRegistroEstacionamiento, times(1)).actualizarEstadoYMontoFechaSalida(registroEstacionamiento);
        verify(repositorioAreaEstacionamiento, times(1)).aumentarCapacidadActual(registroEstacionamiento.getAreaRegistro());
    }

    @Test
    @DisplayName("Debería ejecutar el servicio de registro de estacionamiento exitosamente cuando la fecha de salida es posterior a la fecha de ingreso")
    void ejecutarCuandoFechaSalidaEsDespuesDeFechaIngreso() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0);
        BigDecimal montoPagar = BigDecimal.valueOf(10);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L,
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                fechaHoraIngreso,
                null,
                AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L),
                BigDecimal.ZERO, EstadoRegistro.EN_USO
        );

        Tarifa tarifa = Tarifa.reconstruir(1L, BigDecimal.valueOf(5), BigDecimal.valueOf(3), BigDecimal.valueOf(2));

        when(repositorioTarifa.obtenerTarifa()).thenReturn(tarifa);
        when(servicioCalculadoraParqueadero.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa)).thenReturn(montoPagar);
        ResumenRegistroEstacionamientoDTO resumen = servicioRegistrarSalida.ejecutar(registroEstacionamiento, fechaHoraSalida);

        assertNotNull(resumen);
        assertEquals(registroEstacionamiento.getId(), resumen.getId());
        assertEquals(registroEstacionamiento.getVehiculoRegistro().getId(), resumen.getIdVehiculo());
        assertEquals(registroEstacionamiento.getVehiculoRegistro().getPlaca(), resumen.getPlaca());
        assertEquals(registroEstacionamiento.getFechaHoraIngreso(), resumen.getFechaHoraIngreso());
        assertEquals(registroEstacionamiento.getFechaHoraSalida(), resumen.getFechaHoraSalida());
        assertEquals(registroEstacionamiento.getMontoPagar(), resumen.getMontoAPagar());
        assertEquals(registroEstacionamiento.getEstadoRegistro(), resumen.getEstadoRegistro());

        verify(repositorioTarifa, times(1)).obtenerTarifa();
        verify(servicioCalculadoraParqueadero, times(1)).calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa);
        verify(repositorioRegistroEstacionamiento, times(1)).actualizarEstadoYMontoFechaSalida(registroEstacionamiento);
        verify(repositorioAreaEstacionamiento, times(1)).aumentarCapacidadActual(registroEstacionamiento.getAreaRegistro());
    }

    @Test
    @DisplayName("Debería aumentar la capacidad actual del área de estacionamiento")
    void ejecutarDeberiaAumentarCapacidadActualAreaEstacionamiento() {
        Long id = 1L;
        String nombre = "Area 1";
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 10L;
        Long capacidadActual = 5L;
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(id, nombre, tipoVehiculo, capacidadMaxima, capacidadActual);

        Long vehiculoId = 1L;
        TipoVehiculo vehiculoTipo = TipoVehiculo.PARTICULAR;
        String placa = "ABC123";
        Vehiculo vehiculo = Vehiculo.reconstruir(vehiculoId, vehiculoTipo, placa);

        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0);
        BigDecimal montoPagar = BigDecimal.valueOf(10);

        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L,
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                fechaHoraIngreso,
                fechaHoraSalida,
                AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L),
                montoPagar, EstadoRegistro.EN_USO
        );

        Tarifa tarifa =  Tarifa.reconstruir(1L, BigDecimal.valueOf(5), BigDecimal.valueOf(3), BigDecimal.valueOf(2));

        when(repositorioTarifa.obtenerTarifa()).thenReturn(tarifa);
        when(servicioCalculadoraParqueadero.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa)).thenReturn(montoPagar);

        ResumenRegistroEstacionamientoDTO resumenRegistroEstacionamientoDTO = servicioRegistrarSalida.ejecutar(registroEstacionamiento, fechaHoraSalida);

        verify(repositorioRegistroEstacionamiento, times(1)).actualizarEstadoYMontoFechaSalida(registroEstacionamiento);
        verify(repositorioAreaEstacionamiento, times(1)).aumentarCapacidadActual(registroEstacionamiento.getAreaRegistro());
        assertEquals(EstadoRegistro.FINALIZADO, registroEstacionamiento.getEstadoRegistro());
        assertEquals(fechaHoraSalida, registroEstacionamiento.getFechaHoraSalida());
        assertEquals(montoPagar, registroEstacionamiento.getMontoPagar());
        assertEquals(areaEstacionamiento.getId(), resumenRegistroEstacionamientoDTO.getId());
        assertEquals(vehiculo.getId(), resumenRegistroEstacionamientoDTO.getIdVehiculo());
        assertEquals(vehiculo.getPlaca(), resumenRegistroEstacionamientoDTO.getPlaca());
        assertEquals(fechaHoraIngreso, resumenRegistroEstacionamientoDTO.getFechaHoraIngreso());
        assertEquals(fechaHoraSalida, resumenRegistroEstacionamientoDTO.getFechaHoraSalida());
        assertEquals(montoPagar, resumenRegistroEstacionamientoDTO.getMontoAPagar());
        assertEquals(EstadoRegistro.FINALIZADO, resumenRegistroEstacionamientoDTO.getEstadoRegistro());
    }

    @Test
    @DisplayName("Debería actualizar el estado y el monto del registro de estacionamiento en la fecha de salida")
    void ejecutarDeberiaActualizarEstadoYMontoRegistroEstacionamientoFechaSalida() {
        Long id = 1L;
        LocalDateTime fechaHoraIngreso = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0);
        LocalDateTime fechaHoraSalida = LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0);
        BigDecimal montoPagar = BigDecimal.valueOf(10);
        Vehiculo vehiculo = Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123");
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 5L);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(id, vehiculo, fechaHoraIngreso, fechaHoraSalida, areaEstacionamiento, montoPagar, EstadoRegistro.EN_USO);

        Tarifa tarifa = Tarifa.reconstruir(1L, BigDecimal.valueOf(5), BigDecimal.valueOf(3), BigDecimal.valueOf(2));

        when(repositorioTarifa.obtenerTarifa()).thenReturn(tarifa);
        when(servicioCalculadoraParqueadero.calcularMonto(registroEstacionamiento, fechaHoraSalida, tarifa)).thenReturn(montoPagar);

        ResumenRegistroEstacionamientoDTO resumenRegistroEstacionamientoDTO = servicioRegistrarSalida.ejecutar(registroEstacionamiento, fechaHoraSalida);

        assertNotNull(resumenRegistroEstacionamientoDTO);
        assertEquals(id, resumenRegistroEstacionamientoDTO.getId());
        assertEquals(vehiculo.getId(), resumenRegistroEstacionamientoDTO.getIdVehiculo());
        assertEquals(vehiculo.getPlaca(), resumenRegistroEstacionamientoDTO.getPlaca());
        assertEquals(fechaHoraIngreso, resumenRegistroEstacionamientoDTO.getFechaHoraIngreso());
        assertEquals(fechaHoraSalida, resumenRegistroEstacionamientoDTO.getFechaHoraSalida());
        assertEquals(montoPagar, resumenRegistroEstacionamientoDTO.getMontoAPagar());
        assertEquals(EstadoRegistro.FINALIZADO, resumenRegistroEstacionamientoDTO.getEstadoRegistro());

        verify(repositorioRegistroEstacionamiento, times(1)).actualizarEstadoYMontoFechaSalida(registroEstacionamiento);
        verify(repositorioAreaEstacionamiento, times(1)).aumentarCapacidadActual(areaEstacionamiento);
    }

}