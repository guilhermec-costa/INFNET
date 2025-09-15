package com.infnet.TP5.src.repository.csv;

import com.infnet.TP5.src.model.ItemPedido;
import com.infnet.TP5.src.model.Pedido;
import com.infnet.TP5.src.repository.IPedidoRepository;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoRepositoryCSV implements IPedidoRepository {
  private static final String CSV_FILE = "pedidos.csv";
  private static final String CSV_HEADER = "id;clienteId;valorTotal;status;dataPedido";
  private static final String CSV_SEPARATOR = ";";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public PedidoRepositoryCSV() {
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
  public Pedido salvar(Pedido pedido) {
    if (buscarPorId(pedido.getId()).isPresent()) {
      atualizarPedido(pedido);
    } else {
      inserirPedido(pedido);
    }
    return pedido;
  }

  @Override
  public long getProximoId() {
    long maxId = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      reader.readLine(); // pular header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 1) {
          long id = Long.parseLong(dados[0]);
          if (id > maxId) {
            maxId = id;
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
    }

    return maxId + 1;
  }

  @Override
  public Optional<Pedido> buscarPorId(long id) {
    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      reader.readLine(); // pular header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 5 && Long.parseLong(dados[0]) == id) {
          return Optional.of(criarPedidoBasicoDoCSV(dados));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
    }
    return Optional.empty();
  }

  @Override
  public List<Pedido> buscarPorClienteId(long clienteId) {
    List<Pedido> pedidos = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      reader.readLine(); // pular header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 5 && Long.parseLong(dados[1]) == clienteId) {
          pedidos.add(criarPedidoBasicoDoCSV(dados));
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Erro ao ler arquivo CSV: " + e.getMessage());
    }

    return pedidos;
  }

  private void inserirPedido(Pedido pedido) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE, true))) {
      writer.println(formatarPedidoParaCSV(pedido));
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar pedido: " + e.getMessage());
    }
  }

  private void atualizarPedido(Pedido pedido) {
    List<String> linhas = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
      String linha;
      linhas.add(reader.readLine()); // adicionar header

      while ((linha = reader.readLine()) != null) {
        String[] dados = linha.split(CSV_SEPARATOR);
        if (dados.length >= 5 && Long.parseLong(dados[0]) == pedido.getId()) {
          linhas.add(formatarPedidoParaCSV(pedido));
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
      throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage());
    }
  }

  private Pedido criarPedidoBasicoDoCSV(String[] dados) {
    throw new UnsupportedOperationException("Ainda sob implementação");
  }

  private String formatarPedidoParaCSV(Pedido pedido) {
    String itensStr = pedido.getItens().stream()
        .map(ItemPedido::toString)
        .collect(Collectors.joining("; "));

    String enderecoStr = pedido.getEnderecoEntrega() != null
        ? pedido.getEnderecoEntrega().toString()
        : "";

    String formaPagamentoStr = pedido.getFormaPagamento() != null
        ? pedido.getFormaPagamento().toString()
        : "";

    return String.format("%d;%d;%s;%s;%s;%s;%s;%s",
        pedido.getId(),
        pedido.getCliente().getId(),
        pedido.getValorTotal().toString(),
        pedido.getStatus().name(),
        pedido.getDataPedido().format(DATE_FORMATTER),
        itensStr,
        enderecoStr,
        formaPagamentoStr);
  }
}