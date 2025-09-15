package com.infnet.TP5.src.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private long id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private Endereco enderecoEntrega;
    private FormaPagamento formaPagamento;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private LocalDateTime dataPedido;

    public Pedido() {}

    public Pedido(long id, Cliente cliente, List<ItemPedido> itens, Endereco enderecoEntrega, FormaPagamento formaPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.itens = itens;
        this.enderecoEntrega = enderecoEntrega;
        this.formaPagamento = formaPagamento;
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.dataPedido = LocalDateTime.now();
        this.valorTotal = itens.stream()
                               .map(ItemPedido::getSubtotal)
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long getId() { return id; }
    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return itens; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido _status) {
      status = _status;
    }
    public BigDecimal getValorTotal() { return valorTotal; }
    public LocalDateTime getDataPedido() { return dataPedido; }
}