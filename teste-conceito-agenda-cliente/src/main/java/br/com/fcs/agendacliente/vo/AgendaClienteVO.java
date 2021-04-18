package br.com.fcs.agendacliente.vo;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.fcs.agendacliente.utils.JsonUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgendaClienteVO {

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("msisdn")
    private String msisdn;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("type")
    private String type;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("amount")
    private String amount;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("description")
    private String description;
    

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("transactionID")
    private String transactionID;

    @Id
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("id")
    private String id;
    
    public AgendaClienteVO() {}
    
    public AgendaClienteVO(String timestamp,
                                String msisdn,
                                String type, 
                                String amount,
                                String description, 
                                String transactionID ) {
    
        this.timestamp = timestamp;
        this.msisdn = msisdn;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionID = transactionID;
    }
    
    public AgendaClienteVO(String timestamp,
            String msisdn,
            String type,
            String amount,
            String transactionID) {

        this.timestamp = timestamp;
        this.msisdn = msisdn;
        this.type = type;
        this.amount = amount;
        this.transactionID = transactionID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return JsonUtils.parseToJsonString(this);
    }
    
}
