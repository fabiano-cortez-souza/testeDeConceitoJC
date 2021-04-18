
package br.com.fcs.agendacliente.business;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

import br.com.fcs.agendacliente.constants.Constants;
import br.com.fcs.agendacliente.dto.AgendaClienteSolicitationDTO;
import br.com.fcs.agendacliente.enums.ErrorType;
import br.com.fcs.agendacliente.enums.SuccessMessage;
import br.com.fcs.agendacliente.model.AgendaClienteModel;
import br.com.fcs.agendacliente.response.AgendaClienteApiResponse;
import br.com.fcs.agendacliente.service.AgendaClienteService;
import br.com.fcs.agendacliente.utils.DateUtils;
import br.com.fcs.agendacliente.utils.Utils;
import br.com.fcs.agendacliente.vo.AgendaClienteVO;

@Service
public class AgendaClienteBusiness {
	
	@Autowired
	AgendaClienteService agendaClienteService;
	
	private List<AgendaClienteModel> agendaClienteModels;
	
	private boolean jUnitTest = false;
	
	@Value("${apigee.credentials.bffkey}")
	private String apigeeCredentialsBffkey;
	
	private OneAgentSDK oneAgentSdk = OneAgentSDKFactory.createInstance();
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaClienteBusiness.class);
	
	public AgendaClienteApiResponse saveAgendaCliente(AgendaClienteModel transactionHistoryModel, String bffKey) {

	    if(StringUtils.isBlank(bffKey) ) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!bffKey.equals(apigeeCredentialsBffkey)) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
	    
        getDynatraceApigeeCredentialsBffkey();
        
	    AgendaClienteApiResponse api = fieldValidationSave(transactionHistoryModel);

        if (api == null) {
            if (agendaClienteService.save(transactionHistoryModel)){
            	api = new AgendaClienteApiResponse(SuccessMessage.TRANSACTION_SUCCESS, HttpStatus.OK.value());
            }else {
            	api = new AgendaClienteApiResponse(ErrorType.HTTP_RESPONSE_TRANSACTION_HISTORY_DENIED, HttpStatus.ACCEPTED.value());
            }
        }
        return api;
	}

	public AgendaClienteApiResponse fieldValidationSave(AgendaClienteModel agendaCliente) {
	    
	    if( StringUtils.isBlank(agendaCliente.getAgendaDataHora())          
		 || StringUtils.isBlank(agendaCliente.getCriacaoaAgendaDataHora()) 
	 	 || StringUtils.isBlank(agendaCliente.getDescricaoFalha()) 
		 || StringUtils.isBlank(agendaCliente.getModeloCelular()) 
		 || StringUtils.isBlank(agendaCliente.getIdAgenda().toString()) 
		 || StringUtils.isBlank(agendaCliente.getCliente().getCpf()) 
		 || StringUtils.isBlank(agendaCliente.getCliente().getEndereco()) 
		 || StringUtils.isBlank(agendaCliente.getCliente().getNome())) {
			
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND, HttpStatus.ACCEPTED.value());
		}
		
		if(Utils.invalidTimestampFormat(agendaCliente.getAgendaDataHora())) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TIMESTAMP_INVALID, HttpStatus.ACCEPTED.value());
		}
		/*
		if (!Utils.hasOnlyNumbers(agendaCliente.getMsisdn()) ||
			agendaCliente.getMsisdn().length() != Constants.MSISDN_LENGTH) {

		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		if(!Utils.hasOnlyNumbers(agendaCliente.getAmount()) || 
	       Utils.fieldOutOfRange(agendaCliente.getAmount())) {
	         
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_AMOUNT_INVALID, HttpStatus.ACCEPTED.value());
		}
	      
		if(agendaCliente.getType().length() > 20) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TYPE_INVALID, HttpStatus.ACCEPTED.value());
        }
	      
		if(agendaCliente.getTransactionID().length() > Constants.DEFAULT_TRANSACTION_ID_LENGTH) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
        }      
	    
		if (!Utils.hasOnlyNumbers(agendaCliente.getTransactionID())) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		if(StringUtils.isBlank(agendaCliente.getDescription())) {
            
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DESCRIPTION_INVALID, HttpStatus.ACCEPTED.value());
        }
		
		if(agendaCliente.getDescription().length() > 50) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DESCRIPTION_INVALID, HttpStatus.ACCEPTED.value());
		}
		/**/
		agendaCliente.setIdAgenda(agendaCliente.getIdAgenda());
		
		String eventExist = agendaClienteService.eventExists(agendaCliente.getIdAgenda()); 
		
		if(eventExist.equals("S")) {
            
		    return new AgendaClienteApiResponse(ErrorType.DOCUMENT_ALREADY_EXISTS, HttpStatus.ACCEPTED.value());
        }
		
		if(eventExist.equals("ERRO")) {
            
		    return new AgendaClienteApiResponse(ErrorType.HTTP_RESPONSE_TRANSACTION_HISTORY_DENIED, HttpStatus.ACCEPTED.value());
        }
		
		return null;
	}
	
	public AgendaClienteApiResponse findTransactionHistoryDocuments(AgendaClienteSolicitationDTO agendaClienteSolicitation, String bffKey) {
        
	    if(!jUnitTest) {
	        agendaClienteModels = new ArrayList<>();
	    }
	    
	    List<AgendaClienteModel> listaByMsisdn = new ArrayList<>();
	    
	    if(StringUtils.isBlank(bffKey) ) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!bffKey.equals(apigeeCredentialsBffkey)) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        getDynatraceApigeeCredentialsBffkey();
	    
        AgendaClienteApiResponse apiResponse = fieldValidationFind(agendaClienteSolicitation);
        List<AgendaClienteVO> listTransactionHistoryVO = new ArrayList<>();
        
        int numPage = 0;
        
        if(agendaClienteSolicitation.getNumPage() != null && !agendaClienteSolicitation.getNumPage().equals("")) {
            numPage = Integer.parseInt(agendaClienteSolicitation.getNumPage());
        }
        
        if(apiResponse == null) {
        	
            if(!jUnitTest) {
                listaByMsisdn = agendaClienteService.findTransactionHistoryByMsisdn(agendaClienteSolicitation.getMsisdn());
            }
            
            adicionaTransactionHistoryModels(agendaClienteSolicitation, listaByMsisdn);
            
            long totalOfDocuments = agendaClienteModels.size();
            int totalPages = Utils.calculateTotalOfPages(totalOfDocuments, Integer.parseInt(agendaClienteSolicitation.getNumRecord()));
            
            if(totalPages == 0) { 
                totalPages = 1;
            }
            
            inserirListaTransactionHistoryVO(listTransactionHistoryVO);
            
            apiResponse = new AgendaClienteApiResponse(SuccessMessage.TRANSACTION_SEARCH_SUCCESS,
                                                            agendaClienteSolicitation.getMsisdn(),
                                                            numPage,
                                                            totalPages,
                                                            totalOfDocuments,
                                                            HttpStatus.OK.value(),
                                                            listTransactionHistoryVO);
        }
        
        return apiResponse;
    }

    private void adicionaTransactionHistoryModels(AgendaClienteSolicitationDTO transactionHistorySolicitation,
                                                  List<AgendaClienteModel> listaByMsisdn) {
        
        for (AgendaClienteModel transactionHistoryModel : listaByMsisdn) {
            DateTime timestampDate = DateTime.parse(transactionHistoryModel.getAgendaDataHora());
            DateTime strDate = DateTime.parse(transactionHistorySolicitation.getStartDate()+ "T" + LocalTime.of(0, 0, 0));
            DateTime endDate = DateTime.parse(transactionHistorySolicitation.getEndDate()+ "T" + LocalTime.of(23, 59, 59));
            
            if (agendaClienteModels.size() <= Integer.parseInt(transactionHistorySolicitation.getNumRecord()) && 
                ((timestampDate.isAfter(strDate) && timestampDate.isBefore(endDate)) ||
                  timestampDate.isEqual(strDate) || timestampDate.isEqual(endDate))) {
                
                agendaClienteModels.add(transactionHistoryModel);
            }
        }
    }

    private void inserirListaTransactionHistoryVO(List<AgendaClienteVO> listAgendaClienteVO) {
        if(!agendaClienteModels.isEmpty()) {
            for (AgendaClienteModel agendaClienteModel : agendaClienteModels) {
                
                AgendaClienteVO agendaClienteVO = new AgendaClienteVO();
                agendaClienteVO.setCpf(agendaClienteModel.getCliente().getCpf());
                agendaClienteVO.setEndereco(agendaClienteModel.getCliente().getEndereco());
                agendaClienteVO.setIdCliente(agendaClienteModel.getCliente().getIdCliente());
                agendaClienteVO.setNome(agendaClienteModel.getCliente().getNome());
                listAgendaClienteVO.add(agendaClienteVO);
            }
        }
    }
    
    public AgendaClienteApiResponse fieldValidationFind(AgendaClienteSolicitationDTO agendaClienteSolicitation) {
        
        if(StringUtils.isBlank(agendaClienteSolicitation.getStartDate()) || 
           StringUtils.isBlank(agendaClienteSolicitation.getMsisdn()) ||
           StringUtils.isBlank(agendaClienteSolicitation.getEndDate()) ||
           StringUtils.isBlank(agendaClienteSolicitation.getNumRecord())) {
            
            return new AgendaClienteApiResponse (ErrorType.FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND, HttpStatus.ACCEPTED.value());
        }
        
        if(invalidDateFormat(agendaClienteSolicitation.getStartDate()) ||
           invalidDateFormat(agendaClienteSolicitation.getEndDate())) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DATE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (!Utils.hasOnlyNumbers(agendaClienteSolicitation.getMsisdn()) ||
            agendaClienteSolicitation.getMsisdn().length() != Constants.MSISDN_LENGTH) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
        }
                
        if(!Utils.hasOnlyNumbers(agendaClienteSolicitation.getNumRecord()) ||
           Integer.parseInt(agendaClienteSolicitation.getNumRecord()) < 1 || 
           Integer.parseInt(agendaClienteSolicitation.getNumRecord()) > 100) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_NUMRECORDS_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!StringUtils.isBlank(agendaClienteSolicitation.getNumPage()) && 
           !Utils.hasOnlyNumbers(agendaClienteSolicitation.getNumPage())) {
           
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_NUMPAGE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        return null;
    }

    private boolean invalidDateFormat(String startDate) {
        try {
            DateUtils.dateValidation(startDate);
            return false;
        } catch (DateTimeParseException dateTimeParseException) {
            return true;
        }
    }

    private void getDynatraceApigeeCredentialsBffkey() {
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-apigeeCredentialsBffkey", apigeeCredentialsBffkey);
    }
    
	public void setTransactionHistoryService(AgendaClienteService agendaClienteService) {
		this.agendaClienteService = agendaClienteService;
	}

    public void setTransactionHistoryModels(List<AgendaClienteModel> transactionHistoryModels) {
        this.agendaClienteModels = transactionHistoryModels;
    }

    public void setjUnitTest(boolean jUnitTest) {
        this.jUnitTest = jUnitTest;
    }

    public String getApigeeCredentialsBffkey() {
        return apigeeCredentialsBffkey;
    }

    public void setApigeeCredentialsBffkey(String apigeeCredentialsBffkey) {
        this.apigeeCredentialsBffkey = apigeeCredentialsBffkey;
    }
    
}
