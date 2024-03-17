INSERT INTO registro_estacionamiento
(vehiculo_id, fecha_hora_ingreso, fecha_hora_salida, area_estacionamiento_id, monto_pagar, estado)
VALUES (:vehiculo_id, :fecha_hora_ingreso, null , :area_estacionamiento_id, 0, :estado);
