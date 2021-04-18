package br.com.fcs.agendatecnico.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendatecnico.utils.JsonUtils;

public class RequestOcsJsonVO {

	@JsonProperty("msisdn")
    private String msisdn;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("transactionID")
    private String transactionID;
    
    @JsonProperty("dedicatedAccountID")
    private String dedicatedAccountID;
    
    @JsonProperty("negotiatedCapabilities")
    private NegotiatedCapabilities negotiatedCapabilities;
    
    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("originNodeType")
    private String originNodeType;
    
    @JsonProperty("originHostName")
    private String originHostName;

	public RequestOcsJsonVO() {}

	public RequestOcsJsonVO(String msisdn,
							String timestamp,
							String transactionID,
							String amount,
							String originNodeType,
							String originHostName,
							String dedicatedAccountID,
							String value_805646916,
							String value_1576) {
	    
		super();
		this.msisdn = msisdn;
	    this.timestamp = timestamp;
        this.transactionID = transactionID;
        this.amount = amount;
        this.originNodeType = originNodeType;
        this.originHostName = originHostName;

	    List<Value> listValue = new ArrayList<>();
	    Value value805646916 = new Value();
	    Value value1576 = new Value();
	    NegotiatedCapabilities negotiatedCapabilities = new NegotiatedCapabilities();
	    
	    value805646916.setValue(value_805646916);
	    listValue.add(value805646916);
	    value1576.setValue(value_1576);	    
	    listValue.add(value1576);	    
	    negotiatedCapabilities.setMember(listValue);

	    this.dedicatedAccountID = dedicatedAccountID;
	    this.negotiatedCapabilities = negotiatedCapabilities;
	} 

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getDedicatedAccountID() {
		return dedicatedAccountID;
	}

	public void setDedicatedAccountID(String dedicatedAccountID) {
		this.dedicatedAccountID = dedicatedAccountID;
	}

	public NegotiatedCapabilities getNegotiatedCapabilities() {
		return negotiatedCapabilities;
	}

	public void setNegotiatedCapabilities(NegotiatedCapabilities negotiatedCapabilities) {
		this.negotiatedCapabilities = negotiatedCapabilities;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOriginNodeType() {
		return originNodeType;
	}

	public void setOriginNodeType(String originNodeType) {
		this.originNodeType = originNodeType;
	}

	public String getOriginHostName() {
		return originHostName;
	}

	public void setOriginHostName(String originHostName) {
		this.originHostName = originHostName;
	}
	
    @Override
    public String toString() {
        return JsonUtils.parseToJsonString(this);
    }
	
	public boolean equals(RequestOcsJsonVO ocsCustomerBalanceVO) {
	    if (this == ocsCustomerBalanceVO) {
	        return true;
	    }
	    if (ocsCustomerBalanceVO == null || getClass() != ocsCustomerBalanceVO.getClass()) {
	        return false;
	    }
	    
		boolean a = this.msisdn.equals(ocsCustomerBalanceVO.getMsisdn());
		boolean b = this.timestamp.equals(ocsCustomerBalanceVO.getTimestamp());
		boolean c = this.transactionID.equals(ocsCustomerBalanceVO.getTransactionID());
		boolean d = this.amount.equals(ocsCustomerBalanceVO.getAmount());
		boolean e = this.originNodeType.equals(ocsCustomerBalanceVO.getOriginNodeType());
		boolean f = this.originHostName.equals(ocsCustomerBalanceVO.getOriginHostName());
		
		return a && b && c && d && e && f;
	}

	public class NegotiatedCapabilities {
		@JsonProperty("member")
		private List<Value> member;

		public List<Value> getMember() {
			return member;
		}

		public void setMember(List<Value> member) {
			this.member = member;
		}
	}
	
	public class Value {
		@JsonProperty("value")
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	} 
}