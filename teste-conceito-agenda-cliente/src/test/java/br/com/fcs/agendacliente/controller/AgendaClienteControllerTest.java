package br.com.fcs.agendacliente.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import br.com.fcs.agendacliente.business.AgendaClienteBusiness;
import br.com.fcs.agendacliente.controller.AgendaClienteController;
import br.com.fcs.agendacliente.dto.AgendaClienteSolicitationDTO;
import br.com.fcs.agendacliente.model.AgendaClienteModel;
import br.com.fcs.agendacliente.repository.AgendaClienteRepository;
import br.com.fcs.agendacliente.repository.ContactRepository;
import br.com.fcs.agendacliente.response.ApiResponse;
import br.com.fcs.agendacliente.response.AgendaClienteApiResponse;
import br.com.fcs.agendacliente.service.AgendaClienteService;

@RunWith(MockitoJUnitRunner.class)
class AgendaClienteControllerTest {

	public AgendaClienteController agendaClienteController;
	
	@Mock
	public AgendaClienteBusiness agendaClienteBusinessMock;

	@Mock
	private AgendaClienteService agendaClienteServiceMock;

	@Mock
	private AgendaClienteRepository transactionHistoryRepositoryMock;

	@Mock
	private ContactRepository contactRepositoryMock;
	
	private AgendaClienteModel transactionHistoryModel;

	private AgendaClienteSolicitationDTO agendaClienteSolicitationDTO;
	
	private ResponseEntity<ApiResponse> apiResponseTest;
	
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    
    private ResponseEntity<AgendaClienteApiResponse> apiResponse;
    
	@BeforeEach
	public void setup() {
		initMocks(this);
		final String msisdn = "5590988205339";
		final String startDate = "20191128";
		final String endDate = "20191129";
		final String numRecord = "2";
		final String numPage = "10";
		
		final String amount = "123456";
		final String description = "teste";
		final String id = "AasDAsSaAaaSaQqLAaa2";
	    final String timeStamp = "20191128T14:00:17-0300";
	    final String transactionID = "123456";
	    final String type = "refil";

	    agendaClienteBusinessMock = new AgendaClienteBusiness();
		transactionHistoryModel = new AgendaClienteModel();
		agendaClienteServiceMock = new AgendaClienteService();
		agendaClienteController = new AgendaClienteController(contactRepositoryMock);

		agendaClienteServiceMock.setTransactionHistoryRepository(transactionHistoryRepositoryMock);
		agendaClienteBusinessMock.setTransactionHistoryService(agendaClienteServiceMock);
		agendaClienteBusinessMock.setApigeeCredentialsBffkey("123456dev");
		agendaClienteController.setAgendaClienteBusiness(agendaClienteBusinessMock);
		
	    agendaClienteSolicitationDTO = new AgendaClienteSolicitationDTO();
	    agendaClienteSolicitationDTO.setStartDate(startDate);
	    agendaClienteSolicitationDTO.setEndDate(endDate);
	    agendaClienteSolicitationDTO.setNumPage(numPage);
	    agendaClienteSolicitationDTO.setNumRecord(numRecord);
	    
	    transactionHistoryModel = new AgendaClienteModel();
	    /*
	    transactionHistoryModel.setAmount(amount);
	    transactionHistoryModel.setDescription(description);
	    transactionHistoryModel.setId(id);
	    transactionHistoryModel.setMsisdn(msisdn);
	    transactionHistoryModel.setTimestamp(timeStamp);
	    transactionHistoryModel.setTransactionID(transactionID);
	    transactionHistoryModel.setType(type);
	    /**/
	    
        request.addHeader("x-application-key", "123456dev");
        response.addHeader("x-application-key", "123456dev");
        
	}

	@Test
	void contexLoadsNotNull() throws Exception {
		assertThat(agendaClienteController).isNotNull();
	}
	
	@Test
    void transactionHistoryHealthyOK() {
	    
	    apiResponseTest = agendaClienteController.transactionHistoryHealthy(request, response); 
        
        assertEquals(HttpStatus.OK, apiResponseTest.getStatusCode());
    }
/*
	@Test
	void transactionHistoryFindParametroOK() throws Exception {
		apiResponse = transactionHistoryController.agendaClienteFindParametro(//transactionHistorySolicitationDTO.getMsisdn(),
												                                   agendaClienteSolicitationDTO.getStartDate(),
												                                   agendaClienteSolicitationDTO.getEndDate(),
												                                   agendaClienteSolicitationDTO.getNumRecord(),
												                                   agendaClienteSolicitationDTO.getNumPage(),
												                                   request,
												                                   response);	
		assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
	}
	/*
	@Test
    void transactionHistoryFindParametroACCEPTED() throws Exception {
	    
	    transactionHistorySolicitationDTO.setMsisdn("55XXX");
        
        apiResponse = transactionHistoryController.transactionHistoryFindParametro(transactionHistorySolicitationDTO.getMsisdn(),
                                                                                   transactionHistorySolicitationDTO.getStartDate(),
                                                                                   transactionHistorySolicitationDTO.getEndDate(),
                                                                                   transactionHistorySolicitationDTO.getNumRecord(),
                                                                                   transactionHistorySolicitationDTO.getNumPage(),
                                                                                   request,
                                                                                   response);   
        assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
    }
	
	@Test
    void transactionHistoryFindParametroBffKeyNullACCEPTED() throws Exception {
        
        transactionHistorySolicitationDTO.setMsisdn("55XXX");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        apiResponse = transactionHistoryController.transactionHistoryFindParametro(transactionHistorySolicitationDTO.getMsisdn(),
                                                                                   transactionHistorySolicitationDTO.getStartDate(),
                                                                                   transactionHistorySolicitationDTO.getEndDate(),
                                                                                   transactionHistorySolicitationDTO.getNumRecord(),
                                                                                   transactionHistorySolicitationDTO.getNumPage(),
                                                                                   request,
                                                                                   response);   
        assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
    }
	
	@Test
	void transactionHistoryFindOK() throws Exception {
		
	    apiResponse = transactionHistoryController.transactionHistoryFind(transactionHistorySolicitationDTO.getMsisdn(),
												                     transactionHistorySolicitationDTO.getStartDate(),
												                     transactionHistorySolicitationDTO.getEndDate(),
												                     transactionHistorySolicitationDTO.getNumRecord(),
												                     transactionHistorySolicitationDTO.getNumPage(),
												                     request,
												                     response);	
		assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
	}
	
	@Test
	void transactionHistoryFindACCEPTED() throws Exception {
		
	    transactionHistorySolicitationDTO.setMsisdn("55XXX");
	    
	    apiResponse = transactionHistoryController.transactionHistoryFind(transactionHistorySolicitationDTO.getMsisdn(),
												                          transactionHistorySolicitationDTO.getStartDate(),
												                          transactionHistorySolicitationDTO.getEndDate(),
												                          transactionHistorySolicitationDTO.getNumRecord(),
												                          transactionHistorySolicitationDTO.getNumPage(),
												                          request,
												                          response);	
	    assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
	}	
	
	@Test
    void transactionHistoryFindBffKeyNullACCEPTED() throws Exception {
        
        transactionHistorySolicitationDTO.setMsisdn("55XXX");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        apiResponse = transactionHistoryController.transactionHistoryFind(transactionHistorySolicitationDTO.getMsisdn(),
                                                                          transactionHistorySolicitationDTO.getStartDate(),
                                                                          transactionHistorySolicitationDTO.getEndDate(),
                                                                          transactionHistorySolicitationDTO.getNumRecord(),
                                                                          transactionHistorySolicitationDTO.getNumPage(),
                                                                          request,
                                                                          response);    
        assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
    }   
	/**
	@Test
	void transactionHistorySaveOK() throws Exception {
	    
	    apiResponse = transactionHistoryController.transactionHistorySave(transactionHistoryModel, request, response);	
	    assertEquals(HttpStatus.OK, apiResponse.getStatusCode());
	}
	/*
	@Test
	void shouldtransactionHistorySaveACCEPTED() throws Exception {
	    
	    transactionHistoryModel.setMsisdn("55XXX");
	    
	    apiResponse = transactionHistoryController.transactionHistorySave(transactionHistoryModel, request, response);	
		assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
	}	
	
	@Test
    void shouldtransactionHistorySaveBffKeyNullACCEPTED() throws Exception {
        
        transactionHistoryModel.setMsisdn("55XXX");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        apiResponse = transactionHistoryController.transactionHistorySave(transactionHistoryModel, request, response);  
        assertEquals(HttpStatus.ACCEPTED, apiResponse.getStatusCode());
    }   
	/**/
}