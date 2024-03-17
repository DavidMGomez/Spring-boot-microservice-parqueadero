UPDATE registro_estacionamiento
SET  monto_pagar = :monto_pagar, estado=:estado, fecha_hora_salida=:fecha_hora_salida
WHERE id=:id;
