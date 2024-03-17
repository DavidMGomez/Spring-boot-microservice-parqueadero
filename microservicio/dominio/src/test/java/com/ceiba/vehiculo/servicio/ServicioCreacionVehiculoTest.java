package com.ceiba.vehiculo.servicio;

import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioCreacionVehiculoTest {

    @Mock
    private RepositorioVehiculo repositorioVehiculo;

    @InjectMocks
    private ServicioCreacionVehiculo servicioCreacionVehiculo;

    @Test
    @DisplayName("Should throw an exception when the vehicle type is null")
    void crearVehiculoWhenTipoVehiculoIsNullThenThrowException() {
        String placa = "ABC123";

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCreacionVehiculo.crearVehiculo(null, placa);
        });

        verifyNoInteractions(repositorioVehiculo);
    }

    @Test
    @DisplayName("Should throw an exception when the vehicle plate is null or empty")
    void crearVehiculoWhenPlacaIsNullThenThrowException() {
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        String placa = null;

        // Act and Assert
        assertThrows(ExcepcionValorObligatorio.class, () -> {
            servicioCreacionVehiculo.crearVehiculo(tipoVehiculo, placa);
        });
    }

    @Test
    @DisplayName("Should create a vehicle and return it with the assigned id")
    void crearVehiculoWithAssignedId() {
        TipoVehiculo tipoVehiculo = TipoVehiculo.PARTICULAR;
        String placa = "ABC123";
        Long id = 1L;
        when(repositorioVehiculo.guardar(any(Vehiculo.class))).thenReturn(id);
        Vehiculo result = servicioCreacionVehiculo.crearVehiculo(tipoVehiculo, placa);
        assertEquals(id, result.getId());
        assertEquals(tipoVehiculo, result.getTipo());
        assertEquals(placa, result.getPlaca());
        verify(repositorioVehiculo, times(1)).guardar(any(Vehiculo.class));
    }

}