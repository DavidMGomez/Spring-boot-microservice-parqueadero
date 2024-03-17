package com.ceiba.tarifa.adaptador.repositorio;

import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.EjecucionBaseDeDatos;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.tarifa.entidad.Tarifa;
import com.ceiba.tarifa.puerto.RepositorioTarifa;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioTarifaMysql implements RepositorioTarifa {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;
    private final MapeoTarifa mapeoTarifa;

    @SqlStatement(namespace = "tarifa", value = "obtenertarifa")
    private static String sqlObtenerTarifa;

    public RepositorioTarifaMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate, MapeoTarifa mapeoTarifa) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
        this.mapeoTarifa = mapeoTarifa;
    }

    @Override
    public Tarifa obtenerTarifa() {
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerTarifa, new MapSqlParameterSource(), mapeoTarifa));
    }
}
