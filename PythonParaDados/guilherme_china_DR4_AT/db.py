import sqlite3
from constants import DB_FILE, CSV_PRODUTOS, CSV_CLIENTES
import csv

def inicializar_banco():
    conn = sqlite3.connect("mercado-at.db")
    cursor = conn.cursor()

    cursor.executescript("""
    DROP TABLE IF EXISTS cliente;
    DROP TABLE IF EXISTS produto;
    DROP TABLE IF EXISTS compra;
    DROP TABLE IF EXISTS item;

    CREATE TABLE cliente (
        id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
        nome CHAR(50) NOT NULL
    );

    CREATE TABLE produto (
        id_produto INTEGER PRIMARY KEY AUTOINCREMENT,
        nome CHAR(50) NOT NULL,
        quantidade INT NOT NULL,
        preco REAL NOT NULL
    );

    CREATE TABLE compra (
        id_compra INTEGER PRIMARY KEY AUTOINCREMENT,
        data_compra CHAR(30) NOT NULL,
        id_cliente INT NOT NULL,
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
    );

    CREATE TABLE item (
        id_item INTEGER PRIMARY KEY AUTOINCREMENT,
        quantidade INT NOT NULL,
        id_compra INT NOT NULL,
        id_produto INT NOT NULL,
        FOREIGN KEY (id_compra) REFERENCES compra(id_compra),
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
    );
    """)

    conn.commit()
    conn.close()
    print("Banco de dados inicializado com sucesso!")

def conectar_db():
    return sqlite3.connect(DB_FILE)

def carregar_csv_para_db():
    conn = conectar_db()
    cur = conn.cursor()

    with open(CSV_PRODUTOS, newline='', encoding='utf-8') as f:
        reader = csv.reader(f)
        for row in reader:
            cur.execute("INSERT OR IGNORE INTO produto (id_produto, nome, quantidade, preco) VALUES (?, ?, ?, ?)", row)

    with open(CSV_CLIENTES, newline='', encoding='utf-8') as f:
        reader = csv.reader(f)
        for row in reader:
            cur.execute("INSERT OR IGNORE INTO cliente (id_cliente, nome) VALUES (?, ?)", row)

    conn.commit()
    conn.close()

if __name__ == "__main__":
    inicializar_banco()
