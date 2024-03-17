package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.modelo.entidad.SolicitudRegistrarEntrada;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioRegistroEntradaTest {
    private ServicioRegistroEntrada servicioRegistroEntrada;
    private RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    private RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    @BeforeEach
    void setUp() {
        repositorioRegistroEstacionamiento = mock(RepositorioRegistroEstacionamiento.class);
        repositorioAreaEstacionamiento = mock(RepositorioAreaEstacionamiento.class);
        servicioRegistroEntrada = new ServicioRegistroEntrada(repositorioRegistroEstacionamiento, repositorioAreaEstacionamiento);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la capacidad del área de estacionamiento es cero")
    void ejecutarCuandoCapacidadAreaEstacionamientoEsCeroDeberiaLanzarExcepcion() {
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.crear("Area 1", TipoVehiculo.PARTICULAR, 0L);
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = new SolicitudRegistrarEntrada(vehiculo, fechaHoraIngreso, areaEstacionamiento);

        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> {
            servicioRegistroEntrada.ejecutar(solicitudRegistrarEntrada);
        });
    }

    @Test
    @DisplayName("Debería ejecutar el servicio, disminuir la capacidad del área de estacionamiento y devolver el ID del registro de estacionamiento")
    void ejecutarDisminuirCapacidadAreaEstacionamientoYDevolverId() {
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        AreaEstacionamiento areaEstacionamiento = AreaEstacionamiento.reconstruir(1L,"Area 1", TipoVehiculo.PARTICULAR, 10L,10L);
        Vehiculo vehiculo = Vehiculo.crear(TipoVehiculo.PARTICULAR, "ABC123");
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = new SolicitudRegistrarEntrada(vehiculo, fechaHoraIngreso, areaEstacionamiento);
        Long expectedId = 1L;

        when(repositorioRegistroEstacionamiento.guardar(any())).thenReturn(expectedId);

        Long actualId = servicioRegistroEntrada.ejecutar(solicitudRegistrarEntrada);

        assertEquals(expectedId, actualId);
        verify(repositorioAreaEstacionamiento, times(1)).restarCapacidadActual(areaEstacionamiento);
        verify(repositorioRegistroEstacionamiento, times(1)).guardar(any(RegistroEstacionamiento.class));
    }

}