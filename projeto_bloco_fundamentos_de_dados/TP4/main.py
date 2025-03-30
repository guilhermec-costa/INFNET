import pandas as pd
from sqlalchemy import create_engine, text
import json
import os

DB_USER = os.getenv('MYSQL_USER', 'root')
DB_PASSWORD = os.getenv('MYSQL_PASSWORD', 'root')
DB_HOST = os.getenv('MYSQL_HOST', 'localhost')
DB_PORT = os.getenv('MYSQL_PORT', '3306')
DB_NAME = os.getenv('MYSQL_DATABASE', 'crimes_db')

engine = create_engine(f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}")

df = pd.read_csv('dados_criminalidade.csv', delimiter=';')

with engine.connect() as conn:
    conn.execute(text('''
        CREATE TABLE IF NOT EXISTS crimes (
            id INT PRIMARY KEY,
            data DATE,
            tipo_crime VARCHAR(255),
            localizacao VARCHAR(255),
            hora TIME,
            gravidade VARCHAR(50),
            sexo_suspeito VARCHAR(50),
            idade_suspeito INT,
            status_investigacao VARCHAR(255)
        )
    '''))
    conn.commit()

df.to_sql('crimes', engine, if_exists='replace', index=False)

def crimes_por_mes_ano(mes, ano):
    query = text("SELECT COUNT(*) FROM crimes WHERE MONTH(data) = :mes AND YEAR(data) = :ano")
    with engine.connect() as conn:
        resultado = conn.execute(query, {"mes": mes, "ano": ano}).scalar()
    return resultado or 0

def crime_mais_comum(regiao):
    query = text("""
        SELECT tipo_crime, COUNT(*) as total
        FROM crimes
        WHERE localizacao = :regiao
        GROUP BY tipo_crime
        ORDER BY total DESC
        LIMIT 1
    """)
    with engine.connect() as conn:
        resultado = conn.execute(query, {"regiao": regiao}).fetchone()
    return resultado[0] if resultado else None

def crimes_resolvidos():
    query = text("SELECT COUNT(*) FROM crimes WHERE status_investigacao = 'Concluído'")
    with engine.connect() as conn:
        resultado = conn.execute(query).scalar()
    return resultado or 0

def faixa_etaria_mais_comum():
    query = text("SELECT idade_suspeito FROM crimes WHERE idade_suspeito IS NOT NULL")
    with engine.connect() as conn:
        idades = [row[0] for row in conn.execute(query).fetchall()]
    return max(set(idades), key=idades.count) if idades else None

def padrao_horario():
    query = text("""
        SELECT hora, COUNT(*) as total
        FROM crimes
        GROUP BY hora
        ORDER BY total DESC
        LIMIT 1
    """)
    with engine.connect() as conn:
        resultado = conn.execute(query).fetchone()
    return resultado[0] if resultado else None

def porcentagem_crimes_violentos():
    violentos = ['Homicídio', 'Roubo', 'Violência Doméstica', 'Sequestro']
    
    query_total = text("SELECT COUNT(*) FROM crimes")
    query_violentos = text("SELECT COUNT(*) FROM crimes WHERE tipo_crime IN :tipos")

    with engine.connect() as conn:
        total = conn.execute(query_total).scalar()
        total_violentos = conn.execute(query_violentos, {"tipos": tuple(violentos)}).scalar()

    return (total_violentos / total) * 100 if total else 0

resultados = {
    'crimes_por_mes_ano': crimes_por_mes_ano(2, 2025),
    'crime_mais_comum': crime_mais_comum('Freitas'),
    'crimes_resolvidos': crimes_resolvidos(),
    'faixa_etaria_mais_comum': faixa_etaria_mais_comum(),
    'padrao_horario': padrao_horario(),
    'porcentagem_crimes_violentos': porcentagem_crimes_violentos()
}

with open('resultados.json', 'w') as json_file:
    json.dump(resultados, json_file, indent=4)

print("Resultados salvos em 'resultados.json'")
