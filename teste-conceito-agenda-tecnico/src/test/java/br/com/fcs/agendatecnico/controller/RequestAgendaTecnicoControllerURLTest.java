package br.com.fcs.agendatecnico.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fcs.agendatecnico.business.RequestAgendaTecnicoBusiness;
import br.com.fcs.agendatecnico.business.TokenAccessBusiness;
import br.com.fcs.agendatecnico.controller.RequestAgendaTecnicoController;
import br.com.fcs.agendatecnico.pubsub.PubSubSincronoEnvioFila;
import br.com.fcs.agendatecnico.repository.TokenAccessRepository;
import br.com.fcs.agendatecnico.service.RequestAgendaTecnicoService;
import br.com.fcs.agendatecnico.service.TokenAccessService;
import br.com.fcs.agendatecnico.vo.DedicatedAccountsVO;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;
import br.com.fcs.agendatecnico.xml.CapturaCamposXMLResponseOCS;

@WebMvcTest(controllers = RequestAgendaTecnicoController.class)
@ActiveProfiles("test")
class RequestAgendaTecnicoControllerURLTest {
	
    @Autowired                           
    private MockMvc mockMvc;
	
    @MockBean
    RequestAgendaTecnicoController requestCustomerBalanceController;

	@Mock
	public RequestAgendaTecnicoBusiness requestCustomerBalanceBusinessMock;

	@Mock
	private RequestAgendaTecnicoService requestCustomerBalanceServiceMock;
	
	@Mock
	private CapturaCamposXMLResponseOCS capturaCamposXMLResponseOCSMock;
	
	@Mock
    private TokenAccessBusiness tokenAccessBusinessMock;
	
	@Mock
	private TokenAccessService tokenAccessServiceMock;
	
	@MockBean
	private TokenAccessRepository tokenAccessRepositoryMock;

	@MockBean
	public PubSubSincronoEnvioFila pubSubSincronoEnvioFilaMock;
	
	@MockBean
	public PubSubTemplate pubSubTemplateMock;

	private final String basePathCustomer = "/api/v1/requestCustomerBalance/"; 
	
	private RequestAgendaTecnicoVO requestCustomerBalanceVO;
	private DedicatedAccountsVO dedicatedAccountsVO;
			
	private final String adjustmentAmountRelative = "2000";
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
	
	@BeforeEach
	public void setup() {
		initMocks(this);
		
		requestCustomerBalanceController = new RequestAgendaTecnicoController();
		
		pubSubSincronoEnvioFilaMock = new PubSubSincronoEnvioFila(pubSubTemplateMock);
		tokenAccessServiceMock = new TokenAccessService();
		tokenAccessServiceMock.setTokenAccessRepository(tokenAccessRepositoryMock);
		
		tokenAccessBusinessMock.setTokenAccessService(tokenAccessServiceMock);
		
		requestCustomerBalanceServiceMock = new RequestAgendaTecnicoService(pubSubSincronoEnvioFilaMock);
		requestCustomerBalanceBusinessMock.setRequestCustomerBalanceService(requestCustomerBalanceServiceMock);
		requestCustomerBalanceBusinessMock.setTokenAccessBusiness(tokenAccessBusinessMock);
		
		
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
	}

	@Test
	void contexLoadsNotNull() throws Exception {
		assertThat(requestCustomerBalanceController).isNotNull();
	}

	@Test
	void shouldbasePathCustomerURLNotSucess() throws Exception {
		this.mockMvc.perform(post(basePathCustomer)
				  .content(requestCustomerBalanceVO.toString()).contentType("application/json;charset=UTF-8"))
				  .andDo(print())
				  .andExpect(status().isOk());
	}
}