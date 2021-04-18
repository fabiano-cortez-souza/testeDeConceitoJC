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
	AgendaClienteService transactionHistoryService;
	
	private List<AgendaClienteModel> transactionHistoryModels;
	
	private boolean jUnitTest = false;
	
	@Value("${apigee.credentials.bffkey}")
	private String apigeeCredentialsBffkey;
	
	private OneAgentSDK oneAgentSdk = OneAgentSDKFactory.createInstance();
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaClienteBusiness.class);
	
	public AgendaClienteApiResponse saveTransaction(AgendaClienteModel transactionHistoryModel, String bffKey) {

	    if(StringUtils.isBlank(bffKey) ) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!bffKey.equals(apigeeCredentialsBffkey)) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
	    
        getDynatraceApigeeCredentialsBffkey();
        
	    AgendaClienteApiResponse api = fieldValidationSave(transactionHistoryModel);

        if (api == null) {
            if (transactionHistoryService.save(transactionHistoryModel)){
            	api = new AgendaClienteApiResponse(SuccessMessage.TRANSACTION_SUCCESS, HttpStatus.OK.value());
            }else {
            	api = new AgendaClienteApiResponse(ErrorType.HTTP_RESPONSE_TRANSACTION_HISTORY_DENIED, HttpStatus.ACCEPTED.value());
            }
        }
        return api;
	}

	public AgendaClienteApiResponse fieldValidationSave(AgendaClienteModel transactionHistory) {
	    
	    if( StringUtils.isBlank(transactionHistory.getTimestamp()) || 
		    StringUtils.isBlank(transactionHistory.getMsisdn()) ||
			StringUtils.isBlank(transactionHistory.getAmount()) ||
			StringUtils.isBlank(transactionHistory.getType()) ||
			StringUtils.isBlank(transactionHistory.getTransactionID())) {
			
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND, HttpStatus.ACCEPTED.value());
		}
		
		if(Utils.invalidTimestampFormat(transactionHistory.getTimestamp())) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TIMESTAMP_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		if (!Utils.hasOnlyNumbers(transactionHistory.getMsisdn()) ||
			transactionHistory.getMsisdn().length() != Constants.MSISDN_LENGTH) {

		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		if(!Utils.hasOnlyNumbers(transactionHistory.getAmount()) || 
	       Utils.fieldOutOfRange(transactionHistory.getAmount())) {
	         
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_AMOUNT_INVALID, HttpStatus.ACCEPTED.value());
		}
	      
		if(transactionHistory.getType().length() > 20) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TYPE_INVALID, HttpStatus.ACCEPTED.value());
        }
	      
		if(transactionHistory.getTransactionID().length() > Constants.DEFAULT_TRANSACTION_ID_LENGTH) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
        }      
	    
		if (!Utils.hasOnlyNumbers(transactionHistory.getTransactionID())) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		if(StringUtils.isBlank(transactionHistory.getDescription())) {
            
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DESCRIPTION_INVALID, HttpStatus.ACCEPTED.value());
        }
		
		if(transactionHistory.getDescription().length() > 50) {
		    
		    return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DESCRIPTION_INVALID, HttpStatus.ACCEPTED.value());
		}
		
		transactionHistory.setId(transactionHistory.getTransactionID());
		
		String eventExist = transactionHistoryService.eventExists(transactionHistory.getTransactionID()); 
		
		if(eventExist.equals("S")) {
            
		    return new AgendaClienteApiResponse(ErrorType.DOCUMENT_ALREADY_EXISTS, HttpStatus.ACCEPTED.value());
        }
		
		if(eventExist.equals("ERRO")) {
            
		    return new AgendaClienteApiResponse(ErrorType.HTTP_RESPONSE_TRANSACTION_HISTORY_DENIED, HttpStatus.ACCEPTED.value());
        }
		
		return null;
	}
	
	public AgendaClienteApiResponse findTransactionHistoryDocuments(AgendaClienteSolicitationDTO transactionHistorySolicitation, String bffKey) {
        
	    if(!jUnitTest) {
	        transactionHistoryModels = new ArrayList<>();
	    }
	    
	    List<AgendaClienteModel> listaByMsisdn = new ArrayList<>();
	    
	    if(StringUtils.isBlank(bffKey) ) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!bffKey.equals(apigeeCredentialsBffkey)) {
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        getDynatraceApigeeCredentialsBffkey();
	    
        AgendaClienteApiResponse apiResponse = fieldValidationFind(transactionHistorySolicitation);
        List<AgendaClienteVO> listTransactionHistoryVO = new ArrayList<>();
        
        int numPage = 0;
        
        if(transactionHistorySolicitation.getNumPage() != null && !transactionHistorySolicitation.getNumPage().equals("")) {
            numPage = Integer.parseInt(transactionHistorySolicitation.getNumPage());
        }
        
        if(apiResponse == null) {
        	
            if(!jUnitTest) {
                listaByMsisdn = transactionHistoryService.findTransactionHistoryByMsisdn(transactionHistorySolicitation.getMsisdn());
            }
            
            adicionaTransactionHistoryModels(transactionHistorySolicitation, listaByMsisdn);
            
            long totalOfDocuments = transactionHistoryModels.size();
            int totalPages = Utils.calculateTotalOfPages(totalOfDocuments, Integer.parseInt(transactionHistorySolicitation.getNumRecord()));
            
            if(totalPages == 0) { 
                totalPages = 1;
            }
            
            inserirListaTransactionHistoryVO(listTransactionHistoryVO);
            
            apiResponse = new AgendaClienteApiResponse(SuccessMessage.TRANSACTION_SEARCH_SUCCESS,
                                                            transactionHistorySolicitation.getMsisdn(),
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
            DateTime timestampDate = DateTime.parse(transactionHistoryModel.getTimestamp());
            DateTime strDate = DateTime.parse(transactionHistorySolicitation.getStartDate()+ "T" + LocalTime.of(0, 0, 0));
            DateTime endDate = DateTime.parse(transactionHistorySolicitation.getEndDate()+ "T" + LocalTime.of(23, 59, 59));
            
            if (transactionHistoryModels.size() <= Integer.parseInt(transactionHistorySolicitation.getNumRecord()) && 
                ((timestampDate.isAfter(strDate) && timestampDate.isBefore(endDate)) ||
                  timestampDate.isEqual(strDate) || timestampDate.isEqual(endDate))) {
                
                transactionHistoryModels.add(transactionHistoryModel);
            }
        }
    }

    private void inserirListaTransactionHistoryVO(List<AgendaClienteVO> listTransactionHistoryVO) {
        if(!transactionHistoryModels.isEmpty()) {
            for (AgendaClienteModel transactionHistoryModel : transactionHistoryModels) {
                
                AgendaClienteVO transactionHistoryVO = new AgendaClienteVO();
                transactionHistoryVO.setTimestamp(transactionHistoryModel.getTimestamp());
                transactionHistoryVO.setType(transactionHistoryModel.getType());
                transactionHistoryVO.setAmount(transactionHistoryModel.getAmount());
                transactionHistoryVO.setDescription(transactionHistoryModel.getDescription());
                transactionHistoryVO.setTransactionID(transactionHistoryModel.getTransactionID());
                listTransactionHistoryVO.add(transactionHistoryVO);
            }
        }
    }
    
    public AgendaClienteApiResponse fieldValidationFind(AgendaClienteSolicitationDTO transactionHistorySolicitation) {
        
        if(StringUtils.isBlank(transactionHistorySolicitation.getStartDate()) || 
           StringUtils.isBlank(transactionHistorySolicitation.getMsisdn()) ||
           StringUtils.isBlank(transactionHistorySolicitation.getEndDate()) ||
           StringUtils.isBlank(transactionHistorySolicitation.getNumRecord())) {
            
            return new AgendaClienteApiResponse (ErrorType.FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND, HttpStatus.ACCEPTED.value());
        }
        
        if(invalidDateFormat(transactionHistorySolicitation.getStartDate()) ||
           invalidDateFormat(transactionHistorySolicitation.getEndDate())) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_DATE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (!Utils.hasOnlyNumbers(transactionHistorySolicitation.getMsisdn()) ||
            transactionHistorySolicitation.getMsisdn().length() != Constants.MSISDN_LENGTH) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
        }
                
        if(!Utils.hasOnlyNumbers(transactionHistorySolicitation.getNumRecord()) ||
           Integer.parseInt(transactionHistorySolicitation.getNumRecord()) < 1 || 
           Integer.parseInt(transactionHistorySolicitation.getNumRecord()) > 100) {
            
            return new AgendaClienteApiResponse(ErrorType.FIELD_VALIDATION_NUMRECORDS_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!StringUtils.isBlank(transactionHistorySolicitation.getNumPage()) && 
           !Utils.hasOnlyNumbers(transactionHistorySolicitation.getNumPage())) {
           
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
        oneAgentSdk.addCustomRequestAttribute("transaction-history-apigeeCredentialsBffkey", apigeeCredentialsBffkey);
    }
    
	public void setTransactionHistoryService(AgendaClienteService transactionHistoryService) {
		this.transactionHistoryService = transactionHistoryService;
	}

    public void setTransactionHistoryModels(List<AgendaClienteModel> transactionHistoryModels) {
        this.transactionHistoryModels = transactionHistoryModels;
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
