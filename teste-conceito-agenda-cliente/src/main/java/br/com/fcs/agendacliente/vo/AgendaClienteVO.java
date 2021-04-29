package br.com.fcs.agendacliente.vo;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendacliente.utils.JsonUtils;

public class AgendaClienteVO {
    
    @Id
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("idCliente")
    private Integer idCliente;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("nome")
    private String nome;
   
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("endereco")
    private String endereco;
   
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("cpf")
    private String cpf;
  
    public AgendaClienteVO() {}
     
    public AgendaClienteVO(Integer idCliente, String nome, String endereco, String cpf) {
        super();
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
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

    @Override
    public String toString() {
        return JsonUtils.parseToJsonString(this);
    }
    
}
