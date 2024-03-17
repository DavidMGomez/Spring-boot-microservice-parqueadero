package com.ceiba.registroestacionamiento.modelo.entidad;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.registroestacionamiento.exceptions.ExcepcionRegistroYaFinalizado;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RegistroEstacionamiento")
class RegistroEstacionamientoTest {

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona el registro del vehículo")
    void crearCuandoNoSeProporcionaRegistroVehiculoDeberiaLanzarExcepcion() {
        SolicitudRegistrarEntrada solicitud = new SolicitudRegistrarEntrada(
                null, LocalDateTime.now(),  AreaEstacionamiento.reconstruir(1L,"Area 1", TipoVehiculo.PARTICULAR, 10L,10L));

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> RegistroEstacionamiento.crear(solicitud));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona la fecha y hora de ingreso")
    void crearCuandoNoSeProporcionaFechaHoraIngresoDeberiaLanzarExcepcion() {
        SolicitudRegistrarEntrada solicitud = new SolicitudRegistrarEntrada(
                 Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                null,
               AreaEstacionamiento.reconstruir(1L,"Area 1", TipoVehiculo.PARTICULAR, 10L,10L)
        );

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> RegistroEstacionamiento.crear(solicitud));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona el área de estacionamiento")
    void crearCuandoNoSeProporcionaAreaRegistroDeberiaLanzarExcepcion() {
        SolicitudRegistrarEntrada solicitud = new SolicitudRegistrarEntrada(
                Vehiculo.reconstruir(1L, TipoVehiculo.PARTICULAR, "ABC123"),
                LocalDateTime.now(),
                null
        );

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> RegistroEstacionamiento.crear(solicitud));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona el área de estacionamiento")
    void crearCuandoSeRegistreUnaSalidaDeUnRegistroFinalizadoDeberiaLanzarExcepcion() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.PARTICULAR, 10L);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L, vehiculo, fechaHoraIngreso, fechaHoraIngreso.plusDays(1), areaEstacionamiento, BigDecimal.TEN, EstadoRegistro.FINALIZADO);

        // Act and Assert
        assertThrows(ExcepcionRegistroYaFinalizado.class, () -> registroEstacionamiento.registrarSalida(LocalDateTime.now(),BigDecimal.TEN));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona el área de estacionamiento")
    void crearCuandoSeRegistreUnaSalidaFechaAnteriorAlRegistroFinalizadoDeberiaLanzarExcepcion() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.PARTICULAR, 10L);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L, vehiculo, fechaHoraIngreso, null, areaEstacionamiento, BigDecimal.TEN, EstadoRegistro.EN_USO);

        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> registroEstacionamiento.registrarSalida(fechaHoraIngreso.minusDays(1),BigDecimal.TEN));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando no se proporciona el área de estacionamiento")
    void crearCuandoSeRegistreUnaSalidaMontoCeroLanzarExcepcion() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.PARTICULAR, 10L);
        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.reconstruir(1L, vehiculo, fechaHoraIngreso, fechaHoraIngreso.plusDays(1), areaEstacionamiento, BigDecimal.TEN, EstadoRegistro.EN_USO);

        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> registroEstacionamiento.registrarSalida(fechaHoraIngreso.now(),BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el tipo de vehículo no coincide con el área de estacionamiento")
    void crearCuandoTipoVehiculoNoCoincideConAreaDeberiaLanzarExcepcion() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.MOTOCICLETA, 10L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = new SolicitudRegistrarEntrada(vehiculo, fechaHoraIngreso, areaEstacionamiento);

        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> RegistroEstacionamiento.crear(solicitudRegistrarEntrada));
    }

    @Test
    @DisplayName("Debería crear un nuevo registro de estacionamiento cuando se proporcionan todos los campos requeridos y el tipo de vehículo coincide con el área de estacionamiento")
    void crearCuandoSeProporcionanTodosLosCamposYTipoVehiculoCoincideConAreaDeberiaCrearRegistro() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.PARTICULAR, 10L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = new SolicitudRegistrarEntrada(vehiculo, fechaHoraIngreso, areaEstacionamiento);

        RegistroEstacionamiento registroEstacionamiento = RegistroEstacionamiento.crear(solicitudRegistrarEntrada);

        assertNotNull(registroEstacionamiento);
        assertEquals(vehiculo, registroEstacionamiento.getVehiculoRegistro());
        assertEquals(fechaHoraIngreso, registroEstacionamiento.getFechaHoraIngreso());
        assertEquals(areaEstacionamiento, registroEstacionamiento.getAreaRegistro());
        assertEquals(EstadoRegistro.EN_USO, registroEstacionamiento.getEstadoRegistro());
    }

}