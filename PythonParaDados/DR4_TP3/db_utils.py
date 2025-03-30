from sqlalchemy import create_engine, text
import pandas as pd

def inicializar_banco():
    engine = create_engine("sqlite:///mercado.db")
    with engine.connect() as conn:
        conn.execute(text("""
            CREATE TABLE IF NOT EXISTS produto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome CHAR(50) NOT NULL,
                quantidade INT NOT NULL,
                preco REAL NOT NULL
            )
        """))
        result = conn.execute(text("SELECT COUNT(*) FROM produto"))
        count = result.scalar()
        if count == 0:
            conn.execute(text("""
                INSERT INTO produto (nome, quantidade, preco) VALUES
                ('Produto 1', 1, 10),
                ('Produto 2', 2, 20),
                ('Produto 3', 3, 30),
                ('Produto 4', 4, 40),
                ('Produto 5', 5, 50)
            """))
        conn.commit()
    print("Banco de dados inicializado!")

def ler_banco():
    try:
        engine = create_engine("sqlite:///mercado.db")
        df = pd.read_sql("SELECT * FROM produto", engine)
        return df
    except Exception as e:
        print(f"Erro ao acessar o banco de dados: {e}")
        return None

def salvar_banco(df):
    engine = create_engine("sqlite:///mercado.db")
    df.to_sql("produto", engine, if_exists="replace", index=False)
    print("Banco atualizado com sucesso!")