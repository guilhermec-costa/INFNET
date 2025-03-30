import json
import csv
import pandas as pd
import constants as c;
from utils import verificar_arquivo

# =====================================================================
# Exercício 1: Manipular um Arquivo do Tipo Texto
def ler_nomes(caminho):
    verificar_arquivo(caminho)
    with open(caminho, 'r', encoding='utf-8') as arquivo:
        return [linha.strip() for linha in arquivo.readlines()]

def ordenar_nomes(nomes):
    return sorted(nomes)

def gravar_nomes(caminho, nomes):
    with open(caminho, 'w', encoding='utf-8') as arquivo:
        arquivo.write("\n".join(nomes))

# =====================================================================
# Exercício 2: Manipular um Arquivo do Tipo CSV
def ler_produtos_csv(caminho):
    verificar_arquivo(caminho)
    with open(caminho, 'r', encoding='utf-8') as arquivo:
        return list(csv.DictReader(arquivo))

def reajustar_precos(produtos, percentual):
    for produto in produtos:
        produto['preco'] = round(float(produto['preco']) * (1 + percentual / 100), 2)

def gravar_produtos_csv(caminho, produtos):
    with open(caminho, 'w', encoding='utf-8', newline='') as arquivo:
        campos = produtos[0].keys()
        escritor = csv.DictWriter(arquivo, fieldnames=campos)
        escritor.writeheader()
        escritor.writerows(produtos)

# =====================================================================
# Exercício 3: Manipular um Arquivo do Tipo JSON
def ler_produtos_json(caminho):
    verificar_arquivo(caminho)
    with open(caminho, 'r', encoding='utf-8') as arquivo:
        return json.load(arquivo)

def gravar_produtos_json(caminho, produtos):
    with open(caminho, 'w', encoding='utf-8') as arquivo:
        json.dump(produtos, arquivo, ensure_ascii=False, indent=4)


# =====================================================================
# Exercício 4: Manipular um Arquivo do Tipo CSV utilizando Pandas
def ler_csv_pandas(caminho):
    verificar_arquivo(caminho)
    df = pd.read_csv(caminho, encoding='utf-8')
    df['preco'] = df['preco'].astype(float)
    return df

def reajustar_precos_pandas(df, percentual):
    df['preco'] = df['preco'] * (1 + percentual / 100)

def gravar_csv_pandas(caminho, df):
    df.to_csv(caminho, index=False, encoding='utf-8')

# =====================================================================
# Exercício 5: Manipular um Arquivo do Tipo JSON utilizando Pandas
def ler_json_pandas(caminho):
    verificar_arquivo(caminho)
    df = pd.read_json(caminho, encoding='utf-8')
    df['preco'] = df['preco'].astype(float)
    return df

def gravar_json_pandas(caminho, df):
    df.to_json(caminho, orient='records', force_ascii=False, indent=4)

# =====================================================================

def exe1():
    nomes = ler_nomes(c.CAMINHO_NOMES)
    nomes_ordenados = ordenar_nomes(nomes)
    gravar_nomes(c.CAMINHO_NOMES, nomes_ordenados)

def exe2():
    produtos_csv = ler_produtos_csv(c.CAMINHO_CSV)
    reajustar_precos(produtos_csv, 5)
    gravar_produtos_csv(c.CAMINHO_CSV, produtos_csv)

def exe3():
    produtos_json = ler_produtos_json(c.CAMINHO_JSON)
    reajustar_precos(produtos_json, 5)
    gravar_produtos_json(c.CAMINHO_JSON, produtos_json)

def exe4():
    df_csv = ler_csv_pandas(c.CAMINHO_CSV)
    reajustar_precos_pandas(df_csv, 5)
    gravar_csv_pandas(c.CAMINHO_CSV, df_csv)

def exe5():
    df_json = ler_json_pandas(c.CAMINHO_JSON)
    reajustar_precos_pandas(df_json, 5)
    gravar_json_pandas(c.CAMINHO_JSON, df_json)

def menu():
    opcoes = {
        '1': exe1,
        '2': exe2,
        '3': exe3,
        '4': exe4,
        '5': exe5,
        '0': exit
    }

    while True:
        print("\nEscolha um exercício para executar:")
        print("1 - Exercício 1: Manipular arquivo de texto")
        print("2 - Exercício 2: Manipular arquivo CSV")
        print("3 - Exercício 3: Manipular arquivo JSON")
        print("4 - Exercício 4: Manipular CSV com Pandas")
        print("5 - Exercício 5: Manipular JSON com Pandas")
        print("0 - Sair")

        escolha = input("Digite o número da opção: ")

        if escolha in opcoes:
            print(escolha)
            opcoes[escolha]()
            print("Operação concluída!")
        else:
            print("Opção inválida! Tente novamente.")

if __name__ == '__main__':
    menu()
