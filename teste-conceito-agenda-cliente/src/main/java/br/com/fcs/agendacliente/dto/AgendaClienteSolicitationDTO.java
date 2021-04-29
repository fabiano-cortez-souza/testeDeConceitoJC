package br.com.fcs.agendacliente.dto;

public class AgendaClienteSolicitationDTO {
	
    private Integer idAgenda;  
    private Integer idCliente;
    private String nome;
    private String endereco;
    private String cpf;
    private String agendaDataHora;
    private String criacaoAgendaDataHora;
    private String modeloCelular;
    private String descricaoFalha;
	private String startDate;
	private String endDate;
	private String numPage;
	private String numRecord;
	
	public AgendaClienteSolicitationDTO() {};
	 
    public AgendaClienteSolicitationDTO(Integer idAgenda, Integer idCliente, String nome, String endereco, String cpf,
            String agendaDataHora, String criacaoAgendaDataHora, String modeloCelular, String descricaoFalha,
            String startDate, String endDate, String numPage, String numRecord) {
        super();
        this.idAgenda = idAgenda;
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.agendaDataHora = agendaDataHora;
        this.criacaoAgendaDataHora = criacaoAgendaDataHora;
        this.modeloCelular = modeloCelular;
        this.descricaoFalha = descricaoFalha;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numPage = numPage;
        this.numRecord = numRecord;
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

    public String getCriacaoAgendaDataHora() {
        return criacaoAgendaDataHora;
    }

    public void setCriacaoAgendaDataHora(String criacaoAgendaDataHora) {
        this.criacaoAgendaDataHora = criacaoAgendaDataHora;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNumPage() {
        return numPage;
    }

    public void setNumPage(String numPage) {
        this.numPage = numPage;
    }

    public String getNumRecord() {
        return numRecord;
    }

    public void setNumRecord(String numRecord) {
        this.numRecord = numRecord;
    }
	
}
