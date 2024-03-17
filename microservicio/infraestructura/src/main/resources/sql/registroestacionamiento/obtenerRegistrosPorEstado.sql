SELECT id, vehiculo_id, fecha_hora_ingreso, fecha_hora_salida, area_estacionamiento_id, monto_pagar, estado
FROM registro_estacionamiento where estado = :estado;
