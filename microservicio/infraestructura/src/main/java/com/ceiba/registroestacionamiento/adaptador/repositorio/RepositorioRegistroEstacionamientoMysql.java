package com.ceiba.registroestacionamiento.adaptador.repositorio;


import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.EjecucionBaseDeDatos;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.registroestacionamiento.puerto.repositorio.RepositorioRegistroEstacionamiento;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioRegistroEstacionamientoMysql implements RepositorioRegistroEstacionamiento {
    public static final String ESTADO = "estado";
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    private final MapeoRegistroEstacionamiento mapeoRegistroEstacionamiento;

    @SqlStatement(namespace = "registroestacionamiento", value = "crear")
    private static String sqlCrear;

    @SqlStatement(namespace = "registroestacionamiento", value = "obtenerporid.sql")
    private static String sqlObtenerPorId;

    @SqlStatement(namespace = "registroestacionamiento", value = "actualizarestadoymontofechasalida")
    private static String sqlActualizarEstadoYMonto;

    @SqlStatement(namespace = "registroestacionamiento", value = "obtenerRegistrosPorEstado")
    private static String sqlObtenerResumenRegistrosPorEstado;

    public RepositorioRegistroEstacionamientoMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate, MapeoRegistroEstacionamiento mapeoRegistroEstacionamiento) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
        this.mapeoRegistroEstacionamiento = mapeoRegistroEstacionamiento;
    }


    @Override
    public Long guardar(RegistroEstacionamiento registroEstacionamiento) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("vehiculo_id", registroEstacionamiento.getVehiculoRegistro().getId());
        paramSource.addValue("fecha_hora_ingreso", registroEstacionamiento.getFechaHoraIngreso());
        paramSource.addValue("area_estacionamiento_id", registroEstacionamiento.getAreaRegistro().getId());
        paramSource.addValue(ESTADO, registroEstacionamiento.getEstadoRegistro().name());
        return this.customNamedParameterJdbcTemplate.crear(paramSource, sqlCrear);
    }

    @Override
    public RegistroEstacionamiento obtener(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerPorId, paramSource, mapeoRegistroEstacionamiento));
    }

    @Override
    public void actualizarEstadoYMontoFechaSalida(RegistroEstacionamiento registroEstacionamiento) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", registroEstacionamiento.getId());
        paramSource.addValue(ESTADO, registroEstacionamiento.getEstadoRegistro().name());
        paramSource.addValue("monto_pagar", registroEstacionamiento.getMontoPagar());
        paramSource.addValue("fecha_hora_salida", registroEstacionamiento.getFechaHoraSalida());
        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlActualizarEstadoYMonto, paramSource);
    }

    @Override
    public List<RegistroEstacionamiento> obtenerRegistrosPorEstado(EstadoRegistro estadoRegistro) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue(ESTADO, estadoRegistro.name());
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .query(sqlObtenerResumenRegistrosPorEstado, paramSource, mapeoRegistroEstacionamiento);
    }
}
