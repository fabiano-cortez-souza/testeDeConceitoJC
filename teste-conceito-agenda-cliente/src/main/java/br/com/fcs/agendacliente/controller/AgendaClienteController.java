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
	public ResponseEntity<AgendaClienteApiResponse> transactionHistorySave(@RequestBody AgendaClienteModel agendaClienteModel, 
	                                                                            HttpServletRequest request, 
	                                                                            HttpServletResponse response) {

	    LOGGER.info("Entrando no AgendaClienteModelSave = {} ", agendaClienteModel.getIdAgenda().toString());
	    
	    agendaClienteModel.setCriacaoaAgendaDataHora(DateTime.now().toString());
	    
	    String bffKey = getBffKey(request);
	    
	    oneAgentSdk.addCustomRequestAttribute("agenda-cliente-cliente-cpf", agendaClienteModel.getCliente().getCpf());
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-cliente-nome", agendaClienteModel.getCliente().getNome());
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-description-fail", agendaClienteModel.getDescricaoFalha());
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-agendamento", agendaClienteModel.getAgendaDataHora());
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-modelo-celular", agendaClienteModel.getModeloCelular());
        setDynatraceCPF(agendaClienteModel.getCliente().getCpf());
        setDynatraceBffKey(bffKey);
	    
	    AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveAgendaCliente(agendaClienteModel, bffKey);    
        
	    LOGGER.info("apiResponse transactionHistorySave = {} ", apiResponse.getMessageDetail());
        
	    setDynatraceStatus(apiResponse);
	    
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
      
	    oneAgentSdk.addCustomRequestAttribute("agenda-cliente-startDate", startDate);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-endDate", endDate);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-numPage", numPage);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-numRecord", numRecord);
        setDynatraceCPF(msisdn);
        setDynatraceBffKey(bffKey);	    

        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        
        LOGGER.info("apiResponse transactionHistoryFind = {} ", apiResponse.getMessageDetail());
        
        setDynatraceStatus(apiResponse);
        
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
        
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-startDate", startDate);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-endDate", endDate);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-numPage", numPage);
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-numRecord", numRecord);
        setDynatraceCPF(msisdn);
        setDynatraceBffKey(bffKey);
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitation, bffKey);
        
        LOGGER.info("apiResponse = {} ", apiResponse.getMessageDetail());
        
        setDynatraceStatus(apiResponse);
        
        if (apiResponse.getCode() != 0) {
            return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
        }
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

	private void setDynatraceBffKey(String bffKey) {
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-Key", bffKey);
    }

	private void setDynatraceCPF(String  cpf) {
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-cpf", cpf);
    }
	
	private void setDynatraceStatus(AgendaClienteApiResponse apiResponse) {
        oneAgentSdk.addCustomRequestAttribute("agenda-cliente-status", apiResponse.getMessageDetail());
    }
	
	private String getBffKey(HttpServletRequest request) {
        return request.getHeader("x-application-key");
    }
	
    public void setTransactionHistoryBusiness(AgendaClienteBusiness transactionHistoryBusiness) {
        this.transactionHistoryBusiness = transactionHistoryBusiness;
    }
	
}
