from db import conectar_db

def buscar_cliente(id_cliente):
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("SELECT * FROM cliente WHERE id_cliente = ?", (id_cliente,))
    cliente = cur.fetchone()
    conn.close()
    return cliente

def cadastrar_cliente(id_cliente):
    nome = input("Cliente não encontrado. Digite o nome do cliente para cadastro: ")
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("INSERT INTO cliente (id_cliente, nome) VALUES (?, ?)", (id_cliente, nome))
    conn.commit()
    conn.close()
    return (id_cliente, nome)
