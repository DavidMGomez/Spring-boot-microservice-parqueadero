
INSERT INTO vehiculo (tipo,placa) VALUES
	 ('PARTICULAR','FQY112'),
	 ('PARTICULAR','FQY111'),
	 ('PARTICULAR','FQY114');


INSERT INTO registro_estacionamiento (vehiculo_id,fecha_hora_ingreso,fecha_hora_salida,area_estacionamiento_id,monto_pagar,estado) VALUES
	 (1,'2023-07-21 15:45:30','2023-07-21 15:49:00',2,5000.0,'FINALIZADO'),
	 (2,'2023-07-21 15:45:35','2023-07-21 15:49:06',2,5000.0,'FINALIZADO'),
	 (3,'2023-07-21 15:45:40',NULL,2,0.0,'EN_USO');
