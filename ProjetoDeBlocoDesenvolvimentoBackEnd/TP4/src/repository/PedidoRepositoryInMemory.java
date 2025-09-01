package src.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import src.model.Pedido;

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