package br.com.fcs.agendatecnico.vo;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member   {
  
    @JsonProperty("name")
    private String name = null;

    @JsonProperty("value")
    private Object value = null;

    public Member name(String name) {
        this.name = name;
        return this;
    }
  
    public Member value(Object value) {
        this.value = value;
        return this;
    }
  
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
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
        
        Member member = (Member) o;
        
        return Objects.equals(this.name, member.name) && Objects.equals(this.value, member.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Member {\n");
    
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

