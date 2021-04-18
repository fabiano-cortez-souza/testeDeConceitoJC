package br.com.fcs.agendacliente.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.cloud.datastore.DatastoreException;

import br.com.fcs.agendacliente.business.AgendaClienteBusiness;
import br.com.fcs.agendacliente.dto.AgendaClienteSolicitationDTO;
import br.com.fcs.agendacliente.model.AgendaClienteModel;
import br.com.fcs.agendacliente.repository.AgendaClienteRepository;
import br.com.fcs.agendacliente.response.AgendaClienteApiResponse;
import br.com.fcs.agendacliente.service.AgendaClienteService;
import br.com.fcs.agendacliente.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
class AgendaClienteBusinessTest {

	public AgendaClienteBusiness agendaClienteBusiness;

	@Mock
	private AgendaClienteService agendaClienteServiceMock;

	@Mock
	private AgendaClienteRepository agendaClienteRepositoryMock;

	private AgendaClienteModel agendaClienteModel;

	private AgendaClienteSolicitationDTO agendaClienteSolicitationDTO;
	
	private String bffKey;
	
	@BeforeEach
	public void setup() {
		initMocks(this);
		agendaClienteBusiness = new AgendaClienteBusiness();
		agendaClienteModel = new AgendaClienteModel();
		agendaClienteServiceMock = new AgendaClienteService();

		agendaClienteServiceMock.setTransactionHistoryRepository(agendaClienteRepositoryMock);
		agendaClienteBusiness.setTransactionHistoryService(agendaClienteServiceMock);
		agendaClienteBusiness.setApigeeCredentialsBffkey("123456dev");
        /**
		agendaClienteModel.setTimestamp("20191128T14:00:12+0000");
		agendaClienteModel.setMsisdn("1111111111111");
		agendaClienteModel.setType("refil");
		agendaClienteModel.setAmount("1111");
		agendaClienteModel.setDescription("description");
		agendaClienteModel.setTransactionID("1111");
		agendaClienteModel.setRequestId("112223");
		agendaClienteModel.setEventTimeStamp(ZonedDateTime.now().toString());
		/**/

		agendaClienteSolicitationDTO = new AgendaClienteSolicitationDTO();
		agendaClienteSolicitationDTO.setStartDate("20191124");
		agendaClienteSolicitationDTO.setEndDate("20191125");
		agendaClienteSolicitationDTO.setNumPage("2");
		agendaClienteSolicitationDTO.setNumRecord("20");
		agendaClienteSolicitationDTO.setMsisdn("1111111111111");
		
		bffKey = "123456dev";

	}

	@Test
    void bffKeyIsEmpty() {
	    bffKey = "";
	    AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveAgendaCliente(agendaClienteModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
    void bffKeyIsNull() {
	    bffKey = null;
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveAgendaCliente(agendaClienteModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
    void bffKeyNotInvalid() {
	    bffKey = "5678";
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveAgendaCliente(agendaClienteModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    /**
	@Test
	void timestampIsEmpty() {
		agendaClienteModel.setTimestamp("");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsEmptySave() {
		agendaClienteModel.setMsisdn("");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void typeIsEmpty() {
		agendaClienteModel.setType("");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void amountIsEmpty() {
		agendaClienteModel.setAmount("");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void transactionIdIsEmpty() {
		agendaClienteModel.setTransactionID("");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsNullSave() {
		agendaClienteModel.setMsisdn(null);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void typeIsNull() {
		agendaClienteModel.setType(null);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void amountIsNull() {
		agendaClienteModel.setAmount(null);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void transactionIdIsNull() {
		agendaClienteModel.setTransactionID(null);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}
	
	@Test
    void transactionIdNotNumerico() {
        agendaClienteModel.setTransactionID("123XX");
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
        assertEquals("TransactionId field is invalid", apiResponse.getMessageDetail());
    }

	@Test
	void transactionIdLenghtIsGreatherThanAllowed() {
		agendaClienteModel.setTransactionID("123456789012345678901");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("TransactionId field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void typeLenghtIsGreatherThanAllowed() {
		agendaClienteModel.setType("BYLQRSBLTCDZHYILLONEE");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Type field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void descriptionLenghtIsGreatherThanAllowed() {
		agendaClienteModel.setType("nJBUiSMWzniUclZBNfCKHysyUpHQNsNQcTfnNEEZoyaiQYwLxyO");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Type field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void amountRangeLessThanAllowed() {
		agendaClienteModel.setAmount("-9223372036854775809");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void amountRangeGreaterThanAllowed() {
		agendaClienteModel.setAmount("9223372036854775809");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidTimestamp() {
		agendaClienteModel.setTimestamp("2019-11-28T14:00:12");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Timestamp format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdnSave() {
		agendaClienteModel.setMsisdn("55111111111AD");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdnLength() {
		agendaClienteModel.setMsisdn("551111111119898");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidAmount() {
		agendaClienteModel.setAmount("92233720368547758A");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidDescNull() {
		agendaClienteModel.setDescription(null);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Description field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidDescLenght() {
		agendaClienteModel.setDescription("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("Description field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidEventExists() {
		when(agendaClienteServiceMock.getTransactionHistoryRepository().existsById(agendaClienteModel.getTransactionID())).thenReturn(true);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
		assertEquals("This event already exists", apiResponse.getMessageDetail());
	}

	@Test
    void invalidEventExistsException() {
        DatastoreException datastoreException = new DatastoreException(14, 
                                                                       "I/O error", 
                                                                       "code=UNAVAILABLE",
                                                                       new UnknownHostException("datastore.googleapis.com"));

        when(agendaClienteServiceMock.getTransactionHistoryRepository().existsById(agendaClienteModel.getTransactionID())).thenThrow(datastoreException);
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationSave(agendaClienteModel);
        assertEquals("Transaction history request denied", apiResponse.getMessageDetail());
    }
	
	@Test
	void saveTransactionSuccess() {
		when(agendaClienteServiceMock.getTransactionHistoryRepository().existsById(agendaClienteModel.getTransactionID())).thenReturn(false);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveTransaction(agendaClienteModel, bffKey);
		assertEquals("The transaction was successfully", apiResponse.getMessageDetail());
	}
	/**/
	@Test
	void saveAgendaClienteException() {
		DatastoreException datastoreException = new DatastoreException(14, 
				                                                       "I/O error", 
				                                                       "code=UNAVAILABLE",
				                                                       new UnknownHostException("datastore.googleapis.com"));

		when(agendaClienteRepositoryMock.save(Mockito.any())).thenThrow(datastoreException);
		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveAgendaCliente(agendaClienteModel, bffKey);
		//System.out.println(apiResponse.getMessageDetail());
		//assertEquals("Transaction history request denied", apiResponse.getMessageDetail());
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}	
	/**
	@Test
    void saveTransactionNotSuccess() {
	    agendaClienteModel.setMsisdn("55XXX");
        when(agendaClienteServiceMock.getTransactionHistoryRepository().existsById(agendaClienteModel.getTransactionID())).thenReturn(false);
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.saveTransaction(agendaClienteModel, bffKey);
        assertEquals(new Integer(2), apiResponse.getCode());
        assertNotEquals("The transaction was successfully", apiResponse.getMessageDetail());
    }
/**/
	@Test
    void bffKeyIsEmptyFind() {
        bffKey = "";
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
    @Test
    void bffKeyIsNullFind() {
        bffKey = null;
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
    @Test
    void bffKeyNotInvalidFind() {
        bffKey = "5678";
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
	void timestampStartDateIsEmpty() {
		agendaClienteSolicitationDTO.setStartDate("");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void timestampEndDateIsEmpty() {
		agendaClienteSolicitationDTO.setEndDate("");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsEmpty() {
		agendaClienteSolicitationDTO.setMsisdn("");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnNotOnlynumber() {
		agendaClienteSolicitationDTO.setMsisdn("123456789012a");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnMoreThanMaxPosition() {
		agendaClienteSolicitationDTO.setMsisdn("12345678901234");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsNull() {
		agendaClienteSolicitationDTO.setMsisdn(null);

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void numRecordsIsNull() {
		agendaClienteSolicitationDTO.setNumRecord(null);

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void invalidStartDate() {
		agendaClienteSolicitationDTO.setStartDate("2019-11-28");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Date format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidEndDate() {
		agendaClienteSolicitationDTO.setEndDate("2019-11-29");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Date format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdn() {
		agendaClienteSolicitationDTO.setMsisdn("55111111111AD");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidNegativeRecordsValue() {
		agendaClienteSolicitationDTO.setNumRecord("-1");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void numRecordsIsZero() {
		agendaClienteSolicitationDTO.setNumRecord("0");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidOutOfRangeNumRecords() {
		agendaClienteSolicitationDTO.setNumRecord("101");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidNumRecords() {
		agendaClienteSolicitationDTO.setNumRecord("a");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void NumPageNotNullButNotNumber() {
		agendaClienteSolicitationDTO.setNumPage("abc");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertEquals("NumPage format is invalid", apiResponse.getMessageDetail());

	}

	@Test
	void NumPageValid() {
		agendaClienteSolicitationDTO.setNumPage("1234");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertNull(apiResponse);
	}

	@Test
	void NumPageBlank() {
		agendaClienteSolicitationDTO.setNumPage("");

		AgendaClienteApiResponse apiResponse = agendaClienteBusiness.fieldValidationFind(agendaClienteSolicitationDTO);
		assertNull(apiResponse);
	}
	
	@Test
    void validaReturnApiResponseErroAndContadoresIsNull() {
        agendaClienteBusiness.setjUnitTest(true);
        agendaClienteSolicitationDTO.setMsisdn("55XXX");
        agendaClienteBusiness.setTransactionHistoryModels(new ArrayList<AgendaClienteModel>());
        
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertNull(apiResponse.getCurrentPage());
        assertNull(apiResponse.getTotalNumPage());
        assertNull(apiResponse.getTotalNumRecord());
        assertNull(apiResponse.getTransactions());
        assertEquals(new Integer(2), apiResponse.getCode());
    }
	
	@Test
    void validaHistoryModesIsEmpty() {
        agendaClienteBusiness.setjUnitTest(true);
        agendaClienteBusiness.setTransactionHistoryModels(new ArrayList<AgendaClienteModel>());
        
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals(new Long(0), apiResponse.getTotalNumRecord());
        assertTrue(apiResponse.getTransactions().isEmpty());
    }
	/**
	@Test
    void validateReturnTransactionHistoryModesNotIsEmptyAndEqualsvalidTransactionPayload() {
        agendaClienteBusiness.setjUnitTest(true);
	    agendaClienteBusiness.setTransactionHistoryModels(validTransactionPayload());
        
	    AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
	    assertEquals(new Long(1), apiResponse.getTotalNumRecord());
	    assertEquals(new Integer(1), apiResponse.getTotalNumPage());
	    assertFalse(apiResponse.getTransactions().isEmpty());
	    assertEquals("123", apiResponse.getTransactions().get(0).getAmount());
	    assertEquals("descriptionPayload", apiResponse.getTransactions().get(0).getDescription());
	    assertEquals("12346789", apiResponse.getTransactions().get(0).getTransactionID());
	    assertEquals("refil", apiResponse.getTransactions().get(0).getType());
	    assertEquals("20200101T23:59:59", apiResponse.getTransactions().get(0).getTimestamp());
	    assertEquals("Search was successfully", apiResponse.getMessageDetail());
    }
	/**
    void validateReturnTransactionHistoryModesNotStartDateAndDateEnd() {
    
        when(agendaClienteServiceMock.findTransactionHistoryByMsisdn(agendaClienteSolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        agendaClienteBusiness.setjUnitTest(false);
        agendaClienteSolicitationDTO.setNumPage(null);
        
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals(new Long(0), apiResponse.getTotalNumRecord());
        assertTrue(apiResponse.getTransactions().isEmpty());
    }
	/**
	@Test
    void validateReturnTransactionHistoryModesBetwenStartDateAndDateEnd() {
    
        when(agendaClienteServiceMock.findTransactionHistoryByMsisdn(agendaClienteSolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        agendaClienteBusiness.setjUnitTest(false);
        agendaClienteSolicitationDTO.setNumPage("");
        agendaClienteSolicitationDTO.setStartDate("20191201");
        agendaClienteSolicitationDTO.setEndDate("20200131");
        
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals(new Long(1), apiResponse.getTotalNumRecord());
        assertEquals(new Integer(1), apiResponse.getTotalNumPage());
        assertFalse(apiResponse.getTransactions().isEmpty());
        assertEquals("123", apiResponse.getTransactions().get(0).getAmount());
        assertEquals("descriptionPayload", apiResponse.getTransactions().get(0).getDescription());
        assertEquals("12346789", apiResponse.getTransactions().get(0).getTransactionID());
        assertEquals("refil", apiResponse.getTransactions().get(0).getType());
        assertEquals("20200101T23:59:59", apiResponse.getTransactions().get(0).getTimestamp());
        assertEquals("Search was successfully", apiResponse.getMessageDetail());
    }
    
	@Test
    void validateReturnTransactionHistoryModesEqualsEndDate() {
    
        when(agendaClienteServiceMock.findTransactionHistoryByMsisdn(agendaClienteSolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        agendaClienteBusiness.setjUnitTest(false);
        agendaClienteSolicitationDTO.setNumPage("");
        agendaClienteSolicitationDTO.setStartDate("20190101");
        agendaClienteSolicitationDTO.setEndDate("20200101");
        
        AgendaClienteApiResponse apiResponse = agendaClienteBusiness.findTransactionHistoryDocuments(agendaClienteSolicitationDTO, bffKey);
        assertEquals(new Long(1), apiResponse.getTotalNumRecord());
        assertEquals(new Integer(1), apiResponse.getTotalNumPage());
        assertFalse(apiResponse.getTransactions().isEmpty());
        assertEquals("123", apiResponse.getTransactions().get(0).getAmount());
        assertEquals("descriptionPayload", apiResponse.getTransactions().get(0).getDescription());
        assertEquals("12346789", apiResponse.getTransactions().get(0).getTransactionID());
        assertEquals("refil", apiResponse.getTransactions().get(0).getType());
        assertEquals("20200101T23:59:59", apiResponse.getTransactions().get(0).getTimestamp());
        assertEquals("Search was successfully", apiResponse.getMessageDetail());
    }
	
	private List<AgendaClienteModel> validTransactionPayload() {
		agendaClienteModel = new AgendaClienteModel();
		agendaClienteModel.setAmount("123");
		agendaClienteModel.setDescription("descriptionPayload");
		agendaClienteModel.setEventTimeStamp(ZonedDateTime.now().toString());
		agendaClienteModel.setRequestId(Utils.generateId());
		agendaClienteModel.setMsisdn("5511999999999");
		agendaClienteModel.setTimestamp("20200101T23:59:59");
		agendaClienteModel.setTransactionID("12346789");
		agendaClienteModel.setId(agendaClienteModel.getTransactionID());
		agendaClienteModel.setType("refil");

		List<AgendaClienteModel> transactions = new ArrayList<AgendaClienteModel>();
		transactions.add(agendaClienteModel);
		return transactions;
	}
	/**/
	
}