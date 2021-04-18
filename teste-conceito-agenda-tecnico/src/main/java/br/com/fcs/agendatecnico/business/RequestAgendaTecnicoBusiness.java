package br.com.fcs.agendatecnico.business;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.dynatrace.oneagent.sdk.OneAgentSDKFactory;
import com.dynatrace.oneagent.sdk.api.OneAgentSDK;

import br.com.fcs.agendatecnico.constants.Constants;
import br.com.fcs.agendatecnico.enums.ErrorType;
import br.com.fcs.agendatecnico.enums.OCSDataType;
import br.com.fcs.agendatecnico.enums.OCSTypeCall;
import br.com.fcs.agendatecnico.enums.OriginNodeTypeEnum;
import br.com.fcs.agendatecnico.enums.SuccessMessage;
import br.com.fcs.agendatecnico.enums.TransactionCodeEnum;
import br.com.fcs.agendatecnico.enums.XmlTagNameEnum;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.service.RequestAgendaTecnicoService;
import br.com.fcs.agendatecnico.utils.JsonUtils;
import br.com.fcs.agendatecnico.utils.Utils;
import br.com.fcs.agendatecnico.vo.DedicatedAccountsVO;
import br.com.fcs.agendatecnico.vo.RequestAgendaTecnicoVO;
import br.com.fcs.agendatecnico.vo.RequestOcsJsonVO;
import br.com.fcs.agendatecnico.vo.TransactionHistoryVO;
import br.com.fcs.agendatecnico.xml.BuildXmlObject;
import br.com.fcs.agendatecnico.xml.CapturaCamposXMLResponseOCS;
import io.micrometer.core.instrument.util.StringUtils;

