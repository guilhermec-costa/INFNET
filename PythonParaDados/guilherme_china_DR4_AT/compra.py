from datetime import datetime
from db import conectar_db

def criar_compra(id_cliente):
    data = datetime.now().strftime("%Y-%m-%d %H:%M")
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("INSERT INTO compra (data_compra, id_cliente) VALUES (?, ?)", (data, id_cliente))
    id_compra = cur.lastrowid
    conn.commit()
    conn.close()
    return id_compra, data