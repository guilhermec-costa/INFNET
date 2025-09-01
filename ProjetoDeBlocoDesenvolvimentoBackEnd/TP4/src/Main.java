package src;

import src.cli.EcommerceCli;
import src.repository.ClienteRepositoryInMemory;
import src.repository.IClienteRepository;
import src.repository.IPedidoRepository;
import src.repository.IProdutoRepository;
import src.repository.PedidoRepositoryInMemory;
import src.repository.ProdutoRepositoryInMemory;
import src.service.AutenticacaoService;
import src.service.ClienteService;
import src.service.PedidoService;

public class Main {
    public static void main(String[] args) {
        IClienteRepository clienteRepo = new ClienteRepositoryInMemory();
        IProdutoRepository produtoRepo = new ProdutoRepositoryInMemory();
        IPedidoRepository pedidoRepo = new PedidoRepositoryInMemory();

        AutenticacaoService authService = new AutenticacaoService(clienteRepo);
        PedidoService pedidoService = new PedidoService(pedidoRepo, produtoRepo);
        ClienteService clienteService = new ClienteService();

        EcommerceCli cli = new EcommerceCli(authService, pedidoService, clienteService, produtoRepo);
        cli.iniciar();
    }
}