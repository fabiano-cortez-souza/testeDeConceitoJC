package br.com.fcs.agendatecnico.xml;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.fcs.agendatecnico.utils.JsonUtils;
import br.com.fcs.agendatecnico.vo.Member;
import br.com.fcs.agendatecnico.vo.ResponseOcsXmlVO;

@Component
public class CapturaCamposXMLResponseOCS {

    private static final Logger LOGGER = LoggerFactory.getLogger(CapturaCamposXMLResponseOCS.class);
    
	private Long valorCode = Long.MAX_VALUE; 
    private Long valorDedicatedAccountID = Long.valueOf(0);
    private Long valorDedicatedAccountActiveValue1 = Long.valueOf(0);
    
    private static final String VALUE = "value";
    
	public void capturaCamposXML(String stringResponseOcsXML) {
	    
	    ResponseOcsXmlVO responseOcsXmlVO = null;

		try {
			JSONObject parseJson = XML.toJSONObject(stringResponseOcsXML);
			LOGGER.info("[CAPTURACAMPOSXML] parse XML para JSON {}",parseJson);
			responseOcsXmlVO = JsonUtils.getGson().fromJson(parseJson.toString(), ResponseOcsXmlVO.class);
		} catch (Exception e) {
			LOGGER.error("[CAPTURACAMPOSXML] Erro no Parser GSON: {}", e.getMessage());
		}

		if (responseOcsXmlVO != null && 
			responseOcsXmlVO.getMethodResponse() != null &&
			responseOcsXmlVO.getMethodResponse().getParams() != null &&
			responseOcsXmlVO.getMethodResponse().getParams().getParam() != null &&
			responseOcsXmlVO.getMethodResponse().getParams().getParam().getValue() != null &&
			responseOcsXmlVO.getMethodResponse().getParams().getParam().getValue().getStruct() != null &&
			responseOcsXmlVO.getMethodResponse().getParams().getParam().getValue().getStruct().getMember() != null) {

			for (Member member : responseOcsXmlVO.getMethodResponse().getParams().getParam().getValue().getStruct().getMember()) {

				if (member != null && 
					member.getName() != null && 
					member.getValue() != null &&
				   !member.getValue().equals("") &&
				   (member.getName().contains("dedicatedAccountChangeInformation")	|| member.getName().contains("responseCode"))) {

					getResponseCode(member);
					getDedicatedAccountChangeInformation(member);
				}
			}
		}
	}

	private void getResponseCode(Member member) {

		Map<?, ?> mapValor = (Map) member.getValue();
		if (mapValor != null && mapValor.containsKey("i4")) {
			Double valor = (Double) mapValor.get("i4");
			valorCode = valor.longValue();
		}
	}

	private void getDedicatedAccountChangeInformation(Member member) {

		Object objData = getObject(member.getValue(), "array");

		Object objValue = getObject(objData, "data");

		Object objStruct = getObject(objValue, VALUE);

		Object objMember = getObject(objStruct, "struct");

		if (objMember != null) {

			Map<?, ?> mapMember = (Map) objMember;

			if(mapMember.containsKey("member")) {
		        ArrayList<?> listaMember = (ArrayList) mapMember.get("member");

                getListaMember(listaMember);    
		    }
		}
	}

    private void getListaMember(ArrayList<?> listaMember) {
        for (Object objectMember : listaMember) {

        	Map<?, ?> mapa = (Map) objectMember;

        	if (mapa != null && mapa.containsKey("name")) {

        		getDedicatedAccountID(mapa);

        		getDedicatedAccountActiveValue1(mapa);
        	}
        }
    }

    private void getDedicatedAccountID(Map<?, ?> mapa) {
        if (mapa.get("name").equals("dedicatedAccountID")) {

        	Map<?, ?> mapaValor = (Map) mapa.get(VALUE);
        	if (mapaValor != null && mapaValor.containsKey("i4")) {
        		Double valor = (Double) mapaValor.get("i4");
        		valorDedicatedAccountID = valor.longValue();
        	}
        }
    }

    private void getDedicatedAccountActiveValue1(Map<?, ?> mapa) {
        if (mapa.get("name").equals("dedicatedAccountActiveValue1")) {

        	Map<?, ?> mapaValor = (Map) mapa.get(VALUE);
        	if (mapaValor != null && mapaValor.containsKey("string")) {
        		Double valor = (Double) mapaValor.get("string");
        		valorDedicatedAccountActiveValue1 = valor.longValue();
        	}
        }
    }

	public Object getObject(Object obj, String campo) {

		Object object = null;
		if (obj != null && !obj.equals("")) {
			Map<?, ?> mapa = (Map) obj;
			if (mapa.containsKey(campo)) {
				object = mapa.get(campo);
			}
		}
		return object;
	}
	
	public Long getValorCode() {
		return valorCode;
	}

	public Long getValorDedicatedAccountID() {
		return valorDedicatedAccountID;
	}

	public Long getValorDedicatedAccountActiveValue1() {
		return valorDedicatedAccountActiveValue1;
	}
}
