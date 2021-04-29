package br.com.fcs.agendacliente.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "agendaCliente")
public class AgendaClienteModel {
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer idAgenda;
	
	@Column(nullable = false)
    private Integer idCliente;
    
	@Column(nullable = false)
    private String nome;
    
	@Column(nullable = false)
    private String endereco;
    
	@Column(nullable = false)
    private String cpf;

	@Column(nullable = false)
	private String agendaDataHora;

	@Column(nullable = false)
    private String criacaoaAgendaDataHora;	
	
	@Column(nullable = false)
	private String modeloCelular;

	@Column(nullable = false)
	private String descricaoFalha;

	@ManyToOne
	private ClienteModel cliente;
		
	public AgendaClienteModel() {}

    public AgendaClienteModel(Integer idAgenda, Integer idCliente, String nome, String endereco, String cpf,
            String agendaDataHora, String criacaoaAgendaDataHora, String modeloCelular, String descricaoFalha) {
        super();
        this.idAgenda = idAgenda;
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.agendaDataHora = agendaDataHora;
        this.criacaoaAgendaDataHora = criacaoaAgendaDataHora;
        this.modeloCelular = modeloCelular;
        this.descricaoFalha = descricaoFalha;
    }

    public Integer getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Integer idAgenda) {
        this.idAgenda = idAgenda;
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

    public String getAgendaDataHora() {
        return agendaDataHora;
    }

    public void setAgendaDataHora(String agendaDataHora) {
        this.agendaDataHora = agendaDataHora;
    }

    public String getCriacaoaAgendaDataHora() {
        return criacaoaAgendaDataHora;
    }

    public void setCriacaoaAgendaDataHora(String criacaoaAgendaDataHora) {
        this.criacaoaAgendaDataHora = criacaoaAgendaDataHora;
    }

    public String getModeloCelular() {
        return modeloCelular;
    }

    public void setModeloCelular(String modeloCelular) {
        this.modeloCelular = modeloCelular;
    }

    public String getDescricaoFalha() {
        return descricaoFalha;
    }

    public void setDescricaoFalha(String descricaoFalha) {
        this.descricaoFalha = descricaoFalha;
    }

    public Optional<ClienteModel> getCliente() {
        return Optional.ofNullable(cliente);
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

}
