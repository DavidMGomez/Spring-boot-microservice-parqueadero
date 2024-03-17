package com.ceiba.vehiculo.adaptador;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.EjecucionBaseDeDatos;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.vehiculo.entidad.Vehiculo;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioVehiculoMysql implements RepositorioVehiculo {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;
    private final MapeoVehiculo mapeoVehiculo;

    @SqlStatement(namespace = "vehiculo", value = "crear")
    private static String sqlGuardar;

    @SqlStatement(namespace = "vehiculo", value = "obtenerporid")
    private static String sqlObtenerPorId;

    @SqlStatement(namespace = "vehiculo", value = "obtenerporplaca")
    private static String sqlObtenerPorPlaca;

    public RepositorioVehiculoMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate, MapeoVehiculo mapeoVehiculo) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
        this.mapeoVehiculo = mapeoVehiculo;
    }


    @Override
    public Long guardar(Vehiculo vehiculo) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("tipo", vehiculo.getTipo().name());
        paramSource.addValue("placa", vehiculo.getPlaca());
        return this.customNamedParameterJdbcTemplate.crear(paramSource, sqlGuardar);
    }

    @Override
    public Vehiculo obtener(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerPorId, paramSource, mapeoVehiculo));
    }

    @Override
    public Vehiculo obtenerPorPlaca(String placa) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("placa", placa);
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerPorPlaca, paramSource, mapeoVehiculo));
    }
}
