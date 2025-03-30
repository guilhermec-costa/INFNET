import pandas as pd
import glob
import db_utils

REAJUSTE = 1.05

def ler_excel(nome_arquivo):
    try:
        df = pd.read_excel(nome_arquivo)
        df["preco"] = df["preco"].astype(float)
        return df
    except FileNotFoundError:
        print(f"Erro: O arquivo {nome_arquivo} não foi encontrado.")
        return None

def reajustar_precos(df):
    df_reajustado = df.copy()
    df_reajustado["preco"] = (df_reajustado["preco"] * REAJUSTE)
    return df_reajustado

def salvar_excel(nome_arquivo: str, df_antes: pd.DataFrame, df_depois: pd.DataFrame):
    df_depois["preco"] = df_depois["preco"].astype(str)
    with pd.ExcelWriter(nome_arquivo) as writer:
        df_antes.to_excel(writer, sheet_name="Antes", index=False)
        df_depois.to_excel(writer, sheet_name="Depois", index=False)

def exercicio_1():
    df = ler_excel("produtos.xlsx")
    if df is not None:
        df_reajustado = reajustar_precos(df)
        salvar_excel("produtos2.xlsx", df, df_reajustado)
        print("Arquivo produtos2.xlsx gerado com sucesso!")

def exercicio_2():
    arquivos = glob.glob("produtosexe2_*.xlsx")
    dfs = [ler_excel(arquivo) for arquivo in arquivos if ler_excel(arquivo) is not None]
    if dfs:
        df_geral = pd.concat(dfs, ignore_index=True)
        df_reajustado = reajustar_precos(df_geral)
        df_reajustado.to_excel("produtos_exe2_reajustado.xlsx", sheet_name="Produtos", index=False)
        print("Arquivo produtos.xlsx gerado com sucesso!")


def exercicio_3():
    df = db_utils.ler_banco()
    if df is not None:
        df_reajustado = reajustar_precos(df)
        db_utils.salvar_banco(df_reajustado)

def menu():
    while True:
        print("\nEscolha um exercício para testar:")
        print("1 - Manipular um arquivo Excel")
        print("2 - Manipular múltiplos arquivos Excel")
        print("3 - Manipular um banco de dados SQLite")
        print("0 - Sair")
        opcao = input("Digite o número da opção desejada: ")

        if opcao == "1":
            exercicio_1()
        elif opcao == "2":
            exercicio_2()
        elif opcao == "3":
            exercicio_3()
        elif opcao == "0":
            print("Saindo...")
            break
        else:
            print("Opção inválida, tente novamente.")

if __name__ == "__main__":
    db_utils.inicializar_banco()
    menu()
