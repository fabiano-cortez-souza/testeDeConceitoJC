package br.com.fcs.agendacliente.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Entity(name = "transactionHistory")
public class AgendaClienteModel {

	@Id
	@Field(name = "id")
	private String id;

	@Field(name = "msisdn")
	private String msisdn;

	@Field(name = "type")
	private String type;

	@Field(name = "amount")
	private String amount;

	@Field(name = "description")
	private String description;

	@Field(name = "transactionID")
	private String transactionID;
	
	@Field(name = "timestamp")
	private String timestamp;
	
	@Field(name = "eventTimeStamp")
	private String eventTimeStamp;
	
	@Field(name = "requestId")
	private String requestId;

	public AgendaClienteModel() {}
	
	public AgendaClienteModel(String timestamp, 
	                               String id, 
	                               String msisdn, 
	                               String type, 
	                               String amount,
	                               String description, 
	                               String transactionID, 
	                               String requestId, 
	                               String eventTimeStamp) {
		this.id = id;
		this.msisdn = msisdn;
		this.type = type;
		this.amount = amount;
		this.description = description;
		this.transactionID = transactionID;
		this.timestamp = timestamp;
		this.requestId = requestId;
		this.eventTimeStamp = eventTimeStamp;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timeStamp) {
		this.timestamp = timeStamp;
	}
	public String getRequestId() {
		return requestId;
	}

	public String getEventTimeStamp() {
		return eventTimeStamp;
	}

	public void setEventTimeStamp(String eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
