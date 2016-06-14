/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  juangu
 * Created: 23-may-2016
 */

-- COMO ROOT

drop database if exists damjdbc00;
create database damjdbc00;

create user damjdbc00 identified by 'damuser';
grant all privileges on damjdbc00.* to 'damjdbc00'@'%' identified by 'damuser';
grant all privileges on damjdbc00.* to 'damjdbc00'@'localhost' identified by 'damuser';
flush privileges;

-- COMO USUARIO

-- connect damjdbc00@damjdbc00/'damuser';

drop table if exists linea_pedido;
drop table if exists pedido;
drop table if exists producto;
drop table if exists cliente;


CREATE TABLE cliente (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY, 
        nombre VARCHAR(25) NOT NULL , 
	apellido VARCHAR(50) NOT NULL,
        direccion VARCHAR(80) NOT NULL
 ) ;
		
CREATE TABLE producto (
	id_producto INT AUTO_INCREMENT PRIMARY KEY, 
	nombre VARCHAR(30) NOT NULL, 
        descripcion VARCHAR(70), 
        precio FLOAT(6) NOT NULL	
);

CREATE TABLE pedido (
	id_pedido INT AUTO_INCREMENT, 
        id_cliente INT, 
	fecha DATE NOT NULL,
	direccion_cliente VARCHAR(80) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    PRIMARY KEY (id_pedido)
 );

CREATE TABLE linea_pedido (
        id_lp INT AUTO_INCREMENT,
	id_pedido INT,
        id_producto INT,
        precio FLOAT NOT NULL,
        cantidad INT NOT NULL,
        FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
        PRIMARY KEY (id_lp)
        );

INSERT INTO cliente (nombre, apellido, direccion) VALUES ('Pepe', 'Perez', 'Paseo España 1');
INSERT INTO cliente (nombre, apellido, direccion) VALUES ('Juan', 'Gomez', 'Linares 1');

INSERT INTO producto (nombre, descripcion, precio) VALUES ('IPAD3', 'IPad 3 9 pulgadas retina', 600.0);
INSERT INTO producto (nombre, descripcion, precio) VALUES ('SSDPT240', 'Disco duro SSD PT 240GB', 70.0);

INSERT INTO pedido (id_cliente, fecha, direccion_cliente) VALUES (1, CURRENT_DATE, 'Paseo España 1');
INSERT INTO pedido (id_cliente, fecha, direccion_cliente) VALUES (2, CURRENT_DATE, 'Jaén 2');

INSERT INTO linea_pedido (id_pedido, id_producto, precio, cantidad) VALUES (1, 1, 630.0, 2);
INSERT INTO linea_pedido (id_pedido, id_producto, precio, cantidad) VALUES (1, 2, 50.0, 3);
INSERT INTO linea_pedido (id_pedido, id_producto, precio, cantidad) VALUES (2, 1, 690.0, 1);
INSERT INTO linea_pedido (id_pedido, id_producto, precio, cantidad) VALUES (2, 2, 56.0, 6);
