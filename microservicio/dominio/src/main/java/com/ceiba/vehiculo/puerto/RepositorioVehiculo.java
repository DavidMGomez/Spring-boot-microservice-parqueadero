package com.ceiba.vehiculo.puerto;


import com.ceiba.vehiculo.entidad.Vehiculo;

public interface RepositorioVehiculo {
    Long guardar(Vehiculo vehiculo);
    Vehiculo obtener(Long id);
    Vehiculo obtenerPorPlaca(String id);

}
