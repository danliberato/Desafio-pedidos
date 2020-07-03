package br.com.bluesoft.desafio.repository;

import br.com.bluesoft.desafio.model.Pedido;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends CrudRepository<Pedido, String> {
}
