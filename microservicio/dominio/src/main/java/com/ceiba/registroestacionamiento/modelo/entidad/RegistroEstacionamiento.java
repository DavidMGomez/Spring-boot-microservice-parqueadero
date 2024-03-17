package com.ceiba.registroestacionamiento.modelo.entidad;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.dominio.ValidadorArgumento;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.registroestacionamiento.exceptions.ExcepcionRegistroYaFinalizado;
import com.ceiba.vehiculo.entidad.Vehiculo;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class RegistroEstacionamiento {

    private Long id;
    private final Vehiculo vehiculoRegistro;
    private final LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private final AreaEstacionamiento areaRegistro;
    private BigDecimal montoPagar;
    private EstadoRegistro estadoRegistro;

    private RegistroEstacionamiento(Vehiculo vehiculoRegistro, LocalDateTime fechaHoraIngreso,
                                    AreaEstacionamiento areaRegistro) {
        this.vehiculoRegistro = vehiculoRegistro;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.areaRegistro = areaRegistro;
        this.estadoRegistro = EstadoRegistro.EN_USO;
    }

    private RegistroEstacionamiento(Long id, Vehiculo vehiculoRegistro, LocalDateTime fechaHoraIngreso,
                                    LocalDateTime fechaHoraSalida, AreaEstacionamiento areaRegistro,
                                    BigDecimal montoPagar, EstadoRegistro estadoRegistro) {
        this.id = id;
        this.vehiculoRegistro = vehiculoRegistro;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
        this.areaRegistro = areaRegistro;
        this.montoPagar = montoPagar;
        this.estadoRegistro = estadoRegistro;
    }

    public static RegistroEstacionamiento crear(SolicitudRegistrarEntrada solicitudRegistrarEntrada) {
        ValidadorArgumento.validarObligatorio(solicitudRegistrarEntrada.getAreaRegistro(), "El area de registro  de RegistroEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(solicitudRegistrarEntrada.getVehiculoRegistro(), "El vehiculoRegistro de RegistroEstacionamiento  es requerido");
        ValidadorArgumento.validarObligatorio(solicitudRegistrarEntrada.getFechaHoraIngreso(), "la fechaHoraIngreso del RegistroEstacionamiento es requerida");

        if (solicitudRegistrarEntrada.getAreaRegistro().getTipoVehiculo() != solicitudRegistrarEntrada.getVehiculoRegistro().getTipo()) {
            throw new ExcepcionValorInvalido("Esa area no admite ese tipo de vehiculos");
        }

        return new RegistroEstacionamiento(solicitudRegistrarEntrada.getVehiculoRegistro(),
                solicitudRegistrarEntrada.getFechaHoraIngreso(), solicitudRegistrarEntrada.getAreaRegistro());
    }

    public static RegistroEstacionamiento reconstruir(Long id, Vehiculo vehiculoRegistro, LocalDateTime fechaHoraIngreso,
                                                      LocalDateTime fechaHoraSalida, AreaEstacionamiento areaRegistro,
                                                      BigDecimal montoPagar, EstadoRegistro estadoRegistro) {
        ValidadorArgumento.validarObligatorio(id, "El id de RegistroEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(vehiculoRegistro, "El vehiculoRegistro de RegistroEstacionamiento es requerido");
        ValidadorArgumento.validarObligatorio(fechaHoraIngreso, "La fechaHoraIngreso del RegistroEstacionamiento es requerida");
        ValidadorArgumento.validarObligatorio(areaRegistro, "la areaRegistro del RegistroEstacionamiento es requerida");
        ValidadorArgumento.validarObligatorio(estadoRegistro, "la estadoRegistro del RegistroEstacionamiento es requerida");
        return new RegistroEstacionamiento(id, vehiculoRegistro, fechaHoraIngreso, fechaHoraSalida, areaRegistro, montoPagar, estadoRegistro);
    }


    public void registrarSalida(LocalDateTime fechaHoraSalida, BigDecimal montoPagar) {
        if (estaFinalizado()) {
            throw new ExcepcionRegistroYaFinalizado("El registro ya se ha procesado");
        }
        if (fechaHoraSalida.isBefore(this.fechaHoraIngreso)) {
            throw new ExcepcionValorInvalido("La fechaHoraSalida no puede ser menor que la fechaHoraIngreso");
        }
        this.fechaHoraSalida = fechaHoraSalida;
        if (montoPagar.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcepcionValorInvalido("El montoPagar no puede ser menor a cero");
        }
        this.montoPagar = montoPagar;
        this.estadoRegistro = EstadoRegistro.FINALIZADO;
    }

    public boolean estaFinalizado() {
        return this.getEstadoRegistro().equals(EstadoRegistro.FINALIZADO);
    }

}
