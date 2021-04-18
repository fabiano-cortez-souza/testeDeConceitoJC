package br.com.fcs.agendatecnico.vo;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Param   {
  @JsonProperty("value")
  private ParamValue value = null;

  public Param value(ParamValue value) {
    this.value = value;
    return this;
  }
  
  @Valid
  public ParamValue getValue() {
    return value;
  }

  public void setValue(ParamValue value) {
    this.value = value;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Param param = (Param) o;
    return Objects.equals(this.value, param.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Param {\n");
    
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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