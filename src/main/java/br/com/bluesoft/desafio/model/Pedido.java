package br.com.bluesoft.desafio.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date dataCriacao;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Fornecedor fornecedor;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> itemList;


}


