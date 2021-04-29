package br.com.fcs.agendacliente.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import br.com.fcs.agendacliente.constants.Constants;
import br.com.fcs.agendacliente.utils.JsonUtils;
import br.com.fcs.agendacliente.utils.Utils;
import br.com.fcs.agendacliente.vo.OCSMember;
import br.com.fcs.agendacliente.vo.AgendaClienteVO;

class JsonUtilsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilsTest.class);
    
	@Test
	void validMapShouldReturnValidJsonString() {
		Map<String, String> validMap = new HashMap<String, String>();
		validMap.put("field1", "value");
		validMap.put("field2", "value");
		
		String json = JsonUtils.mapToJson(validMap);

		assertEquals("{\"field1\":\"value\",\"field2\":\"value\"}", json);
	}
	
	@Test
	void validJsonStringShouldReturnValidJavaObject() {
		
		String timestamp = ZonedDateTime.now().toString();
	    
		Integer idCliente = 1;
        String nome = "teste"; 
        String endereco = "Rua teste"; 
        String cpf = "111.111.111-11";
		
		String validJsonString = new AgendaClienteVO(idCliente,
		                                             nome,
		                                             endereco,
		                                             cpf).toString();
		
		String jsonString = String.valueOf(JsonUtils.parseJsonStringToJavaObject(validJsonString, AgendaClienteVO.class));
		assertEquals(validJsonString, jsonString);
	}
	
	@Test
	void shouldReturnJsonWithRootname() {
		Map<String, String> children = new HashMap<String, String>();
		children.put("field1", "value");
		children.put("field2", "value");
		children.put("field3", "20201001T17:20:50-0300");
		
		Map<String, Object> fatherWithChildren = new HashMap<String, Object>();
		fatherWithChildren.put("father", children);
		String json = JsonUtils.mapToJson(fatherWithChildren);
		LOGGER.info("teste = {}", json);
		assertEquals("{\"father\":{\"field1\":\"value\",\"field3\":\"20201001T17:20:50-0300\",\"field2\":\"value\"}}", json);

	}
	
	@Test
	void shouldReturnXMLString() {
		Map<String, String> children = new HashMap<String, String>();
		children.put("field1", "value");
		children.put("field2", "value");
		
		Map<String, Object> fatherWithChildren = new HashMap<String, Object>();
		fatherWithChildren.put("father", children);
		String json = JsonUtils.mapToJson(fatherWithChildren);
		String xml = JsonUtils.jsonToXML(json);
		LOGGER.info("XML: {}", xml);
		assertEquals("<father><field1>value</field1><field2>value</field2></father>", xml);
	}
	
	@Test
	void shouldPassParseToJsonString() {
	    Object object = new Object();
	    
	    String retorno = JsonUtils.parseToJsonString(object);
	    assertEquals("{}", retorno);
	}
	
	@Test
    void shouldPassGetGsonNotNull() {
        Gson retorno = JsonUtils.getGson();
        assertNotNull(retorno);
    }
    
    @Test
    void shouldPassAddRootName() {
        Object retorno = JsonUtils.addRootName("teste", "teste");
        assertEquals("{\"teste\":\"teste\"}", retorno.toString());
    }
    
    @Test
    void shouldPassGetFieldsWithTypeValueString() {
        String[] teste = {"teste", "teste1"};
        Map<String, String[]> hashMap = new HashMap<>();
        hashMap.put("1", teste);
        hashMap.put("2", teste);
        
        Map<String, Object> retorno = JsonUtils.getFieldsWithTypeValueString(hashMap);
        assertEquals("{1={\"teste\":\"teste1\"}, 2={\"teste\":\"teste1\"}}",  retorno.toString());
    }
    
    
    @Test
    void shouldPassGetFieldsWithTypeValueStringWithOneField() {
        String[] teste = {"teste", "teste1"};
        HashMap<String, String[]> hashMap = new HashMap<>();
        hashMap.put("1", teste);
        
        Map<String, Object> retorno = JsonUtils.getFieldsWithTypeValueString(hashMap);
        assertEquals("{1={\"teste\":\"teste1\"}}", retorno.toString());
    }
    
    @Test
    void shouldPassCreateMembersFromHashMap() {
        Object object = new Object();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("1", object);
        
        ListIterator<OCSMember> iterator = JsonUtils.createMembersFromHashMap(hashMap).listIterator();
        
        while (iterator.hasNext()) { 
            assertEquals(object, iterator.next().getValue());
        }
    }
}