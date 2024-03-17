package com.ceiba.areaestacionamiento.puerto;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;

public interface RepositorioAreaEstacionamiento {
    Long crear(AreaEstacionamiento areaEstacionamiento);
    AreaEstacionamiento obtenerAreaDisponible(TipoVehiculo tipoVehiculo);
    AreaEstacionamiento obtener(Long id);
    AreaEstacionamiento restarCapacidadActual(AreaEstacionamiento areaEstacionamiento);
    void aumentarCapacidadActual(AreaEstacionamiento areaEstacionamiento);

}
