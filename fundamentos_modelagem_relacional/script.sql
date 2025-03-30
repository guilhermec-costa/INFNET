-- Exercício 1
CREATE TABLE Cliente (
    ID SERIAL PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE,
    CPF VARCHAR(14),
    DataCadastro DATE
);

-- Exercício 2
ALTER TABLE Cliente ADD COLUMN Telefone BIGINT;
ALTER TABLE Cliente ADD CONSTRAINT unique_cpf UNIQUE (CPF);

-- Exercício 3
INSERT INTO Cliente (Nome, Email, CPF, DataCadastro, Telefone) VALUES
('João Silva', 'joao@email.com', '111.111.111-11', '2023-01-01', 11999999999),
('Maria Oliveira', 'maria@email.com', '222.222.222-22', '2023-02-01', 11988888888),
('Carlos Souza', 'carlos@email.com', '333.333.333-33', '2023-03-01', 11977777777),
('Ana Lima', 'ana@email.com', '444.444.444-44', '2023-04-01', 11966666666),
('Pedro Rocha', 'pedro@email.com', '555.555.555-55', '2023-05-01', 11955555555),
('Fernanda Alves', 'fernanda@email.com', '666.666.666-66', '2023-06-01', 11944444444),
('Lucas Mendes', 'lucas@email.com', '777.777.777-77', '2023-07-01', 11933333333),
('Juliana Costa', 'juliana@email.com', '888.888.888-88', '2023-08-01', 11922222222),
('Rafael Pereira', 'rafael@email.com', '999.999.999-99', '2023-09-01', 11911111111),
('Beatriz Martins', 'beatriz@email.com', '000.000.000-00', '2023-10-01', 11900000000);

-- Exercício 4
DELETE FROM Cliente WHERE Nome LIKE 'J%';

-- Exercício 5
CREATE TABLE Produto (
    ID SERIAL PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL UNIQUE,
    Preco DECIMAL(10,2) NOT NULL,
    DataCadastro DATE
);

-- Exercício 6
ALTER TABLE Produto ADD COLUMN Quantidade INT;

-- Exercício 7
INSERT INTO Produto (Nome, Preco, DataCadastro, Quantidade) VALUES
('Teclado', 150.00, '2023-01-01', 50),
('Mouse', 80.00, '2023-02-01', 100),
('Monitor', 1200.00, '2023-03-01', 30),
('Cadeira Gamer', 900.00, '2023-04-01', 20),
('Notebook', 4500.00, '2023-05-01', 15),
('Headset', 300.00, '2023-06-01', 40),
('Impressora', 700.00, '2023-07-01', 10),
('Smartphone', 2500.00, '2023-08-01', 25),
('Tablet', 1500.00, '2023-09-01', 35),
('Webcam', 200.00, '2023-10-01', 50);

-- Exercício 8
DELETE FROM Produto WHERE Preco BETWEEN 500 AND 1500;

-- Exercício 9
CREATE TABLE Pedido (
    ID SERIAL PRIMARY KEY,
    Total DECIMAL(10,2) NOT NULL,
    DataPedido DATE,
    Id_Cliente INT NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (Id_Cliente) REFERENCES Cliente(ID)
);

CREATE TABLE Pedido_Produto (
    Id_Pedido INT NOT NULL,
    Id_Produto INT NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (Id_Pedido) REFERENCES Pedido(ID),
    CONSTRAINT fk_produto FOREIGN KEY (Id_Produto) REFERENCES Produto(ID)
);

-- Exercício 10
INSERT INTO Pedido (Total, DataPedido, Id_Cliente) VALUES
(500.00, '2023-11-01', 1),
(1000.00, '2023-11-02', 2),
(1500.00, '2023-11-03', 3),
(2000.00, '2023-11-04', 4),
(2500.00, '2023-11-05', 5);

INSERT INTO Pedido_Produto (Id_Pedido, Id_Produto) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 10);

-- Exercício 11
SELECT C.Nome, P.ID AS PedidoID, P.Total, P.DataPedido
FROM Cliente C
JOIN Pedido P ON C.ID = P.Id_Cliente
ORDER BY C.Nome;

SELECT C.Nome, Pr.Nome AS Produto, P.Total, P.DataPedido
FROM Cliente C
JOIN Pedido P ON C.ID = P.Id_Cliente
JOIN Pedido_Produto PP ON P.ID = PP.Id_Pedido
JOIN Produto Pr ON PP.Id_Produto = Pr.ID
ORDER BY C.Nome, Pr.Nome;

-- Exercício 12
SELECT C.Nome, AVG(P.Total) AS PrecoMedio
FROM Cliente C
JOIN Pedido P ON C.ID = P.Id_Cliente
GROUP BY C.Nome;

SELECT MAX(P.Total) AS MaiorVenda FROM Pedido P;

SELECT Pr.Nome AS Produto, COUNT(PP.Id_Pedido) AS TotalVendas
FROM Produto Pr
JOIN Pedido_Produto PP ON Pr.ID = PP.Id_Produto
GROUP BY Pr.Nome;
