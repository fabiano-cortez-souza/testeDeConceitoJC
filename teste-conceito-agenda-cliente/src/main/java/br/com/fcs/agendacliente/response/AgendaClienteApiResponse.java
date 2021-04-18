package br.com.fcs.agendacliente.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.fcs.agendacliente.enums.ErrorType;
import br.com.fcs.agendacliente.enums.SuccessMessage;
import br.com.fcs.agendacliente.vo.AgendaClienteVO;

public class AgendaClienteApiResponse extends ApiResponse {

    @JsonInclude(Include.NON_NULL)
    private Integer currentPage;
    
    @JsonInclude(Include.NON_NULL)
    private Integer totalNumPage;

    @JsonInclude(Include.NON_NULL)
    private Long totalNumRecord;
    
    @JsonInclude(Include.NON_NULL)
    private List<AgendaClienteVO> transactions;

	public AgendaClienteApiResponse() {}
	
	public AgendaClienteApiResponse(SuccessMessage successMessage,
                       String msisdn,
                       Integer currentPage,
                       Integer totalNumPage,
                       Long totalNumRecord,
                       int httpCode,
                       List<AgendaClienteVO> transactions) {

		
	    this.setMsisdn(msisdn);
	    this.setMessageDetail(successMessage.getDesc());
	    this.setCode(successMessage.getCode());
	    this.setHttpCode(httpCode);
	    this.currentPage = currentPage;
	    this.totalNumPage = totalNumPage;
	    this.totalNumRecord = totalNumRecord;
	    this.transactions = transactions;
	}
	
	public AgendaClienteApiResponse(ErrorType errorType, int httpcode) {
		super(errorType, httpcode);
    }
	
	public AgendaClienteApiResponse(SuccessMessage successMessage, int httpcode) {
		super(successMessage, httpcode);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalNumPage() {
        return totalNumPage;
    }

    public void setTotalNumPage(Integer totalNumPage) {
        this.totalNumPage = totalNumPage;
    }

    public Long getTotalNumRecord() {
        return totalNumRecord;
    }

    public void setTotalNumRecord(Long totalNumRecord) {
        this.totalNumRecord = totalNumRecord;
    }
    
    public List<AgendaClienteVO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<AgendaClienteVO> transactions) {
        this.transactions = transactions;
    }
	
}
