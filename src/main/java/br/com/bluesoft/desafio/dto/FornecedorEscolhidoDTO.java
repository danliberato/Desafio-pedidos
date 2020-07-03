package br.com.bluesoft.desafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FornecedorEscolhidoDTO {

    private String cnpj;

    private String nome;

    private BigDecimal preco;

    private Integer qtdMinima;
}
