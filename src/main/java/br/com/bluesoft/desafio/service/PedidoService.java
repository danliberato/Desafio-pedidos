package br.com.bluesoft.desafio.service;

import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.model.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> save(List<PedidoDTO> pedidos);
}
