package com.infnet.TP5.src.repository;

import java.util.List;
import java.util.Optional;

import com.infnet.TP5.src.model.Pedido;

public interface IPedidoRepository {
  Pedido salvar(Pedido pedido);
  long getProximoId();
  Optional<Pedido> buscarPorId(long id);
  List<Pedido> buscarPorClienteId(long clienteId); 
}
