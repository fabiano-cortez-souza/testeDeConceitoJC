package br.com.fcs.agendacliente.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Entity(name = "cliente")
public class ClienteModel {

    @Id
    @Field(name = "idCliente")
    private Double idCliente;
    
    @Field(name = "nome")
    private String nome;
    
    @Field(name = "endereco")
    private String endereco;
    
    @Field(name = "cpf")
    private String cpf;
    
    public ClienteModel(Double idCliente, String nome, String endereco, String cpf) {
        super();
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
    }
    
    public Double getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Double idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
