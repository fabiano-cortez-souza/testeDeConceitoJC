package br.com.fcs.agendacliente.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.fcs.agendacliente.vo.OCSMember;

public final class JsonUtils {
	
    private static final Gson gson = new Gson();
	    
	private JsonUtils() {}

	public static String parseToJsonString(Object object) {
	    return gson.toJson(object);
	}

	public static Gson getGson() {
	    return gson;
	}
	  
	public static Object parseJsonStringToJavaObject(String json, Class<?> clazz) {
	    return gson.fromJson(json, clazz);
	}
	  
	public static String mapToJson(Map<String, ?> map) {
	    return gson.toJson(map);
	}
	  
	@SuppressWarnings("deprecation")
    public static Object addRootName(String rootName, String jsonString) {
	    JsonElement je = new JsonParser().parse(jsonString);
	    JsonObject jo = new JsonObject();
	    jo.add(rootName, je);
	    return new JsonParser().parse(jo.toString());
	}
	  
	public static String jsonToXML(String jsonString) {
	    JSONObject json = new JSONObject(jsonString);
		return XML.toString(json);
	}

	public static Map<String, Object> getFieldsWithTypeValueString(Map<String, String[]> typeField) {
		
	    Map<String, Object> fieldTypeValue = new HashMap<>();
		
		if(typeField != null && !typeField.isEmpty() && typeField.size() > 0) {
		    
		    for (Map.Entry<String,String[]> entry : typeField.entrySet()) {
                
		        String key = entry.getKey();
		        String[] value = entry.getValue();
		        
		        String fieldType = value[0];
                String fieldValue = value[1];
                
                Object fieldInJsonFormat = JsonUtils.addRootName(fieldType,fieldValue);
                fieldTypeValue.put(key, fieldInJsonFormat);
            }
		}
		return fieldTypeValue;
	}

	public static List<OCSMember> createMembersFromHashMap(Map<String, Object> fieldsWithTypeValueString) {
		List<OCSMember> ocsMembers = new ArrayList<>();
		
		if(fieldsWithTypeValueString != null && !fieldsWithTypeValueString.isEmpty() && fieldsWithTypeValueString.size() > 0) {
		    for (Map.Entry<String,Object> entry : fieldsWithTypeValueString.entrySet()) {
	            
	            String key = entry.getKey();
	            Object value = entry.getValue();
	            
	            OCSMember member = new OCSMember();
	            member.setName(key);
	            member.setValue(value);
	            ocsMembers.add(member);
	        }
		}
		
		return ocsMembers;
	}
}
