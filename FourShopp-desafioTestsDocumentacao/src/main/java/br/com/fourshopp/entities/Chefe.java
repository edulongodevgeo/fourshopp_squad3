package br.com.fourshopp.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_chefe")
@Setter
@Getter
@PrimaryKeyJoinColumn(name = "cd_chefe")
public class Chefe extends Pessoa implements Serializable {


    private static final long serialVersionUID = -8563944577868369975L;

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_hireDate")
    private Date dataContratacao;
    @Column(name = "ds_cargo") //@Enumerated(EnumType.STRING)
    private Cargo cargo;

    @Column(name = "ds_setor")
    private Setor setor;

    @Column(name = "vl_salario")
    private double salario;

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //private Chefe chefe;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Produto> listaProdutos;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "conta_corrente_id")
//    private ContaCorrente contaCorrente;

    public Chefe() {}

    public Chefe(String nome, String email, String celular, String password, String cpf, Endereco endereco,
                       Date dataContratacao, Cargo cargo, Setor setor, double salario) {
        super(nome, email, celular, password, cpf, null);
        this.dataContratacao = dataContratacao;
        this.cargo = Cargo.CHEFE_SECAO;
        this.setor = setor;
        this.salario = salario;
        //this.chefe = chefe;
    }
}
