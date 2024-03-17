package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueadero;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.tarifa.puerto.RepositorioTarifa;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ServicioConsultaRegistroEstacionamientoTest {
    private ServicioConsultaRegistroEstacionamiento servicioConsultaRegistroEstacionamiento;
    private RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    private ServicioCalculadoraParqueadero servicioCalculadoraParqueadero;
    private RepositorioTarifa repositorioTarifa;

    @BeforeEach
    void setUp() {
        repositorioRegistroEstacionamiento = mock(RepositorioRegistroEstacionamiento.class);
        servicioCalculadoraParqueadero = mock(ServicioCalculadoraParqueadero.class);
        repositorioTarifa = mock(RepositorioTarifa.class);
        servicioConsultaRegistroEstacionamiento = new ServicioConsultaRegistroEstacionamiento(repositorioRegistroEstacionamiento,
                servicioCalculadoraParqueadero, repositorioTarifa);
    }


    @Test
    @DisplayName("Should return an empty list when there are no parking records for a given state")
    void consultarRegistrosEstacionamientoPorEstadoReturnsEmptyListWhenNoRecords() {
        EstadoRegistro estadoRegistro = EstadoRegistro.EN_USO;
        when(repositorioRegistroEstacionamiento.obtenerRegistrosPorEstado(estadoRegistro)).thenReturn(List.of());
        List<ResumenRegistroEstacionamientoDTO> registrosEstacionamiento = servicioConsultaRegistroEstacionamiento.consultarRegistrosEstacionamientoPorEstado(estadoRegistro);
        assertTrue(registrosEstacionamiento.isEmpty());
        verify(repositorioRegistroEstacionamiento, times(1)).obtenerRegistrosPorEstado(estadoRegistro);
    }

    @Test
    @DisplayName("Debería calcular el monto a pagar para cada registro de estacionamiento que no ha finalizado")
    void consultarRegistrosEstacionamientoPorEstadoCalculaMontoAPagarParaRegistrosNoFinalizados() {
        RegistroEstacionamiento registro1 = RegistroEstacionamiento.reconstruir(
                1L, Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"), LocalDateTime.now().minusHours(2),
                null, AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 10L), null, EstadoRegistro.EN_USO);
        RegistroEstacionamiento registro2 = RegistroEstacionamiento.reconstruir(
                2L, Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "DEF456"), LocalDateTime.now().minusHours(1),
                null, AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.MOTOCICLETA, 10L, 10L), null, EstadoRegistro.EN_USO);
        RegistroEstacionamiento registro3 = RegistroEstacionamiento.reconstruir(
                3L, Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "GHI789"), LocalDateTime.now().minusHours(3),
                null, AreaEstacionamiento.reconstruir(1L, "Area 1", TipoVehiculo.PARTICULAR, 10L, 10L), null, EstadoRegistro.EN_USO);
        List<RegistroEstacionamiento> registros = List.of(registro1, registro2, registro3);

        when(repositorioTarifa.obtenerTarifa()).thenReturn(Tarifa.reconstruir(1L, BigDecimal.valueOf(3), BigDecimal.valueOf(1), BigDecimal.valueOf(1.2)));
        when(repositorioRegistroEstacionamiento.obtenerRegistrosPorEstado(EstadoRegistro.EN_USO)).thenReturn(registros);
        when(servicioCalculadoraParqueadero.calcularMonto(any(RegistroEstacionamiento.class), any(LocalDateTime.class), any())).thenReturn(BigDecimal.ONE);
        List<ResumenRegistroEstacionamientoDTO> resumenRegistroEstacionamientoDTOS = servicioConsultaRegistroEstacionamiento.consultarRegistrosEstacionamientoPorEstado(EstadoRegistro.EN_USO);
        assertEquals(3, resumenRegistroEstacionamientoDTOS.size());
        verify(servicioCalculadoraParqueadero, times(3)).calcularMonto(any(RegistroEstacionamiento.class), any(LocalDateTime.class), any(Tarifa.class));
    }

}