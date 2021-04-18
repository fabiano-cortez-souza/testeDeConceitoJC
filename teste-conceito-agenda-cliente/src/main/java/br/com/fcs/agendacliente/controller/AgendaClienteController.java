package br.com.fcs.agendacliente.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

import br.com.fcs.agendacliente.business.AgendaClienteBusiness;
import br.com.fcs.agendacliente.dto.AgendaClienteSolicitationDTO;
import br.com.fcs.agendacliente.model.AgendaClienteModel;
import br.com.fcs.agendacliente.response.ApiResponse;
import br.com.fcs.agendacliente.response.AgendaClienteApiResponse;
import br.com.fcs.agendacliente.utils.Utils;

@RestController
public class AgendaClienteController{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AgendaClienteController.class);
    
	@Autowired
	AgendaClienteBusiness transactionHistoryBusiness;
	
	private OneAgentSDK oneAgentSdk = OneAgentSDKFactory.createInstance();
	
	@PostMapping(value = "/api/v1/transactionHistory/", produces = { "application/json;charset=utf-8" })
	public ResponseEntity<AgendaClienteApiResponse> transactionHistorySave(@RequestBody AgendaClienteModel transactionHistoryModel, 
	                                                                            HttpServletRequest request, 
	                                                                            HttpServletResponse response) {

	    LOGGER.info("Entrando no transactionHistorySave = {} ", transactionHistoryModel.getTransactionID());
	    
	    transactionHistoryModel.setId(transactionHistoryModel.getTransactionID());
	    transactionHistoryModel.setRequestId(Utils.generateId());
	    transactionHistoryModel.setEventTimeStamp(DateTime.now().toString());
	    
	    String bffKey = getBffKey(request);
	    
	    oneAgentSdk.addCustomRequestAttribute("transaction-history-transactionID", transactionHistoryModel.getTransactionID());
        oneAgentSdk.addCustomRequestAttribute("transaction-history-amount", transactionHistoryModel.getAmount());
        oneAgentSdk.addCustomRequestAttribute("transaction-history-description", transactionHistoryModel.getDescription());
        oneAgentSdk.addCustomRequestAttribute("transaction-history-timeStamp", transactionHistoryModel.getTimestamp());
        oneAgentSdk.addCustomRequestAttribute("transaction-history-type", transactionHistoryModel.getType());
        getDynatraceMsisdn(transactionHistoryModel.getMsisdn());
        getDynatraceBffKey(bffKey);
	    
	    AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);    
        
	    LOGGER.info("apiResponse transactionHistorySave = {} ", apiResponse.getMessageDetail());
        
	    getDynatraceStatus(apiResponse);
	    
	    if (apiResponse.getCode() != 0) {
	        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
	    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    
	
	@GetMapping(value = "/healthy", produces = { "application/json; charset=utf-8"})
    public ResponseEntity<ApiResponse> transactionHistoryHealthy(HttpServletRequest request, 
                                                                 HttpServletResponse response) {
        
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setHttpCode(HttpStatus.OK.value());
        apiResponse.setMessage("Healthy successfull");
        apiResponse.setCode(HttpStatus.OK.value());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        
    }
	
	@GetMapping(value = "/api/v1/transactionHistory/{msisdn}/{startDate}/{endDate}/{numRecord}", produces = { "application/json; charset=utf-8"})
    public ResponseEntity<AgendaClienteApiResponse> transactionHistoryFind(@PathVariable(name = "msisdn") String msisdn, 
                                                                                @PathVariable(name = "startDate") String startDate,
                                                                                @PathVariable(name = "endDate") String endDate,
                                                                                @PathVariable(name = "numRecord") String numRecord,
                                                                                @RequestParam(value = "numPage", required = false) String numPage,
                                                                                HttpServletRequest request, 
                                                                                HttpServletResponse response) {
        
	    LOGGER.info("Entrando no transactionHistoryFind = {} ", msisdn);
	    
	    AgendaClienteSolicitationDTO transactionHistorySolicitationDTO = new AgendaClienteSolicitationDTO();
	    transactionHistorySolicitationDTO.setMsisdn(msisdn);
	    transactionHistorySolicitationDTO.setStartDate(startDate);
	    transactionHistorySolicitationDTO.setEndDate(endDate);
	    transactionHistorySolicitationDTO.setNumPage(numPage);
	    transactionHistorySolicitationDTO.setNumRecord(numRecord);
	    
	    String bffKey = getBffKey(request);
      
	    oneAgentSdk.addCustomRequestAttribute("transaction-history-startDate", startDate);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-endDate", endDate);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-numPage", numPage);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-numRecord", numRecord);
        getDynatraceMsisdn(msisdn);
        getDynatraceBffKey(bffKey);	    

        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        
        LOGGER.info("apiResponse transactionHistoryFind = {} ", apiResponse.getMessageDetail());
        
        getDynatraceStatus(apiResponse);
        
        if (apiResponse.getCode() != 0) {
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
	
	@GetMapping(value = "/api/v1/transactionHistory/", produces = { "application/json; charset=utf-8"})
    public ResponseEntity<AgendaClienteApiResponse> transactionHistoryFindParametro(@RequestParam(value = "msisdn", required = true) String msisdn, 
                                                                                         @RequestParam(value = "startDate", required = true) String startDate, 
                                                                                         @RequestParam(value = "endDate", required = true) String endDate, 
                                                                                         @RequestParam(value = "numRecord", required = true) String numRecord,
                                                                                         @RequestParam(value = "numPage", required = false) String numPage,
                                                                                         HttpServletRequest request, 
                                                                                         HttpServletResponse response) {
	    
	    LOGGER.info("Entrando no transactionHistoryFindParametro = {} ", msisdn);

	    AgendaClienteSolicitationDTO transactionHistorySolicitation = new AgendaClienteSolicitationDTO();
        transactionHistorySolicitation.setMsisdn(msisdn);
        transactionHistorySolicitation.setStartDate(startDate);
        transactionHistorySolicitation.setEndDate(endDate);
        transactionHistorySolicitation.setNumPage(numPage);
        transactionHistorySolicitation.setNumRecord(numRecord);
        
        String bffKey = getBffKey(request);
        
        oneAgentSdk.addCustomRequestAttribute("transaction-history-startDate", startDate);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-endDate", endDate);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-numPage", numPage);
        oneAgentSdk.addCustomRequestAttribute("transaction-history-numRecord", numRecord);
        getDynatraceMsisdn(msisdn);
        getDynatraceBffKey(bffKey);
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitation, bffKey);
        
        LOGGER.info("apiResponse = {} ", apiResponse.getMessageDetail());
        
        getDynatraceStatus(apiResponse);
        
        if (apiResponse.getCode() != 0) {
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

	private void getDynatraceBffKey(String bffKey) {
        oneAgentSdk.addCustomRequestAttribute("transaction-history-Key", bffKey);
    }

	private void getDynatraceMsisdn(String  msisdn) {
        oneAgentSdk.addCustomRequestAttribute("transaction-history-msisdn", msisdn);
    }
	
	private void getDynatraceStatus(AgendaClienteApiResponse apiResponse) {
        oneAgentSdk.addCustomRequestAttribute("transaction-history-status", apiResponse.getMessageDetail());
    }
	
	private String getBffKey(HttpServletRequest request) {
        return request.getHeader("x-application-key");
    }
	
    public void setTransactionHistoryBusiness(AgendaClienteBusiness transactionHistoryBusiness) {
        this.transactionHistoryBusiness = transactionHistoryBusiness;
    }
	
}
