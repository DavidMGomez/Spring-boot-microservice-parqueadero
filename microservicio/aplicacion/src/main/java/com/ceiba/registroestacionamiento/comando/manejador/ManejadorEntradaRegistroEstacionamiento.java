package com.ceiba.registroestacionamiento.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import com.ceiba.registroestacionamiento.comando.ComandoSolicitudRegistrarIngreso;
import com.ceiba.registroestacionamiento.comando.fabrica.FabricaSolicitudRegistrarEntrada;
import com.ceiba.registroestacionamiento.modelo.entidad.SolicitudRegistrarEntrada;
import com.ceiba.registroestacionamiento.servicio.ServicioRegistroEntrada;
import com.ceiba.vehiculo.entidad.Vehiculo;
import com.ceiba.vehiculo.servicio.ServicioCreacionVehiculo;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEntradaRegistroEstacionamiento implements ManejadorComandoRespuesta<ComandoSolicitudRegistrarIngreso, ComandoRespuesta<Long>> {
    private final ServicioRegistroEntrada servicioRegistroEntrada;
    private final ServicioCreacionVehiculo servicioCreacionVehiculo;
    private final FabricaSolicitudRegistrarEntrada fabricaSolicitudRegistrarEntrada;

    public ManejadorEntradaRegistroEstacionamiento(ServicioRegistroEntrada servicioRegistroEntrada, ServicioCreacionVehiculo servicioCreacionVehiculo, FabricaSolicitudRegistrarEntrada fabricaSolicitudRegistrarEntrada) {
        this.servicioRegistroEntrada = servicioRegistroEntrada;
        this.servicioCreacionVehiculo = servicioCreacionVehiculo;
        this.fabricaSolicitudRegistrarEntrada = fabricaSolicitudRegistrarEntrada;
    }

    @Override
    public ComandoRespuesta<Long> ejecutar(ComandoSolicitudRegistrarIngreso comando) {
        SolicitudRegistrarEntrada solicitudRegistrarEntrada = fabricaSolicitudRegistrarEntrada.crear(comando);
        if (solicitudRegistrarEntrada.getVehiculoRegistro() == null) {
            Vehiculo vehiculo = servicioCreacionVehiculo.crearVehiculo(comando.getTipoVehiculo(), comando.getPlacaVehiculo());
            solicitudRegistrarEntrada.setVehiculoRegistro(vehiculo);
        }
        return new ComandoRespuesta<>(servicioRegistroEntrada.ejecutar(solicitudRegistrarEntrada));
    }
}
