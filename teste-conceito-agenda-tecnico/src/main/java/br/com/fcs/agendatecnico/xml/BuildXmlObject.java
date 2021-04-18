package br.com.fcs.agendatecnico.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;

import com.google.gson.JsonParser;

import br.com.fcs.agendatecnico.enums.OCSDataType;
import br.com.fcs.agendatecnico.enums.XmlTagNameEnum;
import br.com.fcs.agendatecnico.utils.JsonUtils;
import br.com.fcs.agendatecnico.vo.AbstractMethodCall;
import br.com.fcs.agendatecnico.vo.Member;
import br.com.fcs.agendatecnico.vo.OCSMember;
import br.com.fcs.agendatecnico.vo.Param;
import br.com.fcs.agendatecnico.vo.ParamContainer;
import br.com.fcs.agendatecnico.vo.ParamValue;
import br.com.fcs.agendatecnico.vo.Struct;

public class BuildXmlObject {

    private BuildXmlObject() {
        //BuildXmlObject
    }
    
    public static String buildXMLObject(Map<String, String[]> updateBalanceAndDateFields, Map<String, String[]> dedicateAccountFields) {
        
        List<Member> listaMembers = getXMLmebers(updateBalanceAndDateFields, dedicateAccountFields);

        Struct struct = new Struct();
        ParamValue paramValue = new ParamValue();
        Param param = new Param();
        ParamContainer paramContainer = new ParamContainer();
        List<ParamContainer> listaParams = new ArrayList<>();
        AbstractMethodCall abstractMethodCall = new AbstractMethodCall();
        
        struct.setMember(listaMembers);
        paramValue.setStruct(struct);
        param.setValue(paramValue);
        paramContainer.setParam(param);
        listaParams.add(paramContainer);
        abstractMethodCall.setParams(listaParams);       
        abstractMethodCall.setMethodName(XmlTagNameEnum.UPDATE_BALANCE_AND_DATE.getDesc());
        
        String jsonString = abstractMethodCall.toString();
        
        Object jsonStringWithRootName = JsonUtils.addRootName("methodCall", jsonString);
        
        JSONObject json = new JSONObject(jsonStringWithRootName.toString());
        return XML.toString(json);
    }
    
    private static List<Member> getXMLmebers(Map<String, String[]> updateBalanceAndDateFields, Map<String, String[]> dedicateAccountFields) {

        List<Member> listaMembers;
        Map<String, Object> fieldTypeValue = new HashMap<>();
        
        if(updateBalanceAndDateFields != null && !updateBalanceAndDateFields.isEmpty()) {
            
            for (Map.Entry<String,String[]> entry : updateBalanceAndDateFields.entrySet()) {
                
                String key = entry.getKey();
                String[] value = entry.getValue();
                
                String fieldType = value[0];
                String fieldValue = value[1];

                Map<String, String> children = new HashMap<>();
                children.put(fieldType, fieldValue);
                
                @SuppressWarnings("deprecation")
                Object json = new JsonParser().parse(JsonUtils.mapToJson(children));
                fieldTypeValue.put(key, json);
                
            }
            
        }

        listaMembers = covertToCustomerBalanceMembers(JsonUtils.createMembersFromHashMap(fieldTypeValue));
            
        Member memberDedicateAccount = getDedicatedAccount(dedicateAccountFields);
        listaMembers.add(memberDedicateAccount);
        
        Member memberTransactionCurrency = getTransactionCurrency();
        listaMembers.add(memberTransactionCurrency);
        
        Member memberNegotiationMember = getNegotiationCapabilites();
        listaMembers.add(memberNegotiationMember);
        
        return listaMembers;
    }
    
	@SuppressWarnings("deprecation")
    private static Member getTransactionCurrency() {

	    Object keyValues = new Object();
	    
	    List<String[]> listaValues = new ArrayList<>();
        listaValues.add(new String[] {OCSDataType.STRING.getDesc(), "BRL"});
        
        for(String[] value : listaValues) {
            HashMap<String, String> map = new HashMap<>();
            map.put(value[0], value[1]);
            keyValues = new JsonParser().parse(JsonUtils.mapToJson(map));
        }

        Member member = new Member();
        member.setName(XmlTagNameEnum.TRANSACTION_CURRENCY.getDesc());
        member.setValue(keyValues);
        return member;
        
    }
	
	@SuppressWarnings("deprecation")
    private static Member getNegotiationCapabilites() {

        List<String[]> listaValues = new ArrayList<>();
        listaValues.add(new String[] {OCSDataType.I4.getDesc(), "805646916"});
        listaValues.add(new String[] {OCSDataType.I4.getDesc(), "1576"});
        Object[] keyValues = new Object[listaValues.size()];
        int i = 0;
        
        for(String[] value : listaValues) {
            HashMap<String, String> map = new HashMap<>();
            map.put(value[0], value[1]);
            keyValues[i] = new JsonParser().parse(JsonUtils.mapToJson(map));
            i++;
        }
        
        HashMap<String, Object[]> value = new HashMap<>();
        value.put("value", keyValues);
        
        HashMap<String, Object> data = new HashMap<>();
        data.put("data", new JsonParser().parse(JsonUtils.mapToJson(value)));
        
        HashMap<String, Object> negotiationCapabilitesArrayBlock = new HashMap<>();
        
        negotiationCapabilitesArrayBlock.put("array", data);

        Member member = new Member();
        member.setName(XmlTagNameEnum.NEGOTIATED_CAPABILITIES.getDesc());
        member.setValue(negotiationCapabilitesArrayBlock);
        return member;
    }
    
    private static List<Member> covertToCustomerBalanceMembers(List<OCSMember> createMembersFromHashMap) {
        List<Member> customerBalanceMembers = new ArrayList<>();
        
        createMembersFromHashMap.forEach(item -> {
            Member member = new Member();
            member.setName(item.getName());
            member.setValue(item.getValue());
            customerBalanceMembers.add(member);
        });
        
        return customerBalanceMembers;
    }
    
    private static Member getDedicatedAccount(Map<String, String[]> dedicateAccountFields) {

        Struct struct = new Struct();
        ParamValue paramValue = new ParamValue();
        Param param = new Param();
        
        Map<String, Object> fieldsWithTypeValueString = JsonUtils.getFieldsWithTypeValueString(dedicateAccountFields);
        List<OCSMember> ocsMembers = JsonUtils.createMembersFromHashMap(fieldsWithTypeValueString);
        List<Member> listsMembers = covertToCustomerBalanceMembers(ocsMembers);
        
        struct.setMember(listsMembers);
        paramValue.setStruct(struct);
        param.setValue(paramValue);
        
        HashMap<String, Object> arrayBlock = new HashMap<>();
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", param);
        
        @SuppressWarnings("deprecation")
        Object dataBlock = new JsonParser().parse(JsonUtils.mapToJson(map));
        
        arrayBlock.put("array", dataBlock);
        
        Member member = new Member();
        member.setName("dedicatedAccountUpdateInformation");
        member.setValue(arrayBlock);
        return member;
    }
    
}