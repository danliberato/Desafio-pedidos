package br.com.bluesoft.desafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OfertaDTO {

    private BigDecimal preco;

    private Integer qtdMinima;
}
