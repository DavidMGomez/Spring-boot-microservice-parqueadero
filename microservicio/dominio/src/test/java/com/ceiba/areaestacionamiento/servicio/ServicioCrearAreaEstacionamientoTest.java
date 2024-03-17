package com.ceiba.areaestacionamiento.servicio;

import com.ceiba.areaestacionamiento.entidad.SolicitudCrearAreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioCrearAreaEstacionamientoTest {

    @Mock
    private RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    @InjectMocks
    private ServicioCrearAreaEstacionamiento servicioCrearAreaEstacionamiento;

    @Test
    @DisplayName("Debería lanzar una excepción cuando la solicitud es nula")
    void ejecutarConSolicitudNulaLanzaExcepcion() {
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCrearAreaEstacionamiento.ejecutar(null);
        });
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el tipo de vehículo es nulo o inválido")
    void ejecutarConTipoDeVehiculoNuloOInvalidoLanzaExcepcion() {
        String nombre = "Area1";
        TipoVehiculo tipoVehiculo = null;
        Long capacidadMaxima = 10L;
        SolicitudCrearAreaEstacionamiento solicitud = new SolicitudCrearAreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCrearAreaEstacionamiento.ejecutar(solicitud);
        });
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando la capacidad máxima es menor o igual a cero")
    void ejecutarConCapacidadMaximaNoPositivaLanzaExcepcion() {
        String nombre = "Area 1";
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 0L;
        SolicitudCrearAreaEstacionamiento solicitud = new SolicitudCrearAreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);
        // Act and Assert
        assertThrows(ExcepcionValorInvalido.class, () -> {
            servicioCrearAreaEstacionamiento.ejecutar(solicitud);
        });
    }

    @Test
    @DisplayName("Debería crear un área de estacionamiento y devolver su identificación cuando la solicitud es válida")
    void ejecutarConSolicitudValida() {
        String nombre = "Area1";
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 10L;
        SolicitudCrearAreaEstacionamiento solicitud = new SolicitudCrearAreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);
        Long expectedId = 1L;

        when(repositorioAreaEstacionamiento.crear(any())).thenReturn(expectedId);

        Long actualId = servicioCrearAreaEstacionamiento.ejecutar(solicitud);

        assertEquals(expectedId, actualId);
    }

    @Test
    @DisplayName("Debería lanzar una excepción cuando el nombre del área de estacionamiento es nulo o vacío")
    void ejecutarConNombreNuloOVacioLanzaExcepcion() {
        String nombre = null;
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        Long capacidadMaxima = 10L;
        SolicitudCrearAreaEstacionamiento solicitud = new SolicitudCrearAreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);

        // Act and Assert
        SolicitudCrearAreaEstacionamiento finalSolicitud = solicitud;
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCrearAreaEstacionamiento.ejecutar(finalSolicitud);
        });

        nombre = null;
        solicitud = new SolicitudCrearAreaEstacionamiento(nombre, tipoVehiculo, capacidadMaxima);

        // Act and Assert
        SolicitudCrearAreaEstacionamiento finalSolicitud1 = solicitud;
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCrearAreaEstacionamiento.ejecutar(finalSolicitud1);
        });
    }

}