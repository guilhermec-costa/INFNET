package com.sistema;

import com.sistema.domain.*;
import com.sistema.exception.ProdutoNaoEncontradoException;
import com.sistema.repository.ProdutoRepositorioMemoria;
import com.sistema.service.ProdutoServico;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class SistemaCLI {
    private final ProdutoServico servico;
    private final Scanner scanner;

    public SistemaCLI() {
        this.servico = new ProdutoServico(new ProdutoRepositorioMemoria());
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        boolean continuar = true;

        System.out.println("=== Sistema de Gerenciamento de Produtos ===");

        while (continuar) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    criarProduto();
                    break;
                case "2":
                    buscarProduto();
                    break;
                case "3":
                    listarProdutos();
                    break;
                case "4":
                    atualizarProduto();
                    break;
                case "5":
                    deletarProduto();
                    break;
                case "6":
                    contarProdutos();
                    break;
                case "0":
                    continuar = false;
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (continuar) {
                System.out.println();
            }
        }

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Criar Produto");
        System.out.println("2. Buscar Produto");
        System.out.println("3. Listar Produtos");
        System.out.println("4. Atualizar Produto");
        System.out.println("5. Deletar Produto");
        System.out.println("6. Contar Produtos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void criarProduto() {
        try {
            System.out.println("\n--- Criar Produto ---");

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Preço: ");
            String precoStr = scanner.nextLine();
            BigDecimal preco = new BigDecimal(precoStr);

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            System.out.println("Categorias disponíveis:");
            for (Categoria cat : Categoria.values()) {
                System.out.println("- " + cat.getDescricao());
            }
            System.out.print("Categoria: ");
            String categoriaStr = scanner.nextLine();

            Produto produto = servico.criar(
                    NomeProduto.de(nome),
                    Preco.de(preco),
                    Quantidade.de(quantidade),
                    Categoria.fromDescricao(categoriaStr)
            );

            System.out.println("Produto criado com sucesso!");
            System.out.println("ID: " + produto.getId().getValor());

        } catch (Exception e) {
            System.out.println("Erro ao criar produto: " + e.getMessage());
        }
    }

    private void buscarProduto() {
        try {
            System.out.println("\n--- Buscar Produto ---");
            System.out.print("ID do produto: ");
            String id = scanner.nextLine();

            Produto produto = servico.buscar(ProdutoId.de(id));
            exibirProduto(produto);

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- Lista de Produtos ---");
        List<Produto> produtos = servico.listarTodos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            exibirProduto(produto);
            System.out.println("---");
        }
    }

    private void atualizarProduto() {
        try {
            System.out.println("\n--- Atualizar Produto ---");
            System.out.print("ID do produto: ");
            String id = scanner.nextLine();

            Produto produtoExistente = servico.buscar(ProdutoId.de(id));
            System.out.println("Produto atual:");
            exibirProduto(produtoExistente);

            System.out.print("\nNovo nome: ");
            String nome = scanner.nextLine();

            System.out.print("Novo preço: ");
            String precoStr = scanner.nextLine();
            BigDecimal preco = new BigDecimal(precoStr);

            System.out.print("Nova quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine());

            System.out.println("Categorias disponíveis:");
            for (Categoria cat : Categoria.values()) {
                System.out.println("- " + cat.getDescricao());
            }
            System.out.print("Nova categoria: ");
            String categoriaStr = scanner.nextLine();

            Produto produtoAtualizado = servico.atualizar(
                    ProdutoId.de(id),
                    NomeProduto.de(nome),
                    Preco.de(preco),
                    Quantidade.de(quantidade),
                    Categoria.fromDescricao(categoriaStr)
            );

            System.out.println("Produto atualizado com sucesso!");
            exibirProduto(produtoAtualizado);

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private void deletarProduto() {
        try {
            System.out.println("\n--- Deletar Produto ---");
            System.out.print("ID do produto: ");
            String id = scanner.nextLine();

            servico.deletar(ProdutoId.de(id));
            System.out.println("Produto deletado com sucesso!");

        } catch (ProdutoNaoEncontradoException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao deletar produto: " + e.getMessage());
        }
    }

    private void contarProdutos() {
        long total = servico.contarProdutos();
        System.out.println("\n--- Total de Produtos ---");
        System.out.println("Total: " + total + " produto(s)");
    }

    private void exibirProduto(Produto produto) {
        System.out.println("ID: " + produto.getId().getValor());
        System.out.println("Nome: " + produto.getNome().getValor());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade: " + produto.getQuantidade());
        System.out.println("Categoria: " + produto.getCategoria().getDescricao());
    }

    public static void main(String[] args) {
        SistemaCLI sistema = new SistemaCLI();
        sistema.executar();
    }
}