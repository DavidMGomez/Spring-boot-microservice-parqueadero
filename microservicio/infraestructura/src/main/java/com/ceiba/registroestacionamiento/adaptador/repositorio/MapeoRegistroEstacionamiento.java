package com.ceiba.registroestacionamiento.adaptador.repositorio;


import com.ceiba.areaestacionamiento.puerto.RepositorioAreaEstacionamiento;
import com.ceiba.infraestructura.jdbc.MapperResult;
import com.ceiba.registroestacionamiento.modelo.entidad.EstadoRegistro;
import com.ceiba.registroestacionamiento.modelo.entidad.RegistroEstacionamiento;
import com.ceiba.vehiculo.puerto.RepositorioVehiculo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class MapeoRegistroEstacionamiento implements RowMapper<RegistroEstacionamiento>, MapperResult {

    private final RepositorioVehiculo repositorioVehiculo;
    private final RepositorioAreaEstacionamiento repositorioAreaEstacionamiento;

    public MapeoRegistroEstacionamiento(RepositorioVehiculo repositorioVehiculo, RepositorioAreaEstacionamiento repositorioAreaEstacionamiento) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioAreaEstacionamiento = repositorioAreaEstacionamiento;
    }


    @Override
    public RegistroEstacionamiento mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        long idVehiculo = rs.getLong("vehiculo_id");
        Long idAreaEstacionamiento = rs.getLong("area_estacionamiento_id");
        LocalDateTime fechaHoraIngreso = rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime();
        Timestamp timestampSalida = rs.getTimestamp("fecha_hora_salida");
        EstadoRegistro estado = EstadoRegistro.valueOf(rs.getString("estado"));
        LocalDateTime fechaHoraSalida = null;
        BigDecimal montoPagar = rs.getBigDecimal("monto_pagar");
        if (timestampSalida != null) {
            fechaHoraSalida = timestampSalida.toLocalDateTime();
        }
        return RegistroEstacionamiento.reconstruir(id,
                repositorioVehiculo.obtener(idVehiculo),
                fechaHoraIngreso,
                fechaHoraSalida, repositorioAreaEstacionamiento.obtener(idAreaEstacionamiento), montoPagar, estado);
    }
}
