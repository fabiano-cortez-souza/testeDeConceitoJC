package br.com.fcs.agendatecnico.vo;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamContainer   {
  @JsonProperty("param")
  private Param param = null;

  public ParamContainer param(Param param) {
    this.param = param;
    return this;
  }

  @Valid
  public Param getParam() {
    return param;
  }

  public void setParam(Param param) {
    this.param = param;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParamContainer paramContainer = (ParamContainer) o;
    return Objects.equals(this.param, paramContainer.param);
  }

  @Override
  public int hashCode() {
    return Objects.hash(param);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamContainer {\n");
    
    sb.append("    param: ").append(toIndentedString(param)).append("\n");
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