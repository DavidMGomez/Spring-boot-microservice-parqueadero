package com.ceiba.tarifa.adaptador.repositorio;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.tarifa.entidad.Tarifa;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapeoTarifa implements RowMapper<Tarifa>, MapperResult {
    @Override
    public Tarifa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        BigDecimal tarifaParticular = rs.getBigDecimal("tarifa_particular");
        BigDecimal tarifaMotocicleta = rs.getBigDecimal("tarifa_motocicleta");
        BigDecimal tarifaFestivoMultiplicador = rs.getBigDecimal("tarifa_festivo_multiplicador");
        return Tarifa.reconstruir(id, tarifaParticular, tarifaMotocicleta, tarifaFestivoMultiplicador);
    }
}
