package com.infnet.TP5.src.repository.csv;

import com.infnet.TP5.src.model.Produto;
import com.infnet.TP5.src.repository.IProdutoRepository;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ProdutoRepositoryCSV implements IProdutoRepository {
    private static final String CSV_FILE = "produtos.csv";
    private static final String CSV_HEADER = "id;nome;descricao;preco;estoque";
    private static final String CSV_SEPARATOR = ";";

    public ProdutoRepositoryCSV() {
        inicializarArquivo();
    }

    private void inicializarArquivo() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
                writer.println(CSV_HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo CSV: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String linha;
            reader.readLine(); // pular header
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(CSV_SEPARATOR);
                if (dados.length >= 5) {
                    produtos.add(criarProdutoDoCSV(dados));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
        }
        
        return produtos;
    }

    @Override
    public Optional<Produto> buscarPorId(long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String linha;
            reader.readLine(); // pular header
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(CSV_SEPARATOR);
                if (dados.length >= 5 && Long.parseLong(dados[0]) == id) {
                    return Optional.of(criarProdutoDoCSV(dados));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void salvar(Produto produto) {
        if (buscarPorId(produto.getId()).isPresent()) {
            atualizarProduto(produto);
        } else {
            inserirProduto(produto);
        }
    }

    private void inserirProduto(Produto produto) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
            writer.println(formatarProdutoParaCSV(produto));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage());
        }
    }

    private void atualizarProduto(Produto produto) {
        List<String> linhas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String linha;
            linhas.add(reader.readLine()); // adicionar header
            
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(CSV_SEPARATOR);
                if (dados.length >= 5 && Long.parseLong(dados[0]) == produto.getId()) {
                    linhas.add(formatarProdutoParaCSV(produto));
                } else {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo para atualização: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (String linha : linhas) {
                writer.println(linha);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private Produto criarProdutoDoCSV(String[] dados) {
        long id = Long.parseLong(dados[0]);
        String nome = dados[1];
        String descricao = dados[2];
        BigDecimal preco = new BigDecimal(dados[3]);
        int estoque = Integer.parseInt(dados[4]);
        return new Produto(id, nome, descricao, preco, estoque);
    }

    private String formatarProdutoParaCSV(Produto produto) {
        return String.format("%d;%s;%s;%s;%d", 
            produto.getId(), 
            produto.getNome(), 
            produto.getDescricao(), 
            produto.getPreco().toString(), 
            produto.getEstoque());
    }
}
