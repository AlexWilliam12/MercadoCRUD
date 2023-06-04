DROP DATABASE mercadoDB;

CREATE DATABASE mercadoDB;
USE mercadoDB;

CREATE TABLE Funcionario(
	cpf VARCHAR(11),
    rg VARCHAR(9),
    nome TEXT NOT NULL,
    email varchar(60) UNIQUE NOT NULL,
    senha varchar(21) NOT NULL,
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
	id INT PRIMARY KEY,
    nome VARCHAR(20) UNIQUE NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Estoque(
	id INT NOT NULL,
    quantidade INT NOT NULL,
    FOREIGN KEY (id) REFERENCES Produto(id)
);

SELECT * FROM Produto;
SELECT * FROM Estoque;

SELECT * FROM Funcionario;
SELECT * FROM Fornecedor;
SELECT * FROM Endereco;
SELECT * FROM Cep;
