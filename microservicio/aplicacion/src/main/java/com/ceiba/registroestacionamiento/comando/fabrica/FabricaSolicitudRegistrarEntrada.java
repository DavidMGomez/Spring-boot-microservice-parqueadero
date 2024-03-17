package com.ceiba.registroestacionamiento.comando.fabrica;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarIngreso;
import com.ceiba.registroestacionamiento.modelo.entidad.SolicitudRegistrarEntrada;
import com.ceiba.vehiculo.entidad.Vehiculo;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FabricaSolicitudRegistrarEntrada {
    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    public FabricaSolicitudRegistrarEntrada(RepositorioVehiculo repositorioVehiculo, RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioAreaEstacionamiento = repositorioAreaEstacionamiento;
    }

    public SolicitudRegistrarEntrada crear(ComandoSolicitudRegistrarIngreso comandoSolicitudRegistrarIngreso) {
        LocalDateTime fechaHoraIngreso = LocalDateTime.now();
        Vehiculo vehiculo = repositorioVehiculo.obtenerPorPlaca(comandoSolicitudRegistrarIngreso.getPlacaVehiculo());
        AreaEstacionamiento areaEstacionamiento = repositorioAreaEstacionamiento.obtenerAreaDisponible(comandoSolicitudRegistrarIngreso.getTipoVehiculo());
        return new SolicitudRegistrarEntrada(vehiculo, fechaHoraIngreso, areaEstacionamiento);
    }
}
