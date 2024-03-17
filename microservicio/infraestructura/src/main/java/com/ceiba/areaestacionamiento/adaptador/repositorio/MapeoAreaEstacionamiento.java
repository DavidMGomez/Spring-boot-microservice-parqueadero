package com.ceiba.areaestacionamiento.adaptador.repositorio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapeoAreaEstacionamiento implements RowMapper<AreaEstacionamiento> {
    @Override
    public AreaEstacionamiento mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String nombre = rs.getString("nombre");
        TipoVehiculo tipoVehiculo = TipoVehiculo.valueOf(rs.getString("tipo_vehiculo"));
        Long capacidadMaxima = rs.getLong("capacidad_maxima");
        Long capacidadActual = rs.getLong("capacidad_actual");
        return AreaEstacionamiento.reconstruir(id, nombre, tipoVehiculo, capacidadMaxima, capacidadActual);
    }
}
