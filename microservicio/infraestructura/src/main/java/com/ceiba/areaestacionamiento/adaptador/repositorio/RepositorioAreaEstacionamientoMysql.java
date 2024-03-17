package com.ceiba.areaestacionamiento.adaptador.repositorio;

import com.ceiba.areaestacionamiento.entidad.AreaEstacionamiento;
import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.EjecucionBaseDeDatos;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import com.ceiba.vehiculo.entidad.TipoVehiculo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioAreaEstacionamientoMysql implements RepositorioAreaEstacionamiento {
    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;
    private final MapeoAreaEstacionamiento mapeoAreaEstacionamiento;

    @SqlStatement(namespace = "area_estacionamiento", value = "obtenerareadisponible")
    private static String sqlObtenerAreaDisponible;

    @SqlStatement(namespace = "area_estacionamiento", value = "crear")
    private static String sqlCrear;

    @SqlStatement(namespace = "area_estacionamiento", value = "obtenerporid")
    private static String sqlObtenerPorId;

    @SqlStatement(namespace = "area_estacionamiento", value = "restarcapacidadactual")
    private static String sqlRestarCapacidadActual;

    @SqlStatement(namespace = "area_estacionamiento", value = "aumentarcapacidadactual")
    private static String sqlAumentarCapacidadActual;

    public RepositorioAreaEstacionamientoMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate, MapeoAreaEstacionamiento mapeoAreaEstacionamiento) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
        this.mapeoAreaEstacionamiento = mapeoAreaEstacionamiento;
    }

    @Override
    public Long crear(AreaEstacionamiento areaEstacionamiento) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("nombre", areaEstacionamiento.getNombre());
        paramSource.addValue("tipo_vehiculo", areaEstacionamiento.getTipoVehiculo().name());
        paramSource.addValue("capacidad_maxima", areaEstacionamiento.getCapacidadMaxima());
        paramSource.addValue("capacidad_actual", areaEstacionamiento.getCapacidadActual());
        return this.customNamedParameterJdbcTemplate.crear(paramSource, sqlCrear);
    }

    @Override
    public AreaEstacionamiento obtenerAreaDisponible(TipoVehiculo tipoVehiculo) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("tipo_vehiculo", tipoVehiculo.name());
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerAreaDisponible, paramSource, mapeoAreaEstacionamiento));
    }

    @Override
    public AreaEstacionamiento obtener(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        return EjecucionBaseDeDatos.obtenerUnObjetoONull(() -> this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate()
                .queryForObject(sqlObtenerPorId, paramSource, mapeoAreaEstacionamiento));
    }

    @Override
    public AreaEstacionamiento restarCapacidadActual(AreaEstacionamiento areaEstacionamiento) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", areaEstacionamiento.getId());
        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlRestarCapacidadActual, paramSource);
        return AreaEstacionamiento.reconstruir(areaEstacionamiento.getId(), areaEstacionamiento.getNombre(), areaEstacionamiento.getTipoVehiculo(),
                areaEstacionamiento.getCapacidadMaxima(), areaEstacionamiento.getCapacidadActual() - 1);
    }

    @Override
    public void aumentarCapacidadActual(AreaEstacionamiento areaEstacionamiento) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", areaEstacionamiento.getId());
        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlAumentarCapacidadActual, paramSource);
    }
}