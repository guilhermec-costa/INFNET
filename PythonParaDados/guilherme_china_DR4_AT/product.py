from db import conectar_db

def buscar_produto(id_produto):
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("SELECT * FROM produto WHERE id_produto = ?", (id_produto,))
    produto = cur.fetchone()
    conn.close()
    return produto

def atualizar_estoque(id_produto, quantidade):
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("UPDATE produto SET quantidade = quantidade - ? WHERE id_produto = ?", (quantidade, id_produto))
    conn.commit()
    conn.close()