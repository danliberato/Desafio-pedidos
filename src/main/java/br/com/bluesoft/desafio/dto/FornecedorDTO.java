package br.com.bluesoft.desafio.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FornecedorDTO {

    private String cnpj;

    private String nome;

    private List<OfertaDTO> precoList;

}
