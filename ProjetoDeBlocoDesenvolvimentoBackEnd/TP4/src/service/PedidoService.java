package src.service;

import java.util.List;
import java.util.Optional;

import src.model.Cliente;
import src.model.Endereco;
import src.model.FormaPagamento;
import src.model.ItemPedido;
import src.model.Pedido;
import src.model.Produto;
import src.model.StatusPedido;
import src.repository.IPedidoRepository;
import src.repository.IProdutoRepository;

public class PedidoService {
    private IPedidoRepository pedidoRepository;
    private IProdutoRepository produtoRepository;

    public PedidoService(IPedidoRepository pedidoRepository, IProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido criarPedido(Cliente cliente, List<ItemPedido> carrinho, Endereco endereco, FormaPagamento formaPagamento) {
        for (ItemPedido item : carrinho) {
            Optional<Produto> produtoOpt = produtoRepository.buscarPorId(item.getProduto().getId());
            if (produtoOpt.isEmpty() || produtoOpt.get().getEstoque() < item.getQuantidade()) {
                System.out.println("Erro: Estoque insuficiente para o produto " + item.getProduto().getNome());
                return null;
            }
        }

        for (ItemPedido item : carrinho) {
            Produto p = item.getProduto();
            p.setEstoque(p.getEstoque() - item.getQuantidade());
        }

        long proximoId = pedidoRepository.getProximoId();
        Pedido novoPedido = new Pedido(proximoId, cliente, carrinho, endereco, formaPagamento);
        return pedidoRepository.salvar(novoPedido);
    }

    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        return pedidoRepository.buscarPorClienteId(cliente.getId());
    }

    public boolean cancelarPedido(Cliente cliente, long pedidoId) {
        Optional<Pedido> pedidoOpt = pedidoRepository.buscarPorId(pedidoId);
        
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            if (pedido.getCliente().getId() == cliente.getId() && pedido.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
                pedido.setStatus(StatusPedido.CANCELADO);

                for (ItemPedido item : pedido.getItens()) {
                    Produto p = item.getProduto();
                    p.setEstoque(p.getEstoque() + item.getQuantidade());
                }
                return true;
            }
        }
        return false;
    }
}