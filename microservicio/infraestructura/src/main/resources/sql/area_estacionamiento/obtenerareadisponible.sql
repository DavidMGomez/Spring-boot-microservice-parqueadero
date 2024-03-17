SELECT id, nombre, tipo_vehiculo, capacidad_maxima, capacidad_actual
FROM area_estacionamiento
WHERE tipo_vehiculo = :tipo_vehiculo AND  capacidad_actual > 0
LIMIT 1