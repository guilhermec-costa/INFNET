from db import conectar_db

def registrar_item(id_compra, id_produto, quantidade):
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("INSERT INTO item (quantidade, id_compra, id_produto) VALUES (?, ?, ?)", (quantidade, id_compra, id_produto))
    conn.commit()
    conn.close()
