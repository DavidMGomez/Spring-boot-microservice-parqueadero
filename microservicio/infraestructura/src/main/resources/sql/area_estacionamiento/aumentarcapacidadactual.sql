UPDATE area_estacionamiento
SET  capacidad_actual = capacidad_actual+1
WHERE id=:id;
