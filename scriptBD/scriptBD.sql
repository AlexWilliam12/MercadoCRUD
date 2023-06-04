DROP DATABASE mercadoDB;

CREATE DATABASE mercadoDB;
USE mercadoDB;

CREATE TABLE Administrador(
	id INT PRIMARY KEY AUTO_INCREMENT,
    usuario TEXT NOT NULL,
    senha varchar(21) NOT NULL
);

CREATE TABLE Funcionario(
	cpf VARCHAR(11),
    rg VARCHAR(9),
    nome TEXT NOT NULL,
    PRIMARY KEY (cpf, rg)
);

CREATE TABLE Fornecedor(
	cnpj VARCHAR(14),
    inscricao_estadual VARCHAR(13) NOT NULL,
    nome TEXT NOT NULL,
    PRIMARY KEY (cnpj)
);

CREATE TABLE Endereco(
	cep VARCHAR(8) PRIMARY KEY,
	logradouro TEXT NOT NULL,
    bairro TEXT NOT NULL,
    complemento TEXT,
    cidade TEXT NOT NULL,
    uf VARCHAR(2) NOT NULL,
    pais TEXT NOT NULL,
    numero INT NOT NULL
);

CREATE TABLE Cep(
	cnpj VARCHAR(14) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    FOREIGN KEY (cnpj) REFERENCES Fornecedor(cnpj),
    FOREIGN KEY (cep) REFERENCES Endereco(cep)
);

CREATE TABLE Produto(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(20) UNIQUE NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Estoque(
	id INT PRIMARY KEY AUTO_INCREMENT,
    quantidade INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Produto(id)
);

INSERT INTO Fornecedor VALUES ('03781835000114', '879907539514', 'Empresa Nobre');
INSERT INTO Endereco VALUES ('69911015', 'Beco da Solidão', 'Volta Seca', '', 'Rio Branco', 'AC', 'Brasil', 68);
INSERT INTO Cep VALUES ('03781835000114', '69911015');
INSERT INTO Administrador(usuario, senha) VALUES ('root', 'root');
INSERT INTO Produto(nome, descricao, preco) VALUES ('Banana', 'Fruta', 4.55);
INSERT INTO Estoque(quantidade) VALUES (10);