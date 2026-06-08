CREATE DATABASE cas_catala;


CREATE TABLE Persona (
    id_persona INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    DNI VARCHAR(9),
    cognom VARCHAR(100),
    email VARCHAR(100),
    contrasenya VARCHAR(100),
    telefon VARCHAR(15),
    tipus_usuari INT
);

CREATE TABLE Encarregat (
    id_encarregat INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
);

CREATE TABLE Client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
);

CREATE TABLE Jardiner (
    id_jardiner INT AUTO_INCREMENT PRIMARY KEY,
    id_persona INT,
    FOREIGN KEY (id_persona) REFERENCES Persona(id_persona)
);

CREATE TABLE TipusTasca (
    id_tipus_tasca INT AUTO_INCREMENT PRIMARY KEY,
    tipus_tasca VARCHAR(50)
    descripcio TEXT,
    preu_h DOUBLE
);

CREATE TABLE Tasca (
    id_tasca INT AUTO_INCREMENT PRIMARY KEY,
    id_tipus_tasca INT,
    FOREIGN KEY (id_tipus_tasca) REFERENCES TipusTasca(id_tipus_tasca)
);

CREATE TABLE Intervencio (
    id_intervencio INT AUTO_INCREMENT PRIMARY KEY,
    data_inici DATE,
    data_fi DATE,
    id_tasca INT,
    id_jardiner INT,
    FOREIGN KEY (id_tasca) REFERENCES Tasca(id_tasca),
    FOREIGN KEY (id_jardiner) REFERENCES Jardiner(id_jardiner)
);

CREATE TABLE Incidencia (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    titol VARCHAR(100),
    comentari TEXT,
    id_intervencio INT,
    FOREIGN KEY (id_intervencio) REFERENCES Intervencio(id_intervencio)
);

CREATE TABLE Furgoneta (
    id_furgoneta INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(7),
    marca VARCHAR(50),
    model VARCHAR(50)
);

CREATE TABLE Full_de_ruta (
    id_full_de_ruta INT AUTO_INCREMENT PRIMARY KEY,
    data DATE,
    id_furgoneta INT,
    FOREIGN KEY (id_furgoneta) REFERENCES Furgoneta(id_furgoneta)
);

CREATE TABLE Tasca_Full_de_Ruta (
    id_tasca_full_de_ruta INT AUTO_INCREMENT PRIMARY KEY,
    id_full_de_ruta INT,
    id_tasca INT,
    FOREIGN KEY (id_full_de_ruta) REFERENCES Full_de_ruta(id_full_de_ruta),
    FOREIGN KEY (id_tasca) REFERENCES Tasca(id_tasca)
);

CREATE TABLE Full_de_Ruta_Jardiner (
    id_full_de_ruta_jardiner INT AUTO_INCREMENT PRIMARY KEY,
    id_full_de_ruta INT,
    id_jardiner INT,
    FOREIGN KEY (id_full_de_ruta) REFERENCES Full_de_ruta(id_full_de_ruta),
    FOREIGN KEY (id_jardiner) REFERENCES Jardiner(id_jardiner)
);

CREATE TABLE Contracte (
    id_contracte INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    descripcio VARCHAR(255)
);

CREATE TABLE Periode (
    id_periode INT AUTO_INCREMENT PRIMARY KEY,
    periode VARCHAR(50)
);

CREATE TABLE Venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_contracte INT,
    data_venta DATETIME,
    FOREIGN KEY (id_contracte) REFERENCES Contracte(id_contracte)
);

CREATE TABLE Manteniment (
    id_manteniment INT AUTO_INCREMENT PRIMARY KEY,
    id_contracte INT,
    id_periode INT,
    data_inici DATETIME,
    FOREIGN KEY (id_periode) REFERENCES Periode(id_periode),
    FOREIGN KEY (id_contracte) REFERENCES Contracte(id_contracte)
);

CREATE TABLE Posada_Punt (
    id_posada_punt INT AUTO_INCREMENT PRIMARY KEY,
    id_contracte INT,
    data_inici DATETIME,
    FOREIGN KEY (id_contracte) REFERENCES Contracte(id_contracte)
);

CREATE TABLE Contracte_Tasca (
    id_contracte_tasca INT AUTO_INCREMENT PRIMARY KEY,
    id_contracte INT,
    id_tasca INT,
    FOREIGN KEY (id_contracte) REFERENCES Contracte(id_contracte),
    FOREIGN KEY (id_tasca) REFERENCES Tasca(id_tasca)
);

CREATE TABLE Planta (
    id_planta INT AUTO_INCREMENT PRIMARY KEY,
    nom_cientific VARCHAR(100),
    temp_adecq VARCHAR(50),
    periode_regar VARCHAR(50),
    clima VARCHAR(50),
    preu_unitat DOUBLE
);

CREATE TABLE Quantitat (
    id_quantitat INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_planta INT,
    FOREIGN KEY (id_venta) REFERENCES Venta(id_venta),
    FOREIGN KEY (id_planta) REFERENCES Planta(id_planta)
);

CREATE TABLE Jardi (
    id_jardi INT AUTO_INCREMENT PRIMARY KEY,
    latitud DOUBLE,
    longitud DOUBLE,
    g_maps_link TEXT
);

CREATE TABLE Jardi_Planta (
    id_jardi_planta INT AUTO_INCREMENT PRIMARY KEY,
    id_jardi DOUBLE,
    id_planta DOUBLE,
    FOREIGN KEY (id_jardi) REFERENCES Jardi(id_jardi),
    FOREIGN KEY (id_planta) REFERENCES Planta(id_planta)
);


INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Juan', '12345678A', 'González', 'juan@example.com', 'contrasenya123', '123456789', 1);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('María', '87654321B', 'López', 'maria@example.com', 'contrasenya456', '987654321', 1);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Pedro', '56789012C', 'Martínez', 'pedro@example.com', 'contrasenya789', '654321987', 2);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Laura', '90123456D', 'Sánchez', 'laura@example.com', 'contrasenya012', '789456123', 2);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Ana', '34567890E', 'García', 'ana@example.com', 'contrasenya345', '321654987', 1);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('David', '78901234F', 'Rodríguez', 'david@example.com', 'contrasenya678', '456789012', 2);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Sara', '01234567G', 'Hernández', 'sara@example.com', 'contrasenya901', '987012345', 1);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Daniel', '23456789H', 'Pérez', 'daniel@example.com', 'contrasenya234', '210987654', 1);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Elena', '45678901I', 'Gómez', 'elena@example.com', 'contrasenya567', '543210987', 2);

INSERT INTO Persona (nom, DNI, cognom, email, contrasenya, telefon, tipus_usuari) 
VALUES ('Carlos', '67890123J', 'Fernández', 'carlos@example.com', 'contrasenya890', '876543210', 1);


INSERT INTO Encarregat (id_persona) VALUES (1);
INSERT INTO Encarregat (id_persona) VALUES (2);
INSERT INTO Encarregat (id_persona) VALUES (9);
INSERT INTO Encarregat (id_persona) VALUES (10);


INSERT INTO Client (id_persona) VALUES (3);
INSERT INTO Client (id_persona) VALUES (4);
INSERT INTO Client (id_persona) VALUES (5);
INSERT INTO Client (id_persona) VALUES (7);


INSERT INTO Jardiner (id_persona) VALUES (6);
INSERT INTO Jardiner (id_persona) VALUES (8);


