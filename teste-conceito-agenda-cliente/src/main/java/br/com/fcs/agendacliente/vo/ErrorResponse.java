package br.com.fcs.agendacliente.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("httpCode")
	private String httpCode;

	@JsonProperty("errorCode")
	private String errorCode;
	
	@JsonProperty("message")
    private String message;

	@JsonProperty("detailedMessage")
    private String detailedMessage;

	
	public ErrorResponse() {}

	public ErrorResponse(String httpCode, String errorCode, String message, String detailedMessage) {
		super();
		this.httpCode = httpCode;
		this.errorCode = errorCode;
		this.message = message;
		this.detailedMessage = detailedMessage;
	}

    public String getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(String httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
