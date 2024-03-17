package com.ceiba.registroestacionamiento.modelo.dto;

import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResumenRegistroEstacionamientoDTO {
    private Long id;
    private Long idVehiculo;
    private String placa;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private BigDecimal montoAPagar;
    private EstadoRegistro estadoRegistro;
}
