package com.infnet.TP5.src.service;

import java.util.ArrayList;
import java.util.List;

import com.infnet.TP5.src.controller.PedidoController.PedidoRequest;
import com.infnet.TP5.src.exception.BusinessException;
import com.infnet.TP5.src.exception.ResourceNotFoundException;
import com.infnet.TP5.src.model.Cliente;
import com.infnet.TP5.src.model.Endereco;
import com.infnet.TP5.src.model.FormaPagamento;
import com.infnet.TP5.src.model.ItemPedido;
import com.infnet.TP5.src.model.Pedido;
import com.infnet.TP5.src.model.Produto;
import com.infnet.TP5.src.model.StatusPedido;
import com.infnet.TP5.src.repository.IPedidoRepository;
import com.infnet.TP5.src.repository.IProdutoRepository;
import com.infnet.TP5.src.repository.csv.ProdutoRepositoryCSV;
import com.infnet.TP5.src.util.ValidationUtil;

public class PedidoService {
    
    private final IPedidoRepository pedidoRepository;
    private final IProdutoRepository produtoRepository;
    
    public PedidoService(IPedidoRepository pedidoRepository, IProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }
    
    public Pedido processarCriacaoPedido(Cliente cliente, PedidoRequest req) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        ValidationUtil.validateNotNull(req, "Request do pedido");
        ValidationUtil.validateNotNull(req.carrinho, "Carrinho");
        
        if (req.carrinho.isEmpty()) {
            throw new BusinessException("Carrinho não pode estar vazio");
        }
        
        List<ItemPedido> carrinho = new ArrayList<>();
        for (var itemReq : req.carrinho) {
            ValidationUtil.validatePositive(itemReq.produtoId, "ID do produto");
            ValidationUtil.validatePositiveOrZero(itemReq.quantidade, "Quantidade");
            
            if (itemReq.quantidade == 0) {
                throw new BusinessException("Quantidade deve ser maior que zero");
            }
            
            Produto produto = produtoRepository.buscarPorId(itemReq.produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemReq.produtoId));
                
            carrinho.add(new ItemPedido(produto, itemReq.quantidade));
        }
        
        Endereco endereco = cliente.getEnderecos().stream()
            .filter(e -> e.getId() == req.enderecoId)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
            
        FormaPagamento formaPagamento = cliente.getFormasPagamento().stream()
            .filter(fp -> fp.getId() == req.formaPagamentoId)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Forma de pagamento não encontrada"));
        
        return criarPedido(cliente, carrinho, endereco, formaPagamento);
    }
    
    private Pedido criarPedido(Cliente cliente, List<ItemPedido> carrinho, 
                              Endereco endereco, FormaPagamento formaPagamento) {
        
        for (ItemPedido item : carrinho) {
            Produto produto = item.getProduto();
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new BusinessException(
                    String.format("Estoque insuficiente para %s. Disponível: %d, Solicitado: %d", 
                        produto.getNome(), produto.getEstoque(), item.getQuantidade())
                );
            }
        }
        
        for (ItemPedido item : carrinho) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            
            if (produtoRepository instanceof ProdutoRepositoryCSV) {
                ((ProdutoRepositoryCSV) produtoRepository).salvar(produto);
            }
        }
        
        long proximoId = pedidoRepository.getProximoId();
        Pedido novoPedido = new Pedido(proximoId, cliente, carrinho, endereco, formaPagamento);
        
        return pedidoRepository.salvar(novoPedido);
    }
    
    public List<Pedido> listarPedidosPorCliente(Cliente cliente) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        return pedidoRepository.buscarPorClienteId(cliente.getId());
    }
    
    public boolean cancelarPedido(Cliente cliente, long pedidoId) {
        ValidationUtil.validateNotNull(cliente, "Cliente");
        ValidationUtil.validatePositive(pedidoId, "ID do pedido");
        
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado: " + pedidoId));
        
        if (pedido.getCliente().getId() != cliente.getId()) {
            throw new BusinessException("Você não tem permissão para cancelar este pedido");
        }
        
        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new BusinessException("Apenas pedidos aguardando pagamento podem ser cancelados");
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.buscarPorId(item.getProduto().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + item.getProduto().getId()));
                
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            
            if (produtoRepository instanceof com.infnet.TP5.src.repository.csv.ProdutoRepositoryCSV) {
                ((com.infnet.TP5.src.repository.csv.ProdutoRepositoryCSV) produtoRepository).salvar(produto);
            }
        }
        
        pedidoRepository.salvar(pedido);
        
        return true;
    }
}