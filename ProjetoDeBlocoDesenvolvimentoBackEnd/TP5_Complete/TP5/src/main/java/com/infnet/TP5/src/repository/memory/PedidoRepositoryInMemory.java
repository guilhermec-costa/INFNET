package com.infnet.TP5.src.repository.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.infnet.TP5.src.model.Pedido;
import com.infnet.TP5.src.repository.IPedidoRepository;

public class PedidoRepositoryInMemory implements IPedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();
    private long proximoId = 1;

    public Pedido salvar(Pedido pedido) {
        pedidos.add(pedido);
        return pedido;
    }
    
    public long getProximoId() {
        return proximoId++;
    }

    public List<Pedido> buscarPorClienteId(long clienteId) {
        return pedidos.stream()
                      .filter(p -> p.getCliente().getId() == clienteId)
                      .collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> buscarPorId(long id) {
      return pedidos.stream().filter(p -> p.getId() == id).findFirst();
    }
}