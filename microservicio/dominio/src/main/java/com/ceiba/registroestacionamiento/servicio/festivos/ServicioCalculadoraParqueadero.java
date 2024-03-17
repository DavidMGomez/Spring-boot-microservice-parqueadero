package com.ceiba.registroestacionamiento.servicio.festivos;

import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.tarifa.entidad.Tarifa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ServicioCalculadoraParqueadero {
    BigDecimal calcularMonto(RegistroEstacionamiento registroEstacionamiento, LocalDateTime salida, Tarifa tarifa);
}
