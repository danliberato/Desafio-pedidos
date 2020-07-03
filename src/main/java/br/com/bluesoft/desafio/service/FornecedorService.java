package br.com.bluesoft.desafio.service;

import br.com.bluesoft.desafio.dto.FornecedorDTO;

import java.util.List;

public interface FornecedorService {

    List<FornecedorDTO> getFornecedoresByProdutoGtin(String gtin);
}
