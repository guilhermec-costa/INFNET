package com.infnet.TP5.src.repository.csv;

import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Endereco;
import com.infnet.TP5.src.model.FormaPagamento;
import com.infnet.TP5.src.repository.IClienteRepository;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteRepositoryCSV implements IClienteRepository {
  private static final String CSV_FILE = "clientes.csv";
  private static final String CSV_HEADER = "id;nome;email;senha;enderecos;formasPagamento";
  private static final String CSV_SEPARATOR = ";";

  public ClienteRepositoryCSV() {
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
  public Optional<Cliente> buscarPorEmail(String email) {
    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      reader.readLine(); // pular header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 4 && dados[2].equals(email)) {
          return Optional.of(criarClienteDoCSV(dados));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public Optional<Cliente> buscarPorId(long clientId) {
    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      reader.readLine(); // pular header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 4 && Long.parseLong(dados[0]) == clientId) {
          return Optional.of(criarClienteDoCSV(dados));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
    }
    return Optional.empty();
  }

  public void salvar(Cliente cliente) {
    if (buscarPorId(cliente.getId()).isPresent()) {
      atualizarCliente(cliente);
    } else {
      inserirCliente(cliente);
    }
  }

  private void inserirCliente(Cliente cliente) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
      writer.println(formatarClienteParaCSV(cliente));
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
    }
  }

  private void atualizarCliente(Cliente cliente) {
    List<String> linhas = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      linhas.add(reader.readLine()); // adicionar header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 4 && Long.parseLong(dados[0]) == cliente.getId()) {
          linhas.add(formatarClienteParaCSV(cliente));
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
      throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
    }
  }

 private Cliente criarClienteDoCSV(String[] dados) {
    long id = Long.parseLong(dados[0]);
    String nome = dados[1];
    String email = dados[2];
    String senha = dados[3];

    Cliente cliente = new Cliente(id, nome, email, senha);

    // Endereços (coluna 4)
    if (dados.length > 4 && !dados[4].isBlank()) {
        String[] enderecosStr = dados[4].split(","); // separa múltiplos endereços
        for (String e : enderecosStr) {
            String[] partes = e.split("\\|"); // logradouro|numero|cep|cidade
            if (partes.length == 4) {
                Endereco endereco = new Endereco(
                    0,          // id pode ser gerado depois
                    partes[0],  // logradouro
                    partes[1],  // numero
                    partes[2],  // cep
                    partes[3]   // cidade
                );
                cliente.adicionarEndereco(endereco);
            }
        }
    }

    // Formas de pagamento (coluna 5)
    if (dados.length > 5 && !dados[5].isBlank()) {
        String[] formasStr = dados[5].split(","); // separa múltiplas formas
        for (String f : formasStr) {
            String[] partes = f.split("\\|"); // tipo|descricao
            if (partes.length == 2) {
                FormaPagamento forma = new FormaPagamento(0, partes[0], partes[1]);
                cliente.adicionarFormaPagamento(forma);
            }
        }
    }

    return cliente;
} 

private String formatarClienteParaCSV(Cliente cliente) {
    // Endereços: logradouro|numero|cep|cidade separados por vírgula
    String enderecosStr = cliente.getEnderecos().stream()
        .map(e -> String.join("|", e.getLogradouro(), e.getNumero(), e.getCep(), e.getCidade()))
        .collect(Collectors.joining(","));

    // Formas de pagamento: tipo|descricao separados por vírgula
    String formasPagamentoStr = cliente.getFormasPagamento().stream()
        .map(f -> String.join("|", f.getTipo(), f.getDescricao()))
        .collect(Collectors.joining(","));

    return String.format("%d;%s;%s;%s;%s;%s",
        cliente.getId(),
        cliente.getNome(),
        cliente.getEmail(),
        cliente.getSenha(),
        enderecosStr,
        formasPagamentoStr
    );
} 
}