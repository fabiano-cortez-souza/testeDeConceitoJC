package br.com.fcs.agendatecnico.vo;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendatecnico.pubsub.PubSubTopicObject;

public class DedicatedAccountsVO extends PubSubTopicObject{

	private static final long serialVersionUID = 1L;

	@JsonProperty("dedicatedAccountID")
	private String dedicatedAccountID;
    
    @JsonProperty("accountBalance")
	private String accountBalance;
    
    @JsonProperty("dedicatedAccountUnitType")
	private String dedicatedAccountUnitType;
	
    @ConstructorProperties({"dedicatedAccountID", "accountBalance", "dedicatedAccountUnitType"})
	public DedicatedAccountsVO(String dedicatedAccountID, String accountBalance, String dedicatedAccountUnitType) {
		this.dedicatedAccountID = dedicatedAccountID;
		this.accountBalance = accountBalance;
		this.dedicatedAccountUnitType = dedicatedAccountUnitType;
	}
	
	public DedicatedAccountsVO(String dedicatedAccountID, String accountBalance) {
		super();
		this.dedicatedAccountID = dedicatedAccountID;
		this.accountBalance = accountBalance;
	}
	
	public String getDedicatedAccountUnitType() {
		return dedicatedAccountUnitType;
	}

	public void setDedicatedAccountUnitType(String dedicatedAccountUnitType) {
		this.dedicatedAccountUnitType = dedicatedAccountUnitType;
	}

	public String getDedicatedAccountID() {
		return dedicatedAccountID;
	}
	public void setDedicatedAccountID(String dedicatedAccountID) {
		this.dedicatedAccountID = dedicatedAccountID;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public boolean equals(DedicatedAccountsVO dedicatedAccounts) {
	    if (this == dedicatedAccounts) {
	        return true;
	    }
	    if (dedicatedAccounts == null || getClass() != dedicatedAccounts.getClass()) {
	        return false;
	    }

		boolean a = this.dedicatedAccountID.equals(dedicatedAccounts.getDedicatedAccountID());
		boolean b = this.accountBalance.equals(dedicatedAccounts.getAccountBalance());
		boolean c = this.dedicatedAccountUnitType.equals(dedicatedAccounts.getDedicatedAccountUnitType());
		
		return a && b && c;
	}
}
