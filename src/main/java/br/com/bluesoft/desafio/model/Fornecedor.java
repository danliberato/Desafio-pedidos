package br.com.bluesoft.desafio.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Fornecedor implements Serializable {

    @Id
    private String cpnj;

    private String nome;

    public Fornecedor(){}

    public Fornecedor(String cpnj, String nome) {
        this.cpnj = cpnj;
        this.nome = nome;
    }
}
