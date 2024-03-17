package com.ceiba.registroestacionamiento.servicio;

import com.ceiba.registroestacionamiento.modelo.dto.ResumenRegistroEstacionamientoDTO;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import com.ceiba.registroestacionamiento.servicio.festivos.ServicioCalculadoraParqueadero;
import com.ceiba.tarifa.puerto.RepositorioTarifa;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioConsultaRegistroEstacionamiento {
    private final RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento;
    private final ServicioCalculadoraParqueadero servicioCalculadoraParqueadero;
    private final RepositorioTarifa repositorioTarifa;


    public ServicioConsultaRegistroEstacionamiento(RepositorioRegistroEstacionamiento repositorioRegistroEstacionamiento,
                                                   ServicioCalculadoraParqueadero servicioCalculadoraParqueadero,
                                                   RepositorioTarifa repositorioTarifa) {
        this.repositorioRegistroEstacionamiento = repositorioRegistroEstacionamiento;
        this.servicioCalculadoraParqueadero = servicioCalculadoraParqueadero;
        this.repositorioTarifa = repositorioTarifa;
    }

    public List<ResumenRegistroEstacionamientoDTO> consultarRegistrosEstacionamientoPorEstado(EstadoRegistro estadoRegistro) {
        List<RegistroEstacionamiento> registroEstacionamientos = repositorioRegistroEstacionamiento.obtenerRegistrosPorEstado(estadoRegistro);
        return registroEstacionamientos.stream().map(this::crearResumenResgistroEstacionamiento).collect(Collectors.toList());
    }

    private ResumenRegistroEstacionamientoDTO crearResumenResgistroEstacionamiento(RegistroEstacionamiento registroEstacionamiento) {
        BigDecimal montoAPagar = registroEstacionamiento.estaFinalizado() ? registroEstacionamiento.getMontoPagar() :
                servicioCalculadoraParqueadero.calcularMonto(registroEstacionamiento, LocalDateTime.now(), repositorioTarifa.obtenerTarifa());
        return new ResumenRegistroEstacionamientoDTO(registroEstacionamiento.getId(),
                registroEstacionamiento.getVehiculoRegistro().getId(),
                registroEstacionamiento.getVehiculoRegistro().getPlaca(),
                registroEstacionamiento.getFechaHoraIngreso(),
                registroEstacionamiento.getFechaHoraSalida(),
                montoAPagar, registroEstacionamiento.getEstadoRegistro());
    }
}
