package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueadero;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.tarifa.puerto.RepositorioTarifa;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ServicioRegistrarSalida {

    private final RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    private final ServicioCalculadoraParqueadero servicioCalculadoraParqueadero;
    private final RepositorioTarifa repositorioTarifa;
    private final RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;


    public ResumenRegistroEstacionamientoDTO ejecutar(RegistroEstacionamiento regEst, LocalDateTime salida) {
        if (salida.isBefore(regEst.getFechaHoraIngreso())) {
            throw new ExcepcionValorInvalido("La fechaHoraSalida no puede ser menor que la fechaHoraIngreso");
        }
        Tarifa tarifa = repositorioTarifa.obtenerTarifa();
        BigDecimal monto = servicioCalculadoraParqueadero.calcularMonto(regEst, salida, tarifa);
        regEst.registrarSalida(salida, monto);
        repositorioRegistroEstacionamiento.actualizarEstadoYMontoFechaSalida(regEst);
        repositorioAreaEstacionamiento.aumentarCapacidadActual(regEst.getAreaRegistro());
        return new ResumenRegistroEstacionamientoDTO(regEst.getId(),
                regEst.getVehiculoRegistro().getId(),
                regEst.getVehiculoRegistro().getPlaca(),
                regEst.getFechaHoraIngreso(),
                regEst.getFechaHoraSalida(), regEst.getMontoPagar(), regEst.getEstadoRegistro());
    }
}
