package br.com.bluesoft.desafio.business;

import br.com.bluesoft.desafio.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PedidoBO {

    @Autowired
    private PedidoRepository pedidoRepository;


}
