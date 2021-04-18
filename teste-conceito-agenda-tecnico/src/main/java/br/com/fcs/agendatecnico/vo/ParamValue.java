package br.com.fcs.agendatecnico.vo;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParamValue   {
  @JsonProperty("struct")
  private Struct struct = null;

  public ParamValue struct(Struct struct) {
    this.struct = struct;
    return this;
  }

  @Valid
  public Struct getStruct() {
    return struct;
  }

  public void setStruct(Struct struct) {
    this.struct = struct;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParamValue paramValue = (ParamValue) o;
    return Objects.equals(this.struct, paramValue.struct);
  }

  @Override
  public int hashCode() {
    return Objects.hash(struct);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParamValue {\n");
    
    sb.append("    struct: ").append(toIndentedString(struct)).append("\n");
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