@Service
public class RequestAgendaTecnicoBusiness {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAgendaTecnicoBusiness.class);
    
    @Value("${apigee.credentials.bffkey}")
    private String apigeeCredentialsBffkey;

    @Value("${apigee.transactionHistory.address}")
    private String transactionHistoryAddress;

    @Value("${apigee.customerBalanceAndDate.address}")
    private String httpAddressOcs;   

    @Value("${apigee.customerBalanceAndDate.typeCallOCS}")
    private String typeCallOCS; 
  
	@Autowired
    private RequestAgendaTecnicoService requestCustomerBalanceService;
    
    @Autowired
    private CapturaCamposXMLResponseOCS capturaCamposXMLResponseOCS;
    
    @Autowired
    private TokenAccessBusiness tokenAccessBusiness;
    
    private String tokenAccess;
    
    private OneAgentSDK oneAgentSdk = OneAgentSDKFactory.createInstance();
    
    public ApiResponse processCustomerBalance(RequestAgendaTecnicoVO requestCustomerBalanceVO, String bffKey) {
		        
        LOGGER.info("[REQUEST - {}] - Processando request. ", requestCustomerBalanceVO.getMsisdn());

        ApiResponse apiResponse = fieldValidation(requestCustomerBalanceVO, bffKey);
        
        if (apiResponse == null) {

        	try {
        	    
        	    tokenAccess = tokenAccessBusiness.getTokenAccess();
        	    
        	    oneAgentSdk.addCustomRequestAttribute("customer-balance-tokenAccess", tokenAccess);
        	    oneAgentSdk.addCustomRequestAttribute("customer-balance-transactionHistoryAddress", transactionHistoryAddress);
        	    oneAgentSdk.addCustomRequestAttribute("customer-balance-httpAddressOcs", httpAddressOcs);
        	    
        	    if(tokenAccess != null) {
        	        
        	        if(typeCallOCS == null || 
                       (!typeCallOCS.equals(OCSTypeCall.XML.getDesc()) && 
                        !typeCallOCS.equals(OCSTypeCall.JSON.getDesc()))) {
                             
                        apiResponse = new ApiResponse(ErrorType.HTTP_RESPONSE_FAIL, HttpStatus.ACCEPTED.value());
                    
        	        } else if (typeCallOCS.equals(OCSTypeCall.JSON.getDesc())) {
                    
                        apiResponse = requestCustomerBalanceOCSJson(requestCustomerBalanceVO);
                    
                    } else if (typeCallOCS.equals(OCSTypeCall.XML.getDesc())) {
                    
                        apiResponse = requestCustomerBalanceOCSXML(requestCustomerBalanceVO);

                    }
        	    }
        	    
            	if (apiResponse != null && apiResponse.getCode() == SuccessMessage.TRANSACTION_SUCCESS.getCode()) {
            	
                	sendTransaction(requestCustomerBalanceVO);
            	}
            	
			} catch (Exception ex) {
			    LOGGER.info("[PROCESSCUSTOMERBALANCE] - Exeption :{}", ex.toString());
			    apiResponse = new ApiResponse(ErrorType.HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED, HttpStatus.ACCEPTED.value());
			}  
		}            

		return apiResponse;
	}
    
    public ApiResponse fieldValidation(RequestAgendaTecnicoVO requestCustomerBalanceVO, String bffKey) {
      
        oneAgentSdk.addCustomRequestAttribute("customer-balance-apigeeCredentialsBffkey", apigeeCredentialsBffkey);
        
        if(StringUtils.isBlank(bffKey) ) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if(!bffKey.equals(apigeeCredentialsBffkey)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_BFFKEY_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (StringUtils.isBlank(requestCustomerBalanceVO.getMsisdn()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getTimestamp()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getTransactionID()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getTransactionCode()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getTransactionType()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getAmount()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getOriginNodeType()) ||
            StringUtils.isBlank(requestCustomerBalanceVO.getOriginHostName())) {

            return new ApiResponse(ErrorType.FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND, HttpStatus.ACCEPTED.value());
        }
        
        ApiResponse apiResponse = verifyStringRange(requestCustomerBalanceVO);
        
        if (apiResponse == null) {
            apiResponse = verifyOtherRules(requestCustomerBalanceVO);
        }

        return apiResponse;
    }
    
    private ApiResponse verifyStringRange(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
        
        if (Utils.stringLengthDiffOfRange(requestCustomerBalanceVO.getMsisdn().length(), Constants.DEFAULT_MSISDN_LENGTH)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.stringLengthOutOfRange(requestCustomerBalanceVO.getTransactionCode().length(), Constants.DEFAULT_TRANSACTION_CODE_LENGTH)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONCODE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.stringLengthOutOfRange(requestCustomerBalanceVO.getTransactionID().length(),Constants.DEFAULT_TRANSACTION_ID_LENGTH)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.stringLengthOutOfRange(requestCustomerBalanceVO.getTransactionType().length(), Constants.DEFAULT_TRANSACTION_TYPE_LENGTH)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONTYPE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.stringLengthOutOfRange(requestCustomerBalanceVO.getOriginNodeType().length(), Constants.DEFAULT_ORIGIN_NODE_TYPE_LENGTH)){
            return new ApiResponse(ErrorType.FIELD_VALIDATION_ORIGIN_NODE_TYPE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.stringLengthOutOfRange(requestCustomerBalanceVO.getOriginHostName().length(), Constants.DEFAULT_ORIGIN_HOST_NAME_LENGTH)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_ORIGIN_HOSTNAME_INVALID, HttpStatus.ACCEPTED.value());
        }

        return null;
    }   
    
    private ApiResponse verifyOtherRules(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
        
        if (!Utils.hasOnlyNumbers(requestCustomerBalanceVO.getMsisdn())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_MSISDN_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (Utils.invalidTimestampFormat(requestCustomerBalanceVO.getTimestamp())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TIMESTAMP_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (!TransactionCodeEnum.contains(requestCustomerBalanceVO.getTransactionCode())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONCODE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (!Utils.hasOnlyNumbers(requestCustomerBalanceVO.getTransactionID())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_TRANSACTIONID_INVALID, HttpStatus.ACCEPTED.value());
        }

        if (!Utils.hasOnlyNumbers(requestCustomerBalanceVO.getAmount()) ||
            Utils.fieldOutOfRange(requestCustomerBalanceVO.getAmount())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_AMOUNT_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if (!OriginNodeTypeEnum.contains(requestCustomerBalanceVO.getOriginNodeType())) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_ORIGIN_NODE_TYPE_INVALID, HttpStatus.ACCEPTED.value());
        }
        
        if ((requestCustomerBalanceVO.getTransactionCode().equals(TransactionCodeEnum.DEBIT.getDesc()) &&
             Long.parseLong(requestCustomerBalanceVO.getAmount()) > 0) ||
            (requestCustomerBalanceVO.getTransactionCode().equals(TransactionCodeEnum.ADJ.getDesc()) &&
             Long.parseLong(requestCustomerBalanceVO.getAmount()) < 0)) {
            return new ApiResponse(ErrorType.FIELD_VALIDATION_AMOUNT_INVALID, HttpStatus.ACCEPTED.value());
        }
        return null;
    }
    
    public ApiResponse requestCustomerBalanceOCSJson(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
        ApiResponse apiResponseOCS = null;
        String httpResponseBody = "";
        ApiResponse apiResponse = null;
        
        RequestOcsJsonVO ocsCustomerBalanceVO = new RequestOcsJsonVO(requestCustomerBalanceVO.getMsisdn(),
                                                                     requestCustomerBalanceVO.getTimestamp(),
                                                                     requestCustomerBalanceVO.getTransactionID(),
                                                                     requestCustomerBalanceVO.getAmount(),
                                                                     requestCustomerBalanceVO.getOriginNodeType(),
                                                                     requestCustomerBalanceVO.getOriginHostName(),
                                                                     "220",
                                                                     "805646916",
                                                                     "1576");
        
        LOGGER.info("[OCS] - APIgee: JSON {}", ocsCustomerBalanceVO);
        
        try {
            httpResponseBody = requestCustomerBalanceService.envioPostRequest(ocsCustomerBalanceVO.toString(),
                                                                              httpAddressOcs, 
                                                                              MediaType.APPLICATION_JSON_VALUE, 
                                                                              tokenAccess);
            
            apiResponse = JsonUtils.getGson().fromJson(httpResponseBody, ApiResponse.class);
            
            LOGGER.info("[requestCustomerBalanceOCSJson - {} ] - responseCode = {} ", apiResponse.getTransactionID(), requestCustomerBalanceService.getResponseCode());
            LOGGER.info("[requestCustomerBalanceOCSJson] - Message = {} ", apiResponse.getMessage());
            LOGGER.info("[requestCustomerBalanceOCSJson] - MessageDetail = {} ", apiResponse.getMessageDetail());
            
            if (requestCustomerBalanceService.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                LOGGER.info("[OCS] - Sucesso");
                
                apiResponseOCS = new ApiResponse(SuccessMessage.TRANSACTION_SUCCESS,
                                                 apiResponse.getAmount(),
                                                 requestCustomerBalanceVO.getTransactionID(),
                                                 requestCustomerBalanceService.getResponseCode());
            } else {
                
                if(apiResponse.getError() != null && 
                   apiResponse.getError().getHttpCode() != null) {
                
                    LOGGER.info("[OCS] - Erro: {}", apiResponse);
                    
                    if(apiResponse.getCode() == null) {
                        apiResponse.setCode(Integer.parseInt(apiResponse.getError().getHttpCode()));
                    }
                     
                    apiResponseOCS = new ApiResponse(apiResponse.getCode(),
                                                     apiResponse.getError().getDetailedMessage(),
                                                     apiResponse.getTransactionID(),
                                                     requestCustomerBalanceService.getResponseCode());    
                }else {

                    LOGGER.info("[OCS] - Erro Padrao");
                    
                    apiResponseOCS = new ApiResponse(ErrorType.HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED,
                                                     requestCustomerBalanceService.getResponseCode());
                }
            }
        }catch (Exception exception) {        
            LOGGER.info("START_OCS_Exception_FAILED"
                    + " HTTPRESPONSEBODY = {}"
                    + " APIRESPONSE = {}"
                    + " EXCEPTION = {} "
                    + " END_OCS_Exception_FAILED", httpResponseBody, apiResponse, exception.getMessage());
            
            oneAgentSdk.addCustomRequestAttribute("customer-balance-httpResponseBody", httpResponseBody);
            oneAgentSdk.addCustomRequestAttribute("customer-balance-apiResponse", apiResponse != null ? apiResponse.toString() : "");
            oneAgentSdk.addCustomRequestAttribute("customer-balance-exception", exception.getMessage());
            
            apiResponseOCS = new ApiResponse(ErrorType.HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED,
                                             requestCustomerBalanceService.getResponseCode());
        }
        
        return apiResponseOCS;
    }
    
	public void sendTransaction(RequestAgendaTecnicoVO requestCustomerBalanceVO) {

		LOGGER.info("[SENDTRANSACTION] - INSERT INTO TRANSACTION HISTORY.");

		TransactionHistoryVO transactionHistoryVO = new TransactionHistoryVO(requestCustomerBalanceVO.getTimestamp(), 
																			 requestCustomerBalanceVO.getMsisdn(),
																			 requestCustomerBalanceVO.getTransactionType(), 
																			 requestCustomerBalanceVO.getAmount(),
																			 requestCustomerBalanceVO.getTransactionCode(), 
																			 requestCustomerBalanceVO.getTransactionID());
		try {
			
		    String response = requestCustomerBalanceService.envioPostRequest(transactionHistoryVO.toString(), 
																			 transactionHistoryAddress,
																			 MediaType.APPLICATION_JSON_VALUE, 
																			 tokenAccess);
		    
		    ApiResponse apiResponse = JsonUtils.getGson().fromJson(response, ApiResponse.class);

		    LOGGER.info("[SENDTRANSACTION - {} ] - responseCode {} ",transactionHistoryVO.getTransactionID(), requestCustomerBalanceService.getResponseCode());
		    LOGGER.info("[SENDTRANSACTION] - Message = {} ", apiResponse.getMessage());
		    LOGGER.info("[SENDTRANSACTION] - MessageDetail = {} ", apiResponse.getMessageDetail()); 
		    
			if (requestCustomerBalanceService.getResponseCode() != HttpURLConnection.HTTP_OK) {
			               
			    requestCustomerBalanceService.envioPubSub(transactionHistoryVO);
	                
			}
			
		} catch (Exception ex) {
			LOGGER.info("[EXCEPTION - {}] - Falha ao Inserir no historico de transações.", ex.getMessage());
			requestCustomerBalanceService.envioPubSub(transactionHistoryVO);
		}
	}
	
	public ApiResponse requestCustomerBalanceOCSXML(RequestAgendaTecnicoVO requestCustomerBalanceVO) {
        ApiResponse apiResponseOCS = null;
        String valorDedicatedAccountID = null;
        
        DedicatedAccountsVO dedicatedAccountsVO = new DedicatedAccountsVO("220", requestCustomerBalanceVO.getAmount(), "1");
        requestCustomerBalanceVO.setDedicatedAccountUpdateInformation(dedicatedAccountsVO);
        
        Map<String, String[]> updateBalanceAndDateFields = new HashMap<>();
        updateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_NODE_TYPE.getDesc() , new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getOriginNodeType()});
        updateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_HOST_NAME.getDesc() , new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getOriginHostName()});
        updateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_TRANSACTION_ID.getDesc(), new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getTransactionID()});
        updateBalanceAndDateFields.put(XmlTagNameEnum.ORIGIN_TIMESTAMP.getDesc() , new String[] {OCSDataType.ISO_8601.getDesc(), requestCustomerBalanceVO.getTimestamp()});
        updateBalanceAndDateFields.put(XmlTagNameEnum.SUBSCRIBER_NUMBER.getDesc(), new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getMsisdn()});
        
        Map<String, String[]> dedicateAccountFields = new HashMap<>();
        dedicateAccountFields.put(XmlTagNameEnum.DEDICATED_ACCOUNT_ID.getDesc(), new String[] {OCSDataType.INT.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountID()});
        dedicateAccountFields.put(XmlTagNameEnum.ADJUSTMENT_AMOUNT_RELATIVE.getDesc(), new String[] {OCSDataType.STRING.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getAccountBalance()});
        dedicateAccountFields.put(XmlTagNameEnum.DEDICATED_ACCOUNT_UNIT_TYPE.getDesc(), new String[] {OCSDataType.I4.getDesc(), requestCustomerBalanceVO.getDedicatedAccountUpdateInformation().getDedicatedAccountUnitType()});

        try {
            Object payloadBuildXML = BuildXmlObject.buildXMLObject(updateBalanceAndDateFields, dedicateAccountFields);            
            
            LOGGER.info("[OCS] - APIgee XML : {}", requestCustomerBalanceVO);

            String httpResponseBody = requestCustomerBalanceService.envioPostRequest(payloadBuildXML, 
            																		 httpAddressOcs, 
            																		 MediaType.APPLICATION_XML_VALUE, 
            																		 tokenAccess);
            capturaCamposXMLResponseOCS.capturaCamposXML(httpResponseBody);
            if(capturaCamposXMLResponseOCS.getValorDedicatedAccountID() == 220) {
            	valorDedicatedAccountID = Long.toString(capturaCamposXMLResponseOCS.getValorDedicatedAccountActiveValue1());
            } else {
            	valorDedicatedAccountID = "0";
            }
            
            if (requestCustomerBalanceService.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
                apiResponseOCS = new ApiResponse(SuccessMessage.TRANSACTION_SUCCESS,
                                                 valorDedicatedAccountID,
                                                 requestCustomerBalanceVO.getTransactionID(),
                                                 requestCustomerBalanceService.getResponseCode());
            } else {
                apiResponseOCS = new ApiResponse(ErrorType.HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED,
                                                 requestCustomerBalanceService.getResponseCode());
            }
            
        }catch (Exception exception) {        
            LOGGER.info("[OCS] - Exception : FAILED = {} ", exception.getMessage());
            apiResponseOCS = new ApiResponse(ErrorType.HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED,
                                             requestCustomerBalanceService.getResponseCode());
        }
        
        return apiResponseOCS;
    }

    public void setRequestCustomerBalanceService(RequestAgendaTecnicoService customerBalanceService) {
        this.requestCustomerBalanceService = customerBalanceService;
    }

    public void setCapturaCamposXMLResponseOCS(CapturaCamposXMLResponseOCS capturaCamposXMLResponseOCS) {
        this.capturaCamposXMLResponseOCS = capturaCamposXMLResponseOCS;
    }

	public String getTransactionHistoryAddress() {
		return transactionHistoryAddress;
	}

	public void setTransactionHistoryAddress(String transactionHistoryAddress) {
		this.transactionHistoryAddress = transactionHistoryAddress;
	}

	public String getHttpAddressOcs() {
		return httpAddressOcs;
	}

	public void setHttpAddressOcs(String httpAddressOcs) {
		this.httpAddressOcs = httpAddressOcs;
	}

	public String getTypeCallOCS() {
		return typeCallOCS;
	}

	public void setTypeCallOCS(String typeCallOCS) {
		this.typeCallOCS = typeCallOCS;
	}

	public RequestAgendaTecnicoService getRequestCustomerBalanceService() {
		return requestCustomerBalanceService;
	}
	
    public String getApigeeCredentialsBffkey() {
        return apigeeCredentialsBffkey;
    }

    public void setApigeeCredentialsBffkey(String apigeeCredentialsBffkey) {
        this.apigeeCredentialsBffkey = apigeeCredentialsBffkey;
    }

    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public TokenAccessBusiness getTokenAccessBusiness() {
        return tokenAccessBusiness;
    }

    public void setTokenAccessBusiness(TokenAccessBusiness tokenAccessBusiness) {
        this.tokenAccessBusiness = tokenAccessBusiness;
    }
    
}