INSERT INTO TipusTasca (tipus_tasca, descripcio, preu_h) VALUES ('Empeltar Taronjers', 'Se tiene que realizar una injertación en los 5 
naranjos de Can Jaume con la herramienta de injertación 5.0. Es imprescindible que el segundo naranjo se haga con la herramienta 3.0. 
En la puerta os va a recibir Paquito Navarro.', 25.50);
INSERT INTO TipusTasca (tipus_tasca, descripcio, preu_h) VALUES ('Llaurar terres', 'En esta tarea nuestro objetivo es labrar las tierras del jardín de Can Jaume, concretamente unos 100m2. Vamos a necesitar varias herramientos como un motocultor y abono, preferiblemente orgánico. Vamos a recibir instrucciones más detalladas por parte de Paquito.', 20.00);
INSERT INTO TipusTasca (tipus_tasca, descripcio, preu_h) VALUES ('Empeltar', 'Se tiene que realizar una injertación en los 5 naranjos de Can Jaume con la herramienta de injertación 5.0. Es imprescindible que el segundo naranjo se haga con la herramienta 3.0. 
En la puerta os va a recibir Paquito Navarro.', 30.00);


INSERT INTO Tasca (id_tipus_tasca) VALUES (1);
INSERT INTO Tasca (id_tipus_tasca) VALUES (2);
INSERT INTO Tasca (id_tipus_tasca) VALUES (3);


INSERT INTO Intervencio (data_inici, data_fi, id_tasca, id_jardiner) VALUES ('2024-04-01', '2024-04-02', 1, 1);
INSERT INTO Intervencio (data_inici, data_fi, id_tasca, id_jardiner) VALUES ('2024-04-03', '2024-04-04', 2, 1);
INSERT INTO Intervencio (data_inici, data_fi, id_tasca, id_jardiner) VALUES ('2024-04-05', '2024-04-06', 3, 1);


INSERT INTO Incidencia (titol, comentari, id_intervencio) VALUES ('Problemas con la herramienta de poda', 'La herramienta de poda está defectuosa y no se puede usar correctamente', 1);
INSERT INTO Incidencia (titol, comentari, id_intervencio) VALUES ('Daño en el cortacésped', 'El cortacésped sufrió un desperfecto durante su uso', 2);
INSERT INTO Incidencia (titol, comentari, id_intervencio) VALUES ('Falta de material para plantar flores', 'No se cuenta con suficiente tierra para completar la plantación de flores', 3);


INSERT INTO Furgoneta (matricula, marca, model) VALUES ('1234', 'Ford', 'Transit');
INSERT INTO Furgoneta (matricula, marca, model) VALUES ('5678', 'Mercedes', 'Sprinter');
INSERT INTO Furgoneta (matricula, marca, model) VALUES ('9101', 'Volkswagen', 'Crafter');


INSERT INTO Full_de_ruta (data, id_furgoneta) VALUES ('2024-04-01', 1);
INSERT INTO Full_de_ruta (data, id_furgoneta) VALUES ('2024-04-02', 2);
INSERT INTO Full_de_ruta (data, id_furgoneta) VALUES ('2024-04-03', 3);


INSERT INTO Tasca_Full_de_Ruta (id_full_de_ruta, id_tasca) VALUES (1, 1);
INSERT INTO Tasca_Full_de_Ruta (id_full_de_ruta, id_tasca) VALUES (2, 2);
INSERT INTO Tasca_Full_de_Ruta (id_full_de_ruta, id_tasca) VALUES (3, 3);


INSERT INTO Full_de_Ruta_Jardiner (id_full_de_ruta, id_jardiner) VALUES (1, 1);
INSERT INTO Full_de_Ruta_Jardiner (id_full_de_ruta, id_jardiner) VALUES (2, 1);
INSERT INTO Full_de_Ruta_Jardiner (id_full_de_ruta, id_jardiner) VALUES (3, 1);


INSERT INTO Contracte (nom, descripcio) VALUES ('Contracte de manteniment mensual', 'Inclou tasques de manteniment mensual de jardineria.');
INSERT INTO Contracte (nom, descripcio) VALUES ('Contracte de manteniment trimestral', 'Inclou tasques de manteniment trimestral de jardineria.');


INSERT INTO Periode (periode) VALUES ('Mensual');
INSERT INTO Periode (periode) VALUES ('Trimestral');


INSERT INTO Venta (id_contracte, data_venta) VALUES (1, '2024-04-01 10:00:00');
INSERT INTO Venta (id_contracte, data_venta) VALUES (2, '2024-04-02 11:00:00');


INSERT INTO Manteniment (id_contracte, id_periode, data_inici) VALUES (1, 1, '2024-05-01 08:00:00');
INSERT INTO Manteniment (id_contracte, id_periode, data_inici) VALUES (2, 2, '2024-04-01 09:00:00');


INSERT INTO Posada_Punt (id_contracte, data_inici) VALUES (1, '2024-04-01 08:00:00');
INSERT INTO Posada_Punt (id_contracte, data_inici) VALUES (2, '2024-04-02 09:00:00');


INSERT INTO Contracte_Tasca (id_contracte, id_tasca) VALUES (1, 1);
INSERT INTO Contracte_Tasca (id_contracte, id_tasca) VALUES (2, 2);


INSERT INTO Planta (nom_cientific, temp_adecq, periode_regar, clima, preu_unitat) 
VALUES ('Rosa spp.', '15-25 °C', 'Cada 2 días', 'Templado', 3.50);

INSERT INTO Planta (nom_cientific, temp_adecq, periode_regar, clima, preu_unitat) 
VALUES ('Lavandula angustifolia', '20-30 °C', 'Cada 3 días', 'Cálido', 4.00);

INSERT INTO Planta (nom_cientific, temp_adecq, periode_regar, clima, preu_unitat) 
VALUES ('Ficus elastica', '18-24 °C', 'Cada 5 días', 'Tropical', 6.50);


INSERT INTO Quantitat (id_venta, id_planta) VALUES (1, 1);
INSERT INTO Quantitat (id_venta, id_planta) VALUES (2, 2);


INSERT INTO Jardi (latitud, longitud, g_maps_link) VALUES (41.388790, 2.158990, 'https://goo.gl/maps/example1');
INSERT INTO Jardi (latitud, longitud, g_maps_link) VALUES (40.416775, -3.703790, 'https://goo.gl/maps/example2');


INSERT INTO Jardi_Planta (id_jardi, id_planta) VALUES (1, 1);
INSERT INTO Jardi_Planta (id_jardi, id_planta) VALUES (2, 2);



