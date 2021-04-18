package br.com.fcs.agendacliente.dto;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;

public class AgendaClienteSolicitationDTO {
	
	@Field(name = "requestId")
	private String requestId;

	@Field(name = "msisdn")
	private String msisdn;

	@Field(name = "startDate")
	private String startDate;

	@Field(name = "endDate")
	private String endDate;

	@Field(name = "numPage")
	private String numPage;

	@Field(name = "numRecord")
	private String numRecord;
	
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	
}
