package br.com.fcs.agendatecnico.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.google.cloud.datastore.DatastoreException;

import br.com.fcs.agendatecnico.business.RequestAgendaTecnicoBusiness;
import br.com.fcs.agendatecnico.business.TokenAccessBusiness;
import br.com.fcs.agendatecnico.enums.OCSDataType;
import br.com.fcs.agendatecnico.enums.OCSTypeCall;
import br.com.fcs.agendatecnico.enums.OriginNodeTypeEnum;
import br.com.fcs.agendatecnico.enums.TransactionCodeEnum;
import br.com.fcs.agendatecnico.enums.TransactionTypeEnum;
import br.com.fcs.agendatecnico.enums.XmlTagNameEnum;
import br.com.fcs.agendatecnico.pubsub.PubSubSincronoEnvioFila;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.service.RequestAgendaTecnicoService;
import br.com.fcs.agendatecnico.service.TokenAccessService;
import br.com.fcs.agendatecnico.vo.DedicatedAccountsVO;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;
import br.com.fcs.agendatecnico.vo.RequestOcsJsonVO;
import br.com.fcs.agendatecnico.vo.TransactionHistoryVO;
import br.com.fcs.agendatecnico.xml.CapturaCamposXMLResponseOCS;

@RunWith(MockitoJUnitRunner.class)
class RequestAgendaTecnicoBusinessTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAgendaTecnicoBusinessTest.class);
	
	private RequestAgendaTecnicoVO requestCustomerBalanceVO;
	
	private TransactionHistoryVO transactionHistoryVO;
	
	public RequestAgendaTecnicoBusiness requestCustomerBalanceBusiness;
	
	@Mock
	private RequestAgendaTecnicoService requestCustomerBalanceServiceMock;
	
	@Mock
	private TokenAccessBusiness tokenAccessBusinessMock;
	
	@Mock
	private TokenAccessService tokenAccessServiceMock;
	
	@Mock
	private PubSubSincronoEnvioFila pubSubSincronoEnvioFilaMock;
	
	private CapturaCamposXMLResponseOCS capturaCamposXMLResponseOCS;
	   
	private String msisdn           = StringUtils.repeat("1", 13);
	private String timestamp        = "20191201T17:20:50+0000";
	private String transactionID    = StringUtils.repeat("1", 20);
	private String transactionCode  = TransactionCodeEnum.DEBIT.getDesc(); 
	private String transactionType  = TransactionTypeEnum.EUC.name();   
	private String amount           = "-9223372036854775808";
	private String originNodeType   = OriginNodeTypeEnum.ADM.name();    
	private String originHostName   = StringUtils.repeat("C", 30);
	private DedicatedAccountsVO dedicatedAccountsVO;
	private String payloadOCSXML = "<methodCall><methodName>UpdateBalanceAndDate</methodName><params><param><value><struct><member><name>originHostName</name><value><string>CCCCCCCCCCCCCCCCCCCCCCCCCCCCCC</string></value></member><member><name>originTimeStamp</name><value><dateTime.iso8601>20191201T17:20:50+0000</dateTime.iso8601></value></member><member><name>originNodeType</name><value><string>ADM</string></value></member><member><name>originTransactionID</name><value><string>11111111111111111111</string></value></member><member><name>subscriberNumber</name><value><string>1111111111111</string></value></member><member><name>dedicatedAccountUpdateInformation</name><value><array><data><value><struct><member><name>dedicatedAccountID</name><value><int>220</int></value></member><member><name>adjustmentAmountRelative</name><value><string>-9223372036854775808</string></value></member><member><name>dedicatedAccountUnitType</name><value><i4>1</i4></value></member></struct></value></data></array></value></member><member><name>transactionCurrency</name><value><string>BRL</string></value></member><member><name>negotiatedCapabilities</name><value><array><data><value><i4>805646916</i4></value><value><i4>1576</i4></value></data></array></value></member></struct></value></param></params></methodCall>";

	private String addressOCS = "http://mocked.com.br";
	private String addressTransaction = "http://mocked.com.br";
	
	private String tokenAccess = "Bearer 936ssDEvmJnugriwnRHXFfOYpj0f";
    
	private String xmlOkOCSResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodResponse><params><param><value><struct><member><name>accountFlagsAfter</name><value><struct><member><name>activationStatusFlag</name><value><boolean>1</boolean></value></member><member><name>negativeBarringStatusFlag</name><value><boolean>0</boolean></value></member><member><name>serviceFeePeriodExpiryFlag</name><value><boolean>0</boolean></value></member><member><name>serviceFeePeriodWarningActiveFlag</name><value><boolean>0</boolean></value></member><member><name>supervisionPeriodExpiryFlag</name><value><boolean>0</boolean></value></member><member><name>supervisionPeriodWarningActiveFlag</name><value><boolean>0</boolean></value></member></struct></value></member><member><name>accountFlagsBefore</name><value><struct><member><name>activationStatusFlag</name><value><boolean>1</boolean></value></member><member><name>negativeBarringStatusFlag</name><value><boolean>0</boolean></value></member><member><name>serviceFeePeriodExpiryFlag</name><value><boolean>0</boolean></value></member><member><name>serviceFeePeriodWarningActiveFlag</name><value><boolean>0</boolean></value></member><member><name>supervisionPeriodExpiryFlag</name><value><boolean>0</boolean></value></member><member><name>supervisionPeriodWarningActiveFlag</name><value><boolean>0</boolean></value></member></struct></value></member><member><name>accountValue1</name><value><string>1300</string></value></member><member><name>availableServerCapabilities</name><value><array><data><value><i4>805646916</i4></value><value><i4>1576</i4></value></data></array></value></member><member><name>currency1</name><value><string>BRL</string></value></member><member><name>dedicatedAccountChangeInformation</name><value><array><data><value><struct><member><name>dedicatedAccountActiveValue1</name><value><string>9223372036854775807</string></value></member><member><name>dedicatedAccountID</name><value><i4>220</i4></value></member><member><name>dedicatedAccountUnitType</name><value><i4>1</i4></value></member><member><name>dedicatedAccountValue1</name><value><string>3000</string></value></member></struct></value></data></array></value></member><member><name>negotiatedCapabilities</name><value><array><data><value><i4>805646916</i4></value><value><i4>1576</i4></value></data></array></value></member><member><name>originTransactionID</name><value><string>1</string></value></member><member><name>responseCode</name><value><i4>0</i4></value></member></struct></value></param></params></methodResponse>";

	private RequestOcsJsonVO requestOcsJsonVO = null;
	
	private String bffKey;
	
	private DatastoreException datastoreException = new DatastoreException(14, 
                                                                           "I/O error", 
                                                                           "code=UNAVAILABLE",
                                                                           new UnknownHostException("datastore.googleapis.com"));
	
	@BeforeEach
	public void setUp() throws Exception {
	    initMocks(this);
		
	    tokenAccessBusinessMock.setTokenAccessService(tokenAccessServiceMock);
	    
		capturaCamposXMLResponseOCS = new CapturaCamposXMLResponseOCS();
		requestCustomerBalanceBusiness = new RequestAgendaTecnicoBusiness();
		requestCustomerBalanceBusiness.setRequestCustomerBalanceService(requestCustomerBalanceServiceMock);
		requestCustomerBalanceBusiness.setCapturaCamposXMLResponseOCS(capturaCamposXMLResponseOCS);
		requestCustomerBalanceBusiness.setTokenAccessBusiness(tokenAccessBusinessMock);
		requestCustomerBalanceBusiness.setApigeeCredentialsBffkey("123456dev");

		final String dedicatedAccountID = "200";
	    final String accountBalance = "-1000";
	    final String dedicatedAccountUnitType = "1";

        dedicatedAccountsVO = new DedicatedAccountsVO(dedicatedAccountID, accountBalance, dedicatedAccountUnitType);
	
		requestCustomerBalanceVO = new RequestAgendaTecnicoVO(msisdn,
				                                                timestamp,
				                                                transactionID,
				                                                transactionCode,
				                                                transactionType,
				                                                amount,
				                                                originNodeType,
				                                                originHostName,
				                                                dedicatedAccountsVO);

		transactionHistoryVO = new TransactionHistoryVO(requestCustomerBalanceVO.getTimestamp(),      
                                                        requestCustomerBalanceVO.getMsisdn(),         
                                                        requestCustomerBalanceVO.getTransactionType(),
                                                        requestCustomerBalanceVO.getAmount(),       
                                                        requestCustomerBalanceVO.getTransactionCode(), 
                                                        requestCustomerBalanceVO.getTransactionID()); 
				
		requestOcsJsonVO = new RequestOcsJsonVO(requestCustomerBalanceVO.getMsisdn(),
		                                        requestCustomerBalanceVO.getTimestamp(),
												requestCustomerBalanceVO.getTransactionID(),
												requestCustomerBalanceVO.getAmount(),
												requestCustomerBalanceVO.getOriginNodeType(),
												requestCustomerBalanceVO.getOriginHostName(),
												"220",
                                                "805646916",
                                                "1576");				

	    requestCustomerBalanceBusiness.setHttpAddressOcs(addressOCS);    
		requestCustomerBalanceBusiness.setTransactionHistoryAddress(addressTransaction);
		requestCustomerBalanceBusiness.setTokenAccess(tokenAccess);
		bffKey = "123456dev";
	}
    
	@Test
    void bffKeyIsEmpty() {
        bffKey = "";
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
    @Test
    void bffKeyIsNull() {
        bffKey = null;
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
    @Test
    void bffKeyNotInvalid() {
        bffKey = "5678";
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }

	@Test
    void msisdnIsEmpty() {
        requestCustomerBalanceVO.setMsisdn("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void TimestampIsEmpty() {
        requestCustomerBalanceVO.setTimestamp("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionIdIsEmpty() {
        requestCustomerBalanceVO.setTransactionID("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionCodeIsEmpty() {
        requestCustomerBalanceVO.setTransactionCode("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }

    @Test
    void transactionTypeIsEmpty() {
        requestCustomerBalanceVO.setTransactionType("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountIsEmpty() {
        requestCustomerBalanceVO.setAmount("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }

    @Test
    void originNodeTypeIsEmpty() {
        requestCustomerBalanceVO.setOriginNodeType("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void originHostNameIsEmpty() {
        requestCustomerBalanceVO.setOriginHostName("");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void msisdnIsNull() {
        requestCustomerBalanceVO.setMsisdn(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void TimestampIsNull() {
        requestCustomerBalanceVO.setTimestamp(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionIdIsNull() {
        requestCustomerBalanceVO.setTransactionID(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionCodeIsNull() {
        requestCustomerBalanceVO.setTransactionCode(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionTypeIsNull() {
        requestCustomerBalanceVO.setTransactionType(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountIsNull() {
        requestCustomerBalanceVO.setAmount(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }

    @Test
    void originNodeTypeIsNull() {
        requestCustomerBalanceVO.setOriginNodeType(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void originHostNameIsNull() {
        requestCustomerBalanceVO.setOriginHostName(null);
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Required field not found", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void msisdnIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setMsisdn(StringUtils.repeat("1", 10));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Msisdn format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionCodeIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setTransactionCode(StringUtils.repeat("A", 31));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("TransactionCode field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionIdIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setTransactionID(StringUtils.repeat("A", 21));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("TransactionId field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionTypeIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setTransactionType(StringUtils.repeat("A", 31));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("TransactionType field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionIdIsVerifyIntError() {
        requestCustomerBalanceVO.setTransactionID("1%3312");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("TransactionId field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void originNodeTypeIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setOriginNodeType(StringUtils.repeat("A", 9));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("OriginNodeType field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void originHostNameIsVerifyStringRangeError() {
        requestCustomerBalanceVO.setOriginHostName(StringUtils.repeat("A", 31));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("OriginHostName field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void msisdnVerifyOtherRulesError() {
        requestCustomerBalanceVO.setMsisdn(StringUtils.repeat("A", 2).concat(StringUtils.repeat("1", 11)));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Msisdn format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void timestampVerifyOtherRulesError() {
        requestCustomerBalanceVO.setTimestamp("2019-11-28T14:00:12");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Timestamp format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionCodeVerifyOtherRulesError() {
        requestCustomerBalanceVO.setTransactionCode("DEIBXXX");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("TransactionCode field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountDebitVerifyOtherRulesError() {
        requestCustomerBalanceVO.setAmount("-1000s");
        requestCustomerBalanceVO.setTransactionCode("DEBIT");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountDebitVerifyRangeError() {
        requestCustomerBalanceVO.setAmount("-9223372036854775809");
        requestCustomerBalanceVO.setTransactionCode("DEBIT");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountAdjVerifyRangeError() {
        requestCustomerBalanceVO.setAmount("9223372036854775808");
        requestCustomerBalanceVO.setTransactionCode("ADJ");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void amountAdjVerifyOtherRulesError() {
        requestCustomerBalanceVO.setAmount("1000s");
        requestCustomerBalanceVO.setTransactionCode("ADJ");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void originNodeTypeVerifyOtherRulesError() {
        requestCustomerBalanceVO.setOriginNodeType(StringUtils.repeat("A", 3));
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("OriginNodeType field is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void transactionCodeADJIsVerifyOtherRulesErrorNegativo() {
        requestCustomerBalanceVO.setTransactionCode(TransactionCodeEnum.ADJ.getDesc());
        requestCustomerBalanceVO.setAmount("-1");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }

    @Test
    void transactionCodeDEBITIsVerifyOtherRulesErrorPositivo() {
        requestCustomerBalanceVO.setTransactionCode(TransactionCodeEnum.DEBIT.getDesc());
        requestCustomerBalanceVO.setAmount("1");
        ApiResponse apiResponse = requestCustomerBalanceBusiness.fieldValidation(requestCustomerBalanceVO, bffKey);
        assertEquals("Amount format is invalid", (apiResponse.getMessageDetail()));
    }
    
    @Test
    void typeCallOCSInvalidIsEmpty() {
        requestCustomerBalanceBusiness.setTypeCallOCS("");
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        ApiResponse apiResponse_invalid_TypeCallOCS = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Service HTTP fail", apiResponse_invalid_TypeCallOCS.getMessageDetail());
    }
    
    @Test
    void typeCallOCSInvalidIsNull() {
        requestCustomerBalanceBusiness.setTypeCallOCS(null);
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        ApiResponse apiResponse_invalid_TypeCallOCS = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Service HTTP fail", apiResponse_invalid_TypeCallOCS.getMessageDetail());
    }

    
    
    
    @Test
    void validaExcpetionTrasacaoGetTokenFindAll() {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        TokenAccessBusiness tokenAccessBusiness = new TokenAccessBusiness();
        tokenAccessBusiness.setTokenAccessService(tokenAccessServiceMock);
        requestCustomerBalanceBusiness.setTokenAccessBusiness(tokenAccessBusiness);
        
        when(tokenAccessServiceMock.findAll()).thenThrow(datastoreException);
        
        ApiResponse apiResponse_invalid_TypeCallOCS = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Customer balance request denied", apiResponse_invalid_TypeCallOCS.getMessageDetail());
    }

    
    @Test
    void validaExcpetionTrasacaoGetToken() {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenThrow(NullPointerException.class);
        
        ApiResponse apiResponse_invalid_TypeCallOCS = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Customer balance request denied", apiResponse_invalid_TypeCallOCS.getMessageDetail());
    }

    @Test
    void validaExcptionJSONConvertResponseJSOMError() throws IOException, RuntimeException, Exception {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.contains(addressOCS), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.contains(tokenAccess))).thenReturn( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
                                                                                                            "<methodResponse>\r\n" + 
                                                                                                            "    <params>\r\n" + 
                                                                                                            "        <param>\r\n" + 
                                                                                                            "            <value>\r\n" + 
                                                                                                            "                <struct>\r\n" + 
                                                                                                            "                    <member>\r\n" + 
                                                                                                            "                        <name>availableServerCapabilities</name>\r\n" + 
                                                                                                            "                        <value>\r\n" + 
                                                                                                            "                            <array>\r\n" + 
                                                                                                            "                                <data>\r\n" + 
                                                                                                            "                                    <value>\r\n" + 
                                                                                                            "                                        <i4>805646916</i4>\r\n" + 
                                                                                                            "                                    </value>\r\n" + 
                                                                                                            "                                    <value>\r\n" + 
                                                                                                            "                                        <i4>1576</i4>\r\n" + 
                                                                                                            "                                    </value>\r\n" + 
                                                                                                            "                                </data>\r\n" + 
                                                                                                            "                            </array>\r\n" + 
                                                                                                            "                        </value>\r\n" + 
                                                                                                            "                    </member>\r\n" + 
                                                                                                            "                    <member>\r\n" + 
                                                                                                            "                        <name>negotiatedCapabilities</name>\r\n" + 
                                                                                                            "                        <value>\r\n" + 
                                                                                                            "                            <array>\r\n" + 
                                                                                                            "                                <data>\r\n" + 
                                                                                                            "                                    <value>\r\n" + 
                                                                                                            "                                        <i4>805646916</i4>\r\n" + 
                                                                                                            "                                    </value>\r\n" + 
                                                                                                            "                                    <value>\r\n" + 
                                                                                                            "                                        <i4>1576</i4>\r\n" + 
                                                                                                            "                                    </value>\r\n" + 
                                                                                                            "                                </data>\r\n" + 
                                                                                                            "                            </array>\r\n" + 
                                                                                                            "                        </value>\r\n" + 
                                                                                                            "                    </member>\r\n" + 
                                                                                                            "                    <member>\r\n" + 
                                                                                                            "                        <name>originTransactionID</name>\r\n" + 
                                                                                                            "                        <value>\r\n" + 
                                                                                                            "                            <string>12</string>\r\n" + 
                                                                                                            "                        </value>\r\n" + 
                                                                                                            "                    </member>\r\n" + 
                                                                                                            "                    <member>\r\n" + 
                                                                                                            "                        <name>responseCode</name>\r\n" + 
                                                                                                            "                        <value>\r\n" + 
                                                                                                            "                            <i4>106</i4>\r\n" + 
                                                                                                            "                        </value>\r\n" + 
                                                                                                            "                    </member>\r\n" + 
                                                                                                            "                </struct>\r\n" + 
                                                                                                            "            </value>\r\n" + 
                                                                                                            "        </param>\r\n" + 
                                                                                                            "    </params>\r\n" + 
                                                                                                            "</methodResponse>");
        
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(200);
        
        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(200, apiResponse.getHttpCode());
    }
    
    @Test
    void validaExcptionJSONConvertResponseJSOMErrorJson() throws IOException, RuntimeException, Exception {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.contains(addressOCS), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.contains(tokenAccess))).thenReturn( "{\r\n" + 
                                                                                                            "    \"teste\": \"1\",\r\n" +
                                                                                                            "}");
        
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(200);
        
        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(200, apiResponse.getHttpCode());
    }
    
	@Test
    void validaRetornoOCSJsonRerroUnauthorized() throws IOException, RuntimeException, Exception {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.contains(addressOCS), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.contains(tokenAccess))).thenReturn( "{\r\n" + 
                                                                                                            "    \"apiVersion\": \"1\",\r\n" + 
                                                                                                            "    \"transactionId\": \"63a4ed99-9801-445c-aa37-945ed6696e81\",\r\n" + 
                                                                                                            "    \"error\": {\r\n" + 
                                                                                                            "        \"httpCode\": 401,\r\n" + 
                                                                                                            "        \"errorCode\": \"API-CUSTOMERBALANCESRECHARGE-401\",\r\n" + 
                                                                                                            "        \"message\": \"Unauthorized\",\r\n" + 
                                                                                                            "        \"detailedMessage\": \"Client authentication token invalid.\",\r\n" + 
                                                                                                            "        \"link\": {\r\n" + 
                                                                                                            "            \"rel\": \"related\",\r\n" + 
                                                                                                            "            \"href\": \"https://api.claro.com.br/docs\"\r\n" + 
                                                                                                            "        }\r\n" + 
                                                                                                            "    }\r\n" + 
                                                                                                            "}");
        
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(401);

        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Client authentication token invalid.", apiResponse.getMessageDetail());
        assertEquals(new Integer(401), apiResponse.getCode());
        assertEquals(401, apiResponse.getHttpCode());
    }
	
	@Test
    void validaRetornoOCSJsonRerroMsdnNotFound() throws IOException, RuntimeException, Exception {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.contains(addressOCS), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.contains(tokenAccess))).thenReturn( "{\r\n" + 
                                                                                                            "    \"code\":102,\r\n" + 
                                                                                                            "    \"transactionID\":\"14800\",\r\n" + 
                                                                                                            "    \"httpCode\":0,\r\n" + 
                                                                                                            "    \"error\":{\r\n" + 
                                                                                                            "        \"httpCode\":\"202\",\r\n" + 
                                                                                                            "        \"errorCode\":\"102\",\r\n" + 
                                                                                                            "        \"message\":\"OK\",\r\n" + 
                                                                                                            "        \"detailedMessage\":\"Msisdn not found\"\r\n" + 
                                                                                                            "    },\r\n" + 
                                                                                                            "    \"referenceAddress\":\"https://api.claro.com.br/docs/error_codes.html\"\r\n" + 
                                                                                                            "}");
        
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(202);

        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Msisdn not found", apiResponse.getMessageDetail());
        assertEquals(new Integer(102), apiResponse.getCode());
        assertEquals(202, apiResponse.getHttpCode());
    }
	
	@Test
    void validaRetornoOCSJsonSemErrorCodeRetornoDiferente200() throws IOException, RuntimeException, Exception {
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.contains(addressOCS), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.contains(tokenAccess))).thenReturn( "{\r\n" + 
                                                                                                            "    \"apiVersion\": \"1\",\r\n" + 
                                                                                                            "    \"transactionId\": \"63a4ed99-9801-445c-aa37-945ed6696e81\"\r\n" + 
                                                                                                            "}");
        
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(401);

        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(401, apiResponse.getHttpCode());
    }
		
	@Test
    void envioTransacrionErrorFilaPubsub() throws IOException, RuntimeException, Exception {

        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(transactionHistoryVO.toString()), 
                                                                Mockito.anyString(), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.any())).thenReturn("<methodResponse></methodResponse>"); 
        
        requestCustomerBalanceBusiness.sendTransaction(requestCustomerBalanceVO);
        assertEquals(requestCustomerBalanceBusiness.getTransactionHistoryAddress(), addressTransaction);
    }
	
	@Test
    void envioRequestOCSTrueEnvioTransacrionErrorFilaPubsub() throws IOException, RuntimeException, Exception {
		
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_ACCEPTED);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(transactionHistoryVO.toString()), 
                                                                Mockito.anyString(), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.any())).thenReturn("{\r\n" + 
                                                                                           "    \"code\": 8,\r\n" + 
                                                                                           "    \"messageDetail\": \"This event already exists\",\r\n" + 
                                                                                           "    \"httpCode\": 0,\r\n" + 
                                                                                           "    \"link\": {\r\n" + 
                                                                                           "        \"rel\": \"related\",\r\n" + 
                                                                                           "        \"href\": \"https://api.claro.com.br/docs/error_codes.html\"\r\n" + 
                                                                                           "    }\r\n" + 
                                                                                           "}");

        requestCustomerBalanceBusiness.sendTransaction(requestCustomerBalanceVO);
        assertEquals(requestCustomerBalanceBusiness.getTransactionHistoryAddress(), addressTransaction);
    }
	
	@Test
    void envioResquestOCSEnvioTransactionHistorySucesso() throws IOException, RuntimeException, Exception {
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(requestOcsJsonVO.toString()), 
                                                                Mockito.anyString(), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.any())).thenReturn("{\r\n" + 
                                                                                           "    \"code\": \"0\",\r\n" + 
                                                                                           "    \"messegeDetail\": \"The transaction was sucessfully\",\r\n" + 
                                                                                           "    \"amount\": \"26278\",\r\n" + 
                                                                                           "    \"httpCode\": \"200\",\r\n" + 
                                                                                           "    \"transactionID\": \"12\",\r\n" + 
                                                                                           "    \"message\": \"OK\",\r\n" + 
                                                                                           "    \"link\": {\r\n" + 
                                                                                           "        \"rel\": \"related\",\r\n" + 
                                                                                           "        \"href\": \"https://api.claro.com.br/docs/error_code\"\r\n" + 
                                                                                           "    }\r\n" + 
                                                                                           "}");
        
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.JSON.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(transactionHistoryVO.toString()), 
                                                                Mockito.anyString(), 
                                                                Mockito.contains(MediaType.APPLICATION_JSON_VALUE), 
                                                                Mockito.any())).thenReturn("{\r\n" + 
                                                                                           "    \"code\": 0,\r\n" + 
                                                                                           "    \"messageDetail\": \"The transaction was successfully\",\r\n" + 
                                                                                           "    \"httpCode\": 0,\r\n" + 
                                                                                           "    \"link\": {\r\n" + 
                                                                                           "        \"rel\": \"related\",\r\n" + 
                                                                                           "        \"href\": \"https://api.claro.com.br/docs/error_codes.html\"\r\n" + 
                                                                                           "    }\r\n" + 
                                                                                           "}");


        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
        
        assertEquals(new Integer(0),apiResponse.getCode());
        assertEquals("The transaction was successfully", apiResponse.getMessageDetail());
        assertEquals("26278", apiResponse.getAmount());
        assertEquals("11111111111111111111", apiResponse.getTransactionID());
        assertEquals(200, apiResponse.getHttpCode());
    }
	
	@Test
    void shouldPassSetGetCustomerBalanceField() {
        
        boolean compare = false;

        RequestAgendaTecnicoVO requestCustomerBalanceVOTesteRetorno = new RequestAgendaTecnicoVO();
        DedicatedAccountsVO dedicatedAccountUpdateInformationTesteRetorno = new DedicatedAccountsVO("", "", "");
        
        for (Map.Entry<String, String[]> entry : getUpdateBalanceAndDateFields().entrySet()) {
            String key = entry.getKey();
            String[] tab = entry.getValue();

            if (key.equals(XmlTagNameEnum.ORIGIN_HOST_NAME.getDesc())){ 
                requestCustomerBalanceVOTesteRetorno.setOriginHostName(tab[1]); 
            }else if (key.equals(XmlTagNameEnum.ORIGIN_NODE_TYPE.getDesc())){ 
                requestCustomerBalanceVOTesteRetorno.setOriginNodeType(tab[1]); 
            }else if (key.equals(XmlTagNameEnum.SUBSCRIBER_NUMBER.getDesc())){ 
                requestCustomerBalanceVOTesteRetorno.setMsisdn(tab[1]);         
            }else if (key.equals(XmlTagNameEnum.ORIGIN_TRANSACTION_ID.getDesc())){ 
                requestCustomerBalanceVOTesteRetorno.setTransactionID(tab[1]);  
            }else if (key.equals(XmlTagNameEnum.ORIGIN_TIMESTAMP.getDesc())){ 
                requestCustomerBalanceVOTesteRetorno.setTimestamp(tab[1]);      
            }else { 
                LOGGER.info("outros {}", key);                                  
            }
        }
        
        for (Map.Entry<String, String[]> entry : getDedicateAccountFields().entrySet()) {
            String key = entry.getKey();
            String[] tab = entry.getValue();

            if (key.equals(XmlTagNameEnum.DEDICATED_ACCOUNT_ID.getDesc())){ 
                dedicatedAccountUpdateInformationTesteRetorno.setDedicatedAccountID(tab[1]);      
            }else if (key.equals(XmlTagNameEnum.ADJUSTMENT_AMOUNT_RELATIVE.getDesc())){ 
                dedicatedAccountUpdateInformationTesteRetorno.setAccountBalance(tab[1]);          
            }else if (key.equals(XmlTagNameEnum.DEDICATED_ACCOUNT_UNIT_TYPE.getDesc())){ 
                dedicatedAccountUpdateInformationTesteRetorno.setDedicatedAccountUnitType(tab[1]);
            }else { 
                LOGGER.info("outros {}", key);                                                    
            }
        }

        requestCustomerBalanceVOTesteRetorno.setDedicatedAccountUpdateInformation(dedicatedAccountUpdateInformationTesteRetorno);

        compare = requestCustomerBalanceVO.equalsFieldsXML(requestCustomerBalanceVOTesteRetorno);
        assertTrue(compare);
    }
	
	@Test
	void validaOSCXMLSucesso() throws RuntimeException, Exception {
	         
	    requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.XML.getDesc());

	    when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
	    
	    when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(payloadOCSXML), 
	                                                                Mockito.anyString(),  
	                                                                Mockito.contains(MediaType.APPLICATION_XML_VALUE), 
	                                                                Mockito.any())).thenReturn(xmlOkOCSResponse); 
	        
	    when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

	        
	    ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
	        
	    assertEquals(new Integer(0),apiResponse.getCode());
	    assertEquals("The transaction was successfully", apiResponse.getMessageDetail());
	    assertEquals("9223372036854775807", apiResponse.getAmount());
	    assertEquals("11111111111111111111", apiResponse.getTransactionID());
	    assertEquals(200, apiResponse.getHttpCode());
	}
	
    @Test
    void validaOCSXMLErrorHTTP_CLIENT_TIMEOUTResponse() throws RuntimeException, Exception {
             
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.XML.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(payloadOCSXML), 
                                                                Mockito.anyString(),  
                                                                Mockito.contains(MediaType.APPLICATION_XML_VALUE), 
                                                                Mockito.any())).thenReturn(xmlOkOCSResponse); 
            
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_CLIENT_TIMEOUT);

            
        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);

        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(408, apiResponse.getHttpCode());

    }	
	
    @Test
    void validaOCSXMLErrorZeroValorDedicatedAccountID() throws RuntimeException, Exception {
             
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.XML.getDesc());

        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);
        
        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(payloadOCSXML), 
                                                                Mockito.anyString(),  
                                                                Mockito.contains(MediaType.APPLICATION_XML_VALUE), 
                                                                Mockito.any())).thenReturn(""); 
            
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_CLIENT_TIMEOUT);

            
        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
            
        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(408, apiResponse.getHttpCode());

    }   
	
    @Test
    void validaOCSXMLErrorException() throws RuntimeException, Exception {
             
        requestCustomerBalanceBusiness.setTypeCallOCS(OCSTypeCall.XML.getDesc());
        
        when(tokenAccessBusinessMock.getTokenAccess()).thenReturn(tokenAccess);

        when(requestCustomerBalanceServiceMock.envioPostRequest(Mockito.contains(payloadOCSXML), 
                                                                Mockito.anyString(),  
                                                                Mockito.contains(MediaType.APPLICATION_XML_VALUE), 
                                                                Mockito.any())).thenThrow(Exception.class); 
            
        when(requestCustomerBalanceServiceMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_CLIENT_TIMEOUT);

            
        ApiResponse apiResponse = requestCustomerBalanceBusiness.processCustomerBalance(requestCustomerBalanceVO, bffKey);
            
        assertEquals("Customer balance request denied", apiResponse.getMessageDetail());
        assertEquals(new Integer(8), apiResponse.getCode());
        assertEquals(408, apiResponse.getHttpCode());

    }
    
	private HashMap<String, String[]> getUpdateBalanceAndDateFields() {
        DedicatedAccountsVO dedicatedAccountsVO = new DedicatedAccountsVO("220", requestCustomerBalanceVO.getAmount(),"1");
        requestCustomerBalanceVO.setDedicatedAccountUpdateInformation(dedicatedAccountsVO);

        HashMap<String, String[]> UpdateBalanceAndDateFields = new HashMap<String, String[]>();
        UpdateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_NODE_TYPE.getDesc(),new String[] { OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getOriginNodeType() });
        UpdateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_HOST_NAME.getDesc(),new String[] { OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getOriginHostName() });
        UpdateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_TRANSACTION_ID.getDesc(),new String[] { OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getTransactionID() });
        UpdateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_TIMESTAMP.getDesc(),new String[] { OCSDataType.ISO_8601.getDesc(), requestCustomerBalanceVO.getTimestamp() });
        UpdateBalanceAndDateFields.put(XmlTagNameEnum.SUBSCRIBER_NUMBER.getDesc(),new String[] { OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getMsisdn() });
        return UpdateBalanceAndDateFields;
    }
	
    private HashMap<String, String[]> getDedicateAccountFields(){
        HashMap<String, String[]> dedicateAccountFields = new HashMap<String, String[]>();
        dedicateAccountFields.put(XmlTagNameEnum.DEDICATED_ACCOUNT_ID.getDesc(), new String[] {OCSDataType.INT.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountID()});
        dedicateAccountFields.put(XmlTagNameEnum.ADJUSTMENT_AMOUNT_RELATIVE.getDesc(), new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getAccountBalance()});
        dedicateAccountFields.put(XmlTagNameEnum.DEDICATED_ACCOUNT_UNIT_TYPE.getDesc(), new String[] {OCSDataType.I4.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountUnitType()});
        return dedicateAccountFields;
    }
	
}
