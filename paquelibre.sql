CREATE DATABASE IF NOT EXISTS paqueteria;
USE paqueteria;

CREATE TABLE IF NOT EXISTS envios (
    guia VARCHAR(9) PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    remitente VARCHAR(100) NOT NULL,
    destinatario VARCHAR(100) NOT NULL,
    destino VARCHAR(120) NOT NULL,
    peso_real DOUBLE NOT NULL,
    largo DOUBLE NOT NULL,
    ancho DOUBLE NOT NULL,
    alto DOUBLE NOT NULL,
    valor_declarado DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS historial_estados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    guia VARCHAR(9) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    CONSTRAINT fk_historial_envio FOREIGN KEY (guia)
        REFERENCES envios(guia) ON DELETE CASCADE
);
