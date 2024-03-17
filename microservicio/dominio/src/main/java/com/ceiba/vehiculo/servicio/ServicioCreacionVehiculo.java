package com.ceiba.vehiculo.servicio;

import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServicioCreacionVehiculo {
    private final RepositorioVehiculo repositorioVehiculo;

    public Vehiculo crearVehiculo(TipoVehiculo tipoVehiculo, String placa) {
        Vehiculo vehiculo = Vehiculo.crear(tipoVehiculo, placa);
        Long id = repositorioVehiculo.guardar(vehiculo);
        return Vehiculo.reconstruir(id, vehiculo.getTipo(), vehiculo.getPlaca());
    }
}
