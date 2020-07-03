package br.com.bluesoft.desafio.api;

import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {


    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/novosPedidos")
    public List<Pedido> save(@RequestBody List<PedidoDTO> pedidos){
        return this.pedidoService.save(pedidos);
    }

}
