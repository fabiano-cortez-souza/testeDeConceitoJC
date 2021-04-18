package br.com.fcs.agendatecnico.vo;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodResponse   {
  
    @JsonProperty("methodResponse")
    private ParamContainer params = null;

    public MethodResponse params(ParamContainer params) {
        this.params = params;
        return this;
    }

    @Valid
    public ParamContainer getParams() {
        return params;
    }

    public void setParams(ParamContainer params) {
        this.params = params;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MethodResponse methodResponse = (MethodResponse) o;
        return Objects.equals(this.params, methodResponse.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MethodResponse {\n");
    
        sb.append("    params: ").append(toIndentedString(params)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    } 
}