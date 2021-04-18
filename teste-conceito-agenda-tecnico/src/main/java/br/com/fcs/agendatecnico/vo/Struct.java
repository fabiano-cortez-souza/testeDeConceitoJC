package br.com.fcs.agendatecnico.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Struct   {
    
    @JsonProperty("member")
    private List<Member> member = null;
    
    public Struct members(List<Member> member) {
        this.member = member;
        return this;
    }

    public Struct addMembersItem(Member membersItem) {
        if (this.member == null) {
            this.member = new ArrayList<Member>();
        }
        this.member.add(membersItem);
        return this;
    }
   
    public List<Member> getMember() {
        return member;
    }

    public void setMember(List<Member> member) {
        this.member = member;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Struct struct = (Struct) o;
        return Objects.equals(this.member, struct.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Struct {\n");
    
        sb.append("    member: ").append(toIndentedString(member)).append("\n");
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