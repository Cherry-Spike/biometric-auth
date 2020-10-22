CREATE DATABASE biometric_auth;

CREATE TABLE tbl_Usuario (
ID_Usuario INT(3)AUTO_INCREMENT,
Nome VARCHAR(20) NOT NULL,
Sobrenome VARCHAR(40) NOT NULL,
Cargo_ID INT(3) NOT NULL,
Nome_Usuario VARCHAR(10) NOT NULL,
/*Impressao_Digital_ID LONGBLOB NOT NULL,*/
PRIMARY KEY (ID_Usuario)
);

ALTER TABLE tbl_Usuario
ADD CONSTRAINT fk_ID_Usuario__ID_Cargo
FOREIGN KEY (Cargo_ID) REFERENCES tbl_Cargo(ID_Cargo);

/*ALTER TABLE tbl_Usuario
ADD CONSTRAINT fk_Impressao_Digital_ID__ID_Digital
FOREIGN KEY (Impressao_Digital_ID) REFERENCES tbl_ImpressaoDigital(ID_Digital);*/

CREATE TABLE tbl_Cargo (
ID_Cargo INT(3) AUTO_INCREMENT,
Descricao VARCHAR(30) NOT NULL,
Nivel_ID INT(1) NOT NULL,
PRIMARY KEY (ID_Cargo)
);

ALTER TABLE tbl_Cargo
ADD CONSTRAINT fk_Nivel_ID_Cargo__ID_Nivel
FOREIGN KEY (Nivel_ID) REFERENCES tbl_Niveis(ID_Nivel);

CREATE TABLE tbl_ImpressaoDigital (
ID_Digital INT(3) AUTO_INCREMENT,
Imagem LONGBLOB NOT NULL,
Usuario_ID INT(3) NOT NULL,
PRIMARY KEY (ID_Digital)
);

ALTER TABLE tbl_ImpressaoDigital
ADD CONSTRAINT fk_Usuario_ID_ImpDig__ID_Usuario
FOREIGN KEY (Usuario_ID) REFERENCES tbl_Usuario(ID_Usuario);

CREATE TABLE tbl_Niveis (
ID_Nivel INT(3) AUTO_INCREMENT,
Descricao VARCHAR(30) NOT NULL,
PRIMARY KEY (ID_Nivel)
);

CREATE TABLE tbl_Informacao (
ID_Info INT(3) AUTO_INCREMENT,
Nivel_ID INT(3) NOT NULL,
Descricao VARCHAR(30) NOT NULL,
Agrotoxico_ID INT(3) NOT NULL,
Endereco_ID INT(3) NOT NULL,
PRIMARY KEY (ID_Info)
);

ALTER TABLE tbl_Informacao
ADD CONSTRAINT fk_Nivel_ID_Info__ID_Nivel
FOREIGN KEY (Nivel_ID) REFERENCES tbl_Niveis(ID_Nivel);

ALTER TABLE tbl_Informacao
ADD CONSTRAINT fk_Agrotoxico_ID__ID_Agrotoxico
FOREIGN KEY (Agrotoxico_ID) REFERENCES tbl_Agrotoxico(ID_Agrotoxico);

ALTER TABLE tbl_Informacao
ADD CONSTRAINT fk_Endereco_ID__ID_Endereco
FOREIGN KEY (Endereco_ID) REFERENCES tbl_Endereco(ID_Endereco);

CREATE TABLE tbl_Endereco (
ID_Endereco INT(3) AUTO_INCREMENT,
Logadouro VARCHAR(50) NOT NULL,
Complemento VARCHAR(50) NOT NULL,
UF VARCHAR(2) NOT NULL,
Bairro VARCHAR(30) NOT NULL,
Cidade VARCHAR(30) NOT NULL,
Numero INT(5) NOT NULL,
CEP INT(8) NOT NULL,
PRIMARY KEY (ID_Endereco)
);

CREATE TABLE tbl_Agrotoxico (
ID_Agrotoxico INT(3) AUTO_INCREMENT,
Descricao VARCHAR(30) NOT NULL,
Risco VARCHAR(30) NOT NULL,
PRIMARY KEY (ID_Agrotoxico)
);




