package com.ceiba.vehiculo.adaptador;

import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import com.ceiba.vehiculo.entidad.Vehiculo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class MapeoVehiculo implements RowMapper<Vehiculo>, MapperResult {

    @Override
    public Vehiculo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        TipoVehiculo tipo = TipoVehiculo.valueOf(rs.getString("tipo"));
        String placa = rs.getString("placa");
        return Vehiculo.reconstruir(id, tipo, placa);
    }
}
