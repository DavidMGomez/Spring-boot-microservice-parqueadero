-- Tabla para áreas de estacionamiento
CREATE TABLE area_estacionamiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo_vehiculo VARCHAR(20) NOT NULL,
    capacidad_maxima INT NOT NULL,
    capacidad_actual INT NOT NULL
);

-- Tabla para vehículos
CREATE TABLE vehiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    placa VARCHAR(20) NOT NULL
);

-- Tabla para registrar el uso de un estacionamiento
CREATE TABLE registro_estacionamiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehiculo_id INT,
    fecha_hora_ingreso DATETIME NOT NULL,
    fecha_hora_salida DATETIME,
    area_estacionamiento_id INT,
    monto_pagar DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculo(id),
    FOREIGN KEY (area_estacionamiento_id) REFERENCES area_estacionamiento(id)
);

-- Tabla para tarifas
CREATE TABLE tarifa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tarifa_particular DOUBLE NOT NULL,
    tarifa_motocicleta DOUBLE NOT NULL,
    tarifa_festivo_multiplicador DOUBLE NOT NULL
);

INSERT INTO area_estacionamiento (nombre,tipo_vehiculo,capacidad_maxima,capacidad_actual) VALUES
	 ('PISO1','MOTOCICLETA',20,20),
	 ('PISO2','PARTICULAR',20,20),
	 ('Piso2','PARTICULAR',20,20);

INSERT INTO tarifa (tarifa_particular,tarifa_motocicleta,tarifa_festivo_multiplicador) VALUES
	 (5000.0,3000.0,1.2);

