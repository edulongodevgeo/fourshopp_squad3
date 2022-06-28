package br.com.fourshopp.entities;

import javax.persistence.*;

@Entity
//@PrimaryKeyJoinColumn(name = "cd_administrador")
@Table(name = "tb_administrador")
public class Administrador{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ds_cpf")
    private String cpf;
    @Column(name = "ds_senha")
    private String senha;

    public Administrador() {}

    public Administrador(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}





