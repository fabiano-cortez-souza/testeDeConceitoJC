package br.com.fcs.agendatecnico.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

import br.com.fcs.agendatecnico.business.RequestAgendaTecnicoBusiness;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;

@RestController
public class RequestAgendaTecnicoController{

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAgendaTecnicoController.class);
		
	@Autowired
	RequestAgendaTecnicoBusiness requestCustomerBalanceBusiness;
	
	private OneAgentSDK oneAgentSdk = OneAgentSDKFactory.createInstance();
	
	@PostMapping(value = "/api/v1/requestCustomerBalance/", produces = { "application/json;charset=utf-8" })
	public ResponseEntity<ApiResponse> requestCustomerBalance(@RequestBody RequestAgendaTecnicoVO requestCustomerBalanceVO,
                                                              HttpServletRequest request, 
                                                              HttpServletResponse response) {
    	
	    String bffKey = request.getHeader("x-application-key");
	    getDynatraceBffKey(bffKey);
	    
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-msisdn", requestCustomerBalanceVO.getMsisdn());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-timestamp", requestCustomerBalanceVO.getTimestamp());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-transactionID", requestCustomerBalanceVO.getTransactionID());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-transactionCode", requestCustomerBalanceVO.getTransactionCode());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-transactionType", requestCustomerBalanceVO.getTransactionType());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-amount", requestCustomerBalanceVO.getAmount());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-originNodeType", requestCustomerBalanceVO.getOriginNodeType());
	    oneAgentSdk.addCustomRequestAttribute("customer-balance-originHostName", requestCustomerBalanceVO.getOriginHostName());
	    
    	ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
	    LOGGER.info("apiResponse = {} ", apiResponse.getMessageDetail());

	    getDynatraceStatus(apiResponse);
	    
	    if (apiResponse.getCode() != 0) {
	        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
	    }

    	return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
    
	@GetMapping(value = "/healthy", produces = { "application/json; charset=utf-8"})
    public ResponseEntity<ApiResponse> requestCustomerBalanceHealthy(HttpServletRequest request, 
                                                                     HttpServletResponse response) {
        
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setHttpCode(HttpStatus.OK.value());
        apiResponse.setMessage("Healthy successfull");
        apiResponse.setCode(HttpStatus.OK.value());
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        
    }
	
    public void setRequestCustomerBalanceBusiness(RequestAgendaTecnicoBusiness requestCustomerBalanceBusiness) {
        this.requestCustomerBalanceBusiness = requestCustomerBalanceBusiness;
    }
    
    private void getDynatraceBffKey(String bffKey) {
        oneAgentSdk.addCustomRequestAttribute("customer-balance-Key", bffKey);
    }
    
	private void getDynatraceStatus(ApiResponse apiResponse) {
        oneAgentSdk.addCustomRequestAttribute("customer-balance-status", apiResponse.getMessageDetail());
    }    
    
}