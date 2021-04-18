package br.com.fcs.agendatecnico.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendatecnico.pubsub.PubSubTopicObject;

public class RequestAgendaTecnicoVO  extends PubSubTopicObject{

	private static final long serialVersionUID = 1L;

	@JsonProperty("msisdn")
    private String msisdn;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("transactionID")
    private String transactionID;
    
    @JsonProperty("transactionCode")
    private String transactionCode;
    
    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("amount")
    private String amount;
    
    @JsonProperty("originNodeType")
    private String originNodeType;
    
    @JsonProperty("originHostName")
    private String originHostName;
   
    @JsonProperty("dedicatedAccountUpdateInformation")
    private DedicatedAccountsVO dedicatedAccountUpdateInformation;

	public RequestAgendaTecnicoVO() {}

	public RequestAgendaTecnicoVO(String msisdn,
							        String timestamp,
									String transactionID,
									String transactionCode,
									String transactionType,
									String amount,
									String originNodeType,
									String originHostName,
									DedicatedAccountsVO dedicatedAccountUpdateInformation) {
		super();
		this.msisdn = msisdn;
		this.timestamp = timestamp;
		this.transactionID = transactionID;
		this.transactionCode = transactionCode;
		this.transactionType = transactionType;
		this.amount = amount;
		this.originNodeType = originNodeType;
		this.originHostName = originHostName;
		this.dedicatedAccountUpdateInformation = dedicatedAccountUpdateInformation;
	}
    
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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

	public DedicatedAccountsVO getDedicatedAccountUpdateInformation() {
		return dedicatedAccountUpdateInformation;
	}

	public void setDedicatedAccountUpdateInformation(DedicatedAccountsVO dedicatedAccountUpdateInformation) {
		this.dedicatedAccountUpdateInformation = dedicatedAccountUpdateInformation;
	}
	
	public boolean equals(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
	    if (this == requestCustomerBalanceVO) {
	        return true;
	    }
	    if (requestCustomerBalanceVO == null || getClass() != requestCustomerBalanceVO.getClass()) {
	        return false;
	    }
		boolean a = this.msisdn.equals(requestCustomerBalanceVO.getMsisdn());
		boolean b = this.timestamp.equals(requestCustomerBalanceVO.getTimestamp());
		boolean c = this.transactionID.equals(requestCustomerBalanceVO.getTransactionID());
		boolean d = this.transactionCode.equals(requestCustomerBalanceVO.getTransactionCode());
		boolean e = this.transactionType.equals(requestCustomerBalanceVO.getTransactionType());
		boolean f = this.amount.equals(requestCustomerBalanceVO.getAmount());
		boolean g = this.originNodeType.equals(requestCustomerBalanceVO.getOriginNodeType());
		boolean h = this.originHostName.equals(requestCustomerBalanceVO.getOriginHostName());
		
		boolean i = this.dedicatedAccountUpdateInformation.getAccountBalance().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getAccountBalance());
		boolean j = this.dedicatedAccountUpdateInformation.getDedicatedAccountID().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountID());
		boolean k = this.dedicatedAccountUpdateInformation.getDedicatedAccountUnitType().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountUnitType());
		return a && b && c && d && e && f && g && h && i && j && k;
	}
	
	public boolean equalsFieldsXML(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
		boolean a = this.originHostName.equals(requestCustomerBalanceVO.getOriginHostName()); 
		boolean b = this.originNodeType.equals(requestCustomerBalanceVO.getOriginNodeType());
		boolean c = this.msisdn.equals(requestCustomerBalanceVO.getMsisdn());
		boolean d = this.transactionID.equals(requestCustomerBalanceVO.getTransactionID());
		boolean e = this.timestamp.equals(requestCustomerBalanceVO.getTimestamp());
		
		boolean f = this.dedicatedAccountUpdateInformation.getAccountBalance().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getAccountBalance());
		boolean g = this.dedicatedAccountUpdateInformation.getDedicatedAccountID().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountID());
		boolean h = this.dedicatedAccountUpdateInformation.getDedicatedAccountUnitType().equals(requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountUnitType());
		return a && b && c && d && e && f && g && h;
	}	
}