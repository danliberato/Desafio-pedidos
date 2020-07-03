package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.FornecedorEscolhidoDTO;
import br.com.bluesoft.desafio.dto.OfertaDTO;
import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;
import br.com.bluesoft.desafio.service.FornecedorService;
import br.com.bluesoft.desafio.service.PedidoService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;
    private FornecedorService fornecedorService;
    private ProdutoRepository produtoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, FornecedorService fornecedorService, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.fornecedorService = fornecedorService;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Pedido> save(List<PedidoDTO> pedidos) {
        Map<String, Pedido> groupPedidos = new HashMap<>();

        // Pega os pedidos já analisados
        List<PedidoDTO> list = new ArrayList<>();
        for (PedidoDTO pedido : pedidos) {
            if (pedido.getQtdMinima() > 0) {
                list.add(pedido);
            }
        }

        // verifica se os fornecedores existem
        for (PedidoDTO p : list) {
            FornecedorEscolhidoDTO fornecedorEscolhidoDTO = this.escolheFornecedor(p);
            if (fornecedorEscolhidoDTO.getCnpj() == null || fornecedorEscolhidoDTO.getPreco() == null) {
                if(Objects.isNull(this.produtoRepository.findByGtin(p.getGtin()))){
                    System.out.println("Fornecedor " + fornecedorEscolhidoDTO.getNome() + " não encontrado!");
                }
            }


            Pedido pedido = groupPedidos.get(fornecedorEscolhidoDTO.getCnpj());
            if (Objects.isNull(pedido)) {
                pedido = buildPedido(fornecedorEscolhidoDTO, p.getGtin());
            } else {
                pedido.getItemList().add(this.buildItem(fornecedorEscolhidoDTO, p.getGtin()));
            }
            groupPedidos.put(fornecedorEscolhidoDTO.getCnpj(), pedido);
        }
        List<Pedido> pedidosEfetuados = groupPedidos.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        return (List<Pedido>) this.pedidoRepository.save(pedidosEfetuados);

    }

    public FornecedorEscolhidoDTO escolheFornecedor(PedidoDTO pedidoDTO){
        FornecedorEscolhidoDTO fornecedorEscolhidoDTO = new FornecedorEscolhidoDTO();
        List<FornecedorDTO> fornecedores = this.fornecedorService.getFornecedoresByProdutoGtin(pedidoDTO.getGtin());
        for (FornecedorDTO fornecedor : fornecedores) {

            OfertaDTO oferta = getOferta(pedidoDTO.getQtdMinima(), fornecedor.getPrecoList()); //TODO: melhorar complexidade
            if (Objects.nonNull(oferta)) {
                if (fornecedorEscolhidoDTO.getPreco() == null || fornecedorEscolhidoDTO.getPreco().compareTo(oferta.getPreco()) > 0) {

                    fornecedorEscolhidoDTO.setCnpj(fornecedor.getCnpj());
                    fornecedorEscolhidoDTO.setNome(fornecedor.getNome());
                    fornecedorEscolhidoDTO.setPreco(oferta.getPreco());
                    fornecedorEscolhidoDTO.setQtdMinima(oferta.getQtdMinima());
                }
            }
        }
        return fornecedorEscolhidoDTO;
    }

    private Pedido buildPedido(FornecedorEscolhidoDTO fornecedorEscolhidoDTO, String gtin) {
        Pedido pedido = new Pedido();

        //setting Fornecedor into Pedido
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCpnj(fornecedorEscolhidoDTO.getCnpj());
        fornecedor.setNome(fornecedorEscolhidoDTO.getNome());
        pedido.setFornecedor(fornecedor);

        //setting Item into Pedido
        pedido.getItemList().add(this.buildItem(fornecedorEscolhidoDTO,gtin));

        return pedido;
    }

    private Item buildItem(FornecedorEscolhidoDTO fornecedorEscolhidoDTO, String gtin) {
        Integer qtd = fornecedorEscolhidoDTO.getQtdMinima();
        Item item = new Item();
        item.setQtd(qtd);
        item.setPreco(fornecedorEscolhidoDTO.getPreco());
        item.setTotal(fornecedorEscolhidoDTO.getPreco().multiply(BigDecimal.valueOf(item.getQtd()))); // avoiding forced cast
        item.setProduto(produtoRepository.findByGtin(gtin));
        return item;
    }

    private OfertaDTO getOferta(Integer qtd, List<OfertaDTO> ofertas) {
        OfertaDTO ofertaEscolhida = null;
        for (OfertaDTO o : ofertas) {
            if(Objects.equals(qtd, ofertaEscolhida.getQtdMinima())) {
                ofertaEscolhida = o;
            }
        }
        return ofertaEscolhida;
    }

}
