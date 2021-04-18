package br.com.fcs.agendatecnico.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fcs.agendatecnico.utils.JsonUtils;

public class ResponseOcsXmlVO {

    @JsonProperty("methodResponse")
    private MethodResponse methodResponse = null;
    
	public ResponseOcsXmlVO() {}

	public ResponseOcsXmlVO(MethodResponse methodResponse) {
		super();
		this.methodResponse = methodResponse;
	}

    public MethodResponse getMethodResponse() {
        return methodResponse;
    }

    public void setMethodResponse(MethodResponse methodResponse) {
        this.methodResponse = methodResponse;
    }

	public String toString() {
        return JsonUtils.parseToJsonString(this); 
	}   
}