package br.com.fcs.agendatecnico.response;

import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendatecnico.enums.ErrorType;
import br.com.fcs.agendatecnico.enums.SuccessMessage;
import br.com.fcs.agendatecnico.utils.JsonUtils;
import br.com.fcs.agendatecnico.utils.Utils;
import br.com.fcs.agendatecnico.vo.ErrorResponse;
import br.com.fcs.agendatecnico.vo.HttpStatusReference;
import br.com.fcs.agendatecnico.vo.ResponseOcsXmlVO;

public class ApiResponse {

	@JsonInclude(Include.NON_NULL)
	HttpServletRequest request;
	
	@JsonInclude(Include.NON_NULL)
	private ErrorType errorType;

	@JsonInclude(Include.NON_NULL)
	private HttpStatus httpStatus;

	@JsonIgnore
	private String requestId;

	@JsonInclude(Include.NON_NULL)
	private String timestamp;

	@JsonInclude(Include.NON_NULL)
	private ZonedDateTime eventTimestamp;
	
	@JsonInclude(Include.NON_NULL)
	private String path;
	
	@JsonInclude(Include.NON_NULL)
	private SuccessMessage successMessage;

	@JsonInclude(Include.NON_NULL)
    private String msisdn;
	
    @JsonInclude(Include.NON_NULL)
    private Integer code;

    @JsonInclude(Include.NON_NULL)
    private String messageDetail;
	
    @JsonInclude(Include.NON_NULL)
    private String amount;
    
    @JsonInclude(Include.NON_NULL)
    private String transactionID;
    
    @JsonInclude(Include.NON_NULL)
    private String message;
    
    @JsonInclude(Include.NON_NULL)
    private int httpCode;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("link")
    private HttpStatusReference apiReferences;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("error")
    private ErrorResponse error;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("responseOCSVO")
    private ResponseOcsXmlVO responseOCSVO;
    
	private String referenceAddress = "https://api.claro.com.br/docs/error_codes.html";
    
	public ApiResponse() {}
	
	public ApiResponse(ErrorType errorType) {
		this.requestId = Utils.generateId();
        this.code = errorType.getCode();
        this.messageDetail = errorType.getDesc();
        this.apiReferences = new HttpStatusReference("related", referenceAddress);
    }
	
	public ApiResponse(ErrorType errorType,
	                   int httpCode) {
	    
        this.requestId = Utils.generateId();
        this.code = errorType.getCode();
        this.messageDetail = errorType.getDesc();
        this.httpCode = httpCode;
        this.apiReferences = new HttpStatusReference("related", referenceAddress);
    }
	
	public ApiResponse(int code,
	                   String messageDetail,
	                   String transactionId,
                       int httpCode) {

	    this.requestId = Utils.generateId();
	    this.transactionID = transactionId;
	    this.code = code;
	    this.messageDetail = messageDetail;
	    this.httpCode = httpCode;
	    this.apiReferences = new HttpStatusReference("related", referenceAddress);
	}
	
	public ApiResponse(SuccessMessage successMessage) {
		this.requestId = Utils.generateId();
        this.code = successMessage.getCode();
        this.messageDetail = successMessage.getDesc();
        this.apiReferences = new HttpStatusReference("related", referenceAddress);
    }
	
	public ApiResponse(SuccessMessage successMessage,
                       String amount,
                       String transactionId, 
                       int httpCode) {
		
	    this.transactionID = transactionId;
		this.requestId = Utils.generateId();
		this.code = successMessage.getCode();
		this.messageDetail = successMessage.getDesc();
		this.amount = amount;
		this.httpCode = httpCode;
        this.apiReferences = new HttpStatusReference("related", referenceAddress);
	}

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ZonedDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(ZonedDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SuccessMessage getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(SuccessMessage successMessage) {
        this.successMessage = successMessage;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
	
	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public HttpStatusReference getApiReferences() {
		return apiReferences;
	}

	public void setApiReferences(HttpStatusReference apiReferences) {
		this.apiReferences = apiReferences;
	}
	
	public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
    
    public ResponseOcsXmlVO getResponseOCSVO() {
		return responseOCSVO;
	}

	public void setResponseOCSVO(ResponseOcsXmlVO responseOCSVO) {
		this.responseOCSVO = responseOCSVO;
	}
	
    @Override
	public String toString() {
        return JsonUtils.parseToJsonString(this); 
	}
}
