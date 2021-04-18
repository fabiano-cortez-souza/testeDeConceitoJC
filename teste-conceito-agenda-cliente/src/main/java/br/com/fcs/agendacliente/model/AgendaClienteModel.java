package br.com.fcs.agendacliente.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Entity(name = "agendaCliente")
public class AgendaClienteModel {

	@Id
	@Field(name = "idAgenda")
	private Double idAgenda;

	@Field(name = "cliente")
	private ClienteModel cliente;

	@Field(name = "agendaDataHora")
	private String agendaDataHora;

    @Field(name = "criacaoAgendaDataHora")
    private String criacaoaAgendaDataHora;	
	
	@Field(name = "modeloCelular")
	private String modeloCelular;

	@Field(name = "descricaoFalha")
	private String descricaoFalha;

	public AgendaClienteModel() {}

    public AgendaClienteModel(Double idAgenda, ClienteModel cliente, String agendaDataHora,
            String criacaoaAgendaDataHora, String modeloCelular, String descricaoFalha) {
        super();
        this.idAgenda = idAgenda;
        this.cliente = cliente;
        this.agendaDataHora = agendaDataHora;
        this.criacaoaAgendaDataHora = criacaoaAgendaDataHora;
        this.modeloCelular = modeloCelular;
        this.descricaoFalha = descricaoFalha;
    }

    public Double getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Double idAgenda) {
        this.idAgenda = idAgenda;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
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
	
}
