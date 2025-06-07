from db import conectar_db
from datetime import datetime
from tabulate import tabulate
from utils import validar_inteiro
from client import buscar_cliente, cadastrar_cliente
from product import buscar_produto, atualizar_estoque
from compra import criar_compra
from item import registrar_item

def nota_fiscal(id_cliente, id_compra):
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("""
        SELECT concat("Item ", cast(i.id_item as text)), p.nome, i.quantidade, p.preco, i.quantidade * p.preco AS total
        FROM item i
        JOIN produto p ON i.id_produto = p.id_produto
        WHERE i.id_compra = ?
    """, (id_compra,))
    itens = cur.fetchall()
    conn.close()

    total_itens = sum(i[2] for i in itens)
    total_valor = sum(i[4] for i in itens)

    print(f"\nCliente: {id_cliente}")
    print(tabulate(itens, headers=["Item", "Produto", "Quantidade", "Preço Unit.", "Preço Total"]))
    print(f"\nItens: {total_itens}")
    print(f"Preço total: {total_valor:.2f}\n")

def fechar_caixa():
    print("\nFechamento do caixa:")
    print(f"Data: {datetime.now().strftime('%Y-%m-%d %H:%M')}")
    conn = conectar_db()
    cur = conn.cursor()
    cur.execute("""
        SELECT c.nome, SUM(p.preco * i.quantidade) AS total
        FROM cliente c
        JOIN compra co ON c.id_cliente = co.id_cliente
        JOIN item i ON co.id_compra = i.id_compra
        JOIN produto p ON i.id_produto = p.id_produto
        GROUP BY c.id_cliente
    """)
    resultados = cur.fetchall()
    total_geral = sum(r[1] for r in resultados)

    print(tabulate(resultados, headers=["Cliente", "Total"]))
    print(f"\nTotal de vendas: {total_geral:.2f}")

    cur.execute("SELECT nome FROM produto WHERE quantidade = 0")
    esgotados = cur.fetchall()
    if esgotados:
        print("\nProdutos sem estoque:")
        for (nome,) in esgotados:
            print(f"- {nome}")
    conn.close()

def atendimento():
    while True:
        opcao = input("\n1 - Iniciar atendimento\n2 - Fechar caixa\nEscolha: ")
        if opcao == "1":
            id_raw = input("Digite o ID do cliente: ")
            id_cliente = validar_inteiro(id_raw)
            if not id_cliente:
                print("Erro: valor inválido")
                continue

            cliente = buscar_cliente(id_cliente)
            if not cliente:
                cliente = cadastrar_cliente(id_cliente)

            id_compra, data = criar_compra(cliente[0])
            while True:
                idp_raw = input("ID do produto (ou 'fim' para encerrar): ")
                if idp_raw.lower() == "fim":
                    break
                id_produto = validar_inteiro(idp_raw)
                if not id_produto:
                    print("Erro: id inválido")
                    continue

                produto = buscar_produto(id_produto)
                if not produto:
                    print("Erro: produto não cadastrado")
                    continue

                qtd_raw = input("Quantidade: ")
                qtd = validar_inteiro(qtd_raw)
                if not qtd:
                    print("Erro: quantidade inválida")
                    continue

                if qtd > produto[2]:
                    print("Erro: quantidade fora do estoque")
                    continue

                registrar_item(id_compra, id_produto, qtd)
                atualizar_estoque(id_produto, qtd)
                print("Item adicionado.")

            nota_fiscal(cliente[0], id_compra)
        elif opcao == "2":
            fechar_caixa()
            break
        else:
            print("Opção inválida.")