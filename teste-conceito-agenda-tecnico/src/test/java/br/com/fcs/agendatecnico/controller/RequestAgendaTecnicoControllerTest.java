package br.com.fcs.agendatecnico.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import br.com.fcs.agendatecnico.business.RequestAgendaTecnicoBusiness;
import br.com.fcs.agendatecnico.controller.RequestAgendaTecnicoController;
import br.com.fcs.agendatecnico.pubsub.PubSubSenderCore;
import br.com.fcs.agendatecnico.pubsub.PubSubSincronoEnvioFila;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.service.RequestAgendaTecnicoService;
import br.com.fcs.agendatecnico.vo.DedicatedAccountsVO;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;

@RunWith(MockitoJUnitRunner.class)
class RequestAgendaTecnicoControllerTest {

    @Mock
    RequestAgendaTecnicoController requestCustomerBalanceController;

	@Mock
	private RequestAgendaTecnicoService requestCustomerBalanceServiceMock;

	@Mock
	public RequestAgendaTecnicoBusiness requestCustomerBalanceBusinessMock;
	
	@Mock
	public PubSubSincronoEnvioFila pubSubSincronoEnvioFilaMock;
	
	@Mock
	public PubSubTemplate pubSubTemplateMock;
	
	@Mock
	private PubSubSenderCore pubSubSenderCore;
	
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    
    private RequestAgendaTecnicoVO requestCustomerBalanceVO;
	private DedicatedAccountsVO dedicatedAccountsVO;
			
	private final String adjustmentAmountRelative = "-2000";
	private final String dedicatedAccountID = "200";
    private final String accountBalance = "-1000";
    private final String dedicatedAccountUnitType = "1";
           
    private final String msisdn = "5511444444445";
	private final String originHostName = "ucipClient";
	private final String originNodeType = "AIR";
	private final String timestamp = "20191128T14:00:17-0300";
	private final String transactionCode = "DEBIT";
	private final String transactionID = "!@#ASDASD!";
    private final String transactionType = "EUC";
    
    private String bffKey;
	
	@BeforeEach
	public void setup() {
		initMocks(this);
		
		requestCustomerBalanceController = new RequestAgendaTecnicoController();
		requestCustomerBalanceServiceMock = new RequestAgendaTecnicoService(pubSubSincronoEnvioFilaMock);
		requestCustomerBalanceBusinessMock.setRequestCustomerBalanceService(requestCustomerBalanceServiceMock);
		requestCustomerBalanceController.setRequestCustomerBalanceBusiness(requestCustomerBalanceBusinessMock);

		dedicatedAccountsVO = new DedicatedAccountsVO(dedicatedAccountID, accountBalance, dedicatedAccountUnitType);
		requestCustomerBalanceVO = new RequestAgendaTecnicoVO(msisdn,
		         												timestamp,
		         												transactionID,
		         												transactionCode,
		         												transactionType,
		         												adjustmentAmountRelative,
		         												originNodeType,
		         												originHostName,
		         												dedicatedAccountsVO);
		bffKey = "123456dev";
		requestCustomerBalanceBusinessMock.setApigeeCredentialsBffkey("123456dev");
        request.addHeader("x-application-key", "123456dev");
        response.addHeader("x-application-key", "123456dev");
	}

	@Test
	void contexLoadsNotNull() throws Exception {
		assertThat(requestCustomerBalanceController).isNotNull();
	}
	
	@Test
    void requestCustomerBalanceHealthyOK() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(0);
        
        when(requestCustomerBalanceBusinessMock.processCustomerBalance(requestCustomerBalanceVO, bffKey)).thenReturn(apiResponse);
        ResponseEntity<ApiResponse> api = requestCustomerBalanceController.requestCustomerBalanceHealthy(request, response);
        assertEquals(HttpStatus.OK, api.getStatusCode());
    }
	
	@Test
    void requestCustomerBalanceOK() throws Exception {
	    ApiResponse apiResponse = new ApiResponse();
	    apiResponse.setCode(0);
        
	    when(requestCustomerBalanceBusinessMock.processCustomerBalance(requestCustomerBalanceVO, bffKey)).thenReturn(apiResponse);
	    ResponseEntity<ApiResponse> api = requestCustomerBalanceController.requestCustomerBalance(requestCustomerBalanceVO, request, response);
        assertEquals(HttpStatus.OK, api.getStatusCode());
    }
	
	@Test
    void requestCustomerBalanceACCEPTED() throws Exception {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(1);
        
        when(requestCustomerBalanceBusinessMock.processCustomerBalance(requestCustomerBalanceVO, bffKey)).thenReturn(apiResponse);
        ResponseEntity<ApiResponse> api = requestCustomerBalanceController.requestCustomerBalance(requestCustomerBalanceVO, request, response);
        assertEquals(HttpStatus.ACCEPTED, api.getStatusCode());
    }
   
	@Test
	void requestCustomerBalanceBAD_REQUEST() throws Exception {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
		apiResponse.setHttpCode(HttpStatus.BAD_REQUEST.value());
		apiResponse.setMessage(HttpStatus.BAD_REQUEST.name());
		apiResponse.setMessageDetail("Insert TransactionHistory fail");
			   
		when(requestCustomerBalanceBusinessMock.processCustomerBalance(requestCustomerBalanceVO, bffKey)).thenReturn(apiResponse);
	    ResponseEntity<ApiResponse> api = requestCustomerBalanceController.requestCustomerBalance(requestCustomerBalanceVO, request, response);	
	    assertEquals(HttpStatus.ACCEPTED.value(), api.getStatusCodeValue());
	    assertEquals("Insert TransactionHistory fail", api.getBody().getMessageDetail());
	    assertEquals(HttpStatus.BAD_REQUEST.name(), api.getBody().getMessage());
	    assertEquals(HttpStatus.BAD_REQUEST.value(), api.getBody().getHttpCode());
	    assertEquals(HttpStatus.BAD_REQUEST.value(), api.getBody().getCode().intValue());	    
	}
}