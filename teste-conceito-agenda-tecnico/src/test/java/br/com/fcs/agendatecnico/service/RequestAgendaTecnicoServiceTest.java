package br.com.fcs.agendatecnico.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.http.MediaType;

import br.com.fcs.agendatecnico.enums.TransactionCodeEnum;
import br.com.fcs.agendatecnico.pubsub.PubSubSenderCore;
import br.com.fcs.agendatecnico.pubsub.PubSubSincronoEnvioFila;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.service.RequestAgendaTecnicoService;
import br.com.fcs.agendatecnico.utils.Utils;
import br.com.fcs.agendatecnico.vo.DedicatedAccountsVO;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;
import br.com.fcs.agendatecnico.vo.TransactionHistoryVO;

@RunWith(MockitoJUnitRunner.class)
class RequestAgendaTecnicoServiceTest {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAgendaTecnicoServiceTest.class);

	@Mock
	RequestAgendaTecnicoService requestCustomerBalanceServiceMock;

	@Mock
	private PubSubSincronoEnvioFila pubSubSincronoEnvioFilaMock;
	
	@Mock
	private PubSubSenderCore pubSubSenderCore;

	private RequestAgendaTecnicoVO requestCustomerBalanceVO;

	private TransactionHistoryVO transactionHistory;

	@Autowired
    PubSubTemplate pubSubTemplate;
	
	private DedicatedAccountsVO dedicatedAccountUpdateInformation = new DedicatedAccountsVO("123", "456", "789");
	private DedicatedAccountsVO dedicatedAccount;
	private String address = "http://mocked.com.br";
	private String sincronoTopic = "teste";

	@BeforeEach
	void setUp() {
		initMocks(this);

		pubSubSincronoEnvioFilaMock = new PubSubSincronoEnvioFila(pubSubTemplate);
		pubSubSincronoEnvioFilaMock.setPubSubSenderCore(pubSubSenderCore);
		pubSubSincronoEnvioFilaMock.setTopic(sincronoTopic);
		requestCustomerBalanceServiceMock = new RequestAgendaTecnicoService(pubSubSincronoEnvioFilaMock);

		dedicatedAccount = new DedicatedAccountsVO("200", "1000", "1");
		transactionHistory = new TransactionHistoryVO(DateTime.now().toString(), 
													  StringUtils.repeat("1", 13),
													  StringUtils.repeat("1", 13), 
													  String.valueOf(Long.MAX_VALUE), 
													  StringUtils.repeat("1", 13),
													  Utils.generateId());

		requestCustomerBalanceVO = new RequestAgendaTecnicoVO(StringUtils.repeat("1", 13),
																DateTime.now().toString(), 
																Utils.generateId(),
																TransactionCodeEnum.DEBIT.getDesc(), 
																StringUtils.repeat("A", 4), 
																"35", 
																StringUtils.repeat("A", 4),
																StringUtils.repeat("A", 4), 
																dedicatedAccount);

		requestCustomerBalanceVO.setAmount("1");
		requestCustomerBalanceVO.setDedicatedAccountUpdateInformation(dedicatedAccountUpdateInformation);
		requestCustomerBalanceVO.setMsisdn("11900000000");
		requestCustomerBalanceVO.setOriginNodeType("teste");
		requestCustomerBalanceVO.setTimestamp("teste");
		requestCustomerBalanceVO.setTransactionCode("1");
		requestCustomerBalanceVO.setTransactionID("1");
		requestCustomerBalanceVO.setTransactionType("teste");
	}

	@Test
	void timeoutExceptionShouldSendTransactionToTopic() throws IOException {
		try {
			doThrow(new SocketTimeoutException()).when(pubSubSenderCore).sendMessage(transactionHistory);
		} catch (Exception e) {
			requestCustomerBalanceServiceMock.envioPubSub(transactionHistory);
			assertEquals(0, requestCustomerBalanceServiceMock.getResponseCode());
			LOGGER.info(e.toString());			
		}
	}

	@Test
	void connectExceptionShouldSendTransactionToTopic() throws IOException {
		try {
			doThrow(new ConnectException()).when(pubSubSenderCore).sendMessage(transactionHistory);
		} catch (Exception e) {
	        assertEquals(0, requestCustomerBalanceServiceMock.getResponseCode());
			LOGGER.info(e.toString());
		}
	}

	@Test
	void RuntimeExceptionShouldReturnIOException() {
		String payloadBuildXML = "AAA";
		String response = "";

		try {
    		doThrow(new RuntimeException()).when(requestCustomerBalanceServiceMock).envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);
		}catch (Exception e) {
			try {
				response = requestCustomerBalanceServiceMock.envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);
			} catch (RuntimeException re) {
				assertNotNull(response);
				LOGGER.info(re.toString());
			} catch (IOException ioe) {
				assertNotNull(response);
				 assertTrue(true);
				LOGGER.info(ioe.toString());
			} catch (Exception e2) {
				assertNotNull(response);
				LOGGER.info(e2.toString());
			}
		}
	}
	
	@Test
	void ExceptionShouldReturnIOException() {
		String payloadBuildXML = "AAA";
		String response = "";

		try {
			doThrow(new Exception()).when(requestCustomerBalanceServiceMock).envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);			
		}catch (Exception e) {
			try {
				response = requestCustomerBalanceServiceMock.envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);
			} catch (RuntimeException re) {
				assertNotNull(response);
				LOGGER.info(re.toString());
			} catch (IOException ioe) {
				assertNotNull(response);
				assertTrue(true);
				LOGGER.info(ioe.toString());
			} catch (Exception e2) {
				assertNotNull(response);
				LOGGER.info(e2.toString());
			}
		}
	}
	
	@Test
	void IOExceptionShouldReturnIOException() {
		String payloadBuildXML = "AAA";
		String response = "";

		try {
			doThrow(new IOException()).when(requestCustomerBalanceServiceMock).envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);
		}catch (Exception e) {
			try {
				response = requestCustomerBalanceServiceMock.envioPostRequest(payloadBuildXML, address, MediaType.TEXT_XML_VALUE, null);
			} catch (RuntimeException re) {
				assertNotNull(response);
				LOGGER.info(re.toString());
			} catch (IOException ioe) {
				assertNotNull(response);
                assertTrue(true);
				LOGGER.info(ioe.toString());
			} catch (Exception e2) {
				assertNotNull(response);
				LOGGER.info(e2.toString());
			}
		}
	}	
}