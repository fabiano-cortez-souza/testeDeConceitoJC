package br.com.fcs.agendatecnico.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MethodCall   {
  
    @JsonProperty("methodName")
    private String methodName = null;

    @JsonProperty("params")
    private List<ParamContainer> params = null;

    public MethodCall methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodCall params(List<ParamContainer> params) {
        this.params = params;
        return this;
    }

    public MethodCall addParamsItem(ParamContainer paramsItem) {
        if (this.params == null) {
            this.params = new ArrayList<ParamContainer>();
        }
        this.params.add(paramsItem);
        return this;
    }

    @Valid
    public List<ParamContainer> getParams() {
        return params;
    }

    public void setParams(List<ParamContainer> params) {
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
        
        MethodCall methodCall = (MethodCall) o;
        
        return Objects.equals(this.methodName, methodCall.methodName) &&
                Objects.equals(this.params, methodCall.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, params);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MethodCall {\n");
        sb.append("    methodName: ").append(toIndentedString(methodName)).append("\n");
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