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

	public AgendaClienteBusiness transactionHistoryBusiness;

	@Mock
	private AgendaClienteService transactionHistoryServiceMock;

	@Mock
	private AgendaClienteRepository transactionHistoryRepositoryMock;

	private AgendaClienteModel transactionHistoryModel;

	private AgendaClienteSolicitationDTO transactionHistorySolicitationDTO;
	
	private String bffKey;
	
	@BeforeEach
	public void setup() {
		initMocks(this);
		transactionHistoryBusiness = new AgendaClienteBusiness();
		transactionHistoryModel = new AgendaClienteModel();
		transactionHistoryServiceMock = new AgendaClienteService();

		transactionHistoryServiceMock.setTransactionHistoryRepository(transactionHistoryRepositoryMock);
		transactionHistoryBusiness.setTransactionHistoryService(transactionHistoryServiceMock);
		transactionHistoryBusiness.setApigeeCredentialsBffkey("123456dev");

		transactionHistoryModel.setTimestamp("20191128T14:00:12+0000");
		transactionHistoryModel.setMsisdn("1111111111111");
		transactionHistoryModel.setType("refil");
		transactionHistoryModel.setAmount("1111");
		transactionHistoryModel.setDescription("description");
		transactionHistoryModel.setTransactionID("1111");
		transactionHistoryModel.setRequestId("112223");
		transactionHistoryModel.setEventTimeStamp(ZonedDateTime.now().toString());

		transactionHistorySolicitationDTO = new AgendaClienteSolicitationDTO();
		transactionHistorySolicitationDTO.setStartDate("20191124");
		transactionHistorySolicitationDTO.setEndDate("20191125");
		transactionHistorySolicitationDTO.setNumPage("2");
		transactionHistorySolicitationDTO.setNumRecord("20");
		transactionHistorySolicitationDTO.setMsisdn("1111111111111");
		
		bffKey = "123456dev";

	}

	@Test
    void bffKeyIsEmpty() {
	    bffKey = "";
	    AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
    void bffKeyIsNull() {
	    bffKey = null;
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
    void bffKeyNotInvalid() {
	    bffKey = "5678";
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
	@Test
	void timestampIsEmpty() {
		transactionHistoryModel.setTimestamp("");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsEmptySave() {
		transactionHistoryModel.setMsisdn("");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void typeIsEmpty() {
		transactionHistoryModel.setType("");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void amountIsEmpty() {
		transactionHistoryModel.setAmount("");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void transactionIdIsEmpty() {
		transactionHistoryModel.setTransactionID("");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsNullSave() {
		transactionHistoryModel.setMsisdn(null);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void typeIsNull() {
		transactionHistoryModel.setType(null);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void amountIsNull() {
		transactionHistoryModel.setAmount(null);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void transactionIdIsNull() {
		transactionHistoryModel.setTransactionID(null);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}
	
	@Test
    void transactionIdNotNumerico() {
        transactionHistoryModel.setTransactionID("123XX");
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
        assertEquals("TransactionId field is invalid", apiResponse.getMessageDetail());
    }

	@Test
	void transactionIdLenghtIsGreatherThanAllowed() {
		transactionHistoryModel.setTransactionID("123456789012345678901");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("TransactionId field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void typeLenghtIsGreatherThanAllowed() {
		transactionHistoryModel.setType("BYLQRSBLTCDZHYILLONEE");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Type field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void descriptionLenghtIsGreatherThanAllowed() {
		transactionHistoryModel.setType("nJBUiSMWzniUclZBNfCKHysyUpHQNsNQcTfnNEEZoyaiQYwLxyO");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Type field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void amountRangeLessThanAllowed() {
		transactionHistoryModel.setAmount("-9223372036854775809");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void amountRangeGreaterThanAllowed() {
		transactionHistoryModel.setAmount("9223372036854775809");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidTimestamp() {
		transactionHistoryModel.setTimestamp("2019-11-28T14:00:12");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Timestamp format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdnSave() {
		transactionHistoryModel.setMsisdn("55111111111AD");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdnLength() {
		transactionHistoryModel.setMsisdn("551111111119898");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidAmount() {
		transactionHistoryModel.setAmount("92233720368547758A");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Amount format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidDescNull() {
		transactionHistoryModel.setDescription(null);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Description field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidDescLenght() {
		transactionHistoryModel.setDescription("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("Description field is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidEventExists() {
		when(transactionHistoryServiceMock.getTransactionHistoryRepository().existsById(transactionHistoryModel.getTransactionID())).thenReturn(true);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
		assertEquals("This event already exists", apiResponse.getMessageDetail());
	}

	@Test
    void invalidEventExistsException() {
        DatastoreException datastoreException = new DatastoreException(14, 
                                                                       "I/O error", 
                                                                       "code=UNAVAILABLE",
                                                                       new UnknownHostException("datastore.googleapis.com"));

        when(transactionHistoryServiceMock.getTransactionHistoryRepository().existsById(transactionHistoryModel.getTransactionID())).thenThrow(datastoreException);
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationSave(transactionHistoryModel);
        assertEquals("Transaction history request denied", apiResponse.getMessageDetail());
    }
	
	@Test
	void saveTransactionSuccess() {
		when(transactionHistoryServiceMock.getTransactionHistoryRepository().existsById(transactionHistoryModel.getTransactionID())).thenReturn(false);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
		assertEquals("The transaction was successfully", apiResponse.getMessageDetail());
	}
	
	@Test
	void saveTransactionException() {
		DatastoreException datastoreException = new DatastoreException(14, 
				                                                       "I/O error", 
				                                                       "code=UNAVAILABLE",
				                                                       new UnknownHostException("datastore.googleapis.com"));

		when(transactionHistoryRepositoryMock.save(Mockito.any())).thenThrow(datastoreException);
		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
		assertEquals("Transaction history request denied", apiResponse.getMessageDetail());
	}	
	
	@Test
    void saveTransactionNotSuccess() {
	    transactionHistoryModel.setMsisdn("55XXX");
        when(transactionHistoryServiceMock.getTransactionHistoryRepository().existsById(transactionHistoryModel.getTransactionID())).thenReturn(false);
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.saveTransaction(transactionHistoryModel, bffKey);
        assertEquals(new Integer(2), apiResponse.getCode());
        assertNotEquals("The transaction was successfully", apiResponse.getMessageDetail());
    }

	@Test
    void bffKeyIsEmptyFind() {
        bffKey = "";
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
    @Test
    void bffKeyIsNullFind() {
        bffKey = null;
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
    
    @Test
    void bffKeyNotInvalidFind() {
        bffKey = "5678";
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertEquals("Bffkey field is invalid", apiResponse.getMessageDetail());
    }
	
	@Test
	void timestampStartDateIsEmpty() {
		transactionHistorySolicitationDTO.setStartDate("");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void timestampEndDateIsEmpty() {
		transactionHistorySolicitationDTO.setEndDate("");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsEmpty() {
		transactionHistorySolicitationDTO.setMsisdn("");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnNotOnlynumber() {
		transactionHistorySolicitationDTO.setMsisdn("123456789012a");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnMoreThanMaxPosition() {
		transactionHistorySolicitationDTO.setMsisdn("12345678901234");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void msisdnIsNull() {
		transactionHistorySolicitationDTO.setMsisdn(null);

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void numRecordsIsNull() {
		transactionHistorySolicitationDTO.setNumRecord(null);

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Required field not found", apiResponse.getMessageDetail());
	}

	@Test
	void invalidStartDate() {
		transactionHistorySolicitationDTO.setStartDate("2019-11-28");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Date format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidEndDate() {
		transactionHistorySolicitationDTO.setEndDate("2019-11-29");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Date format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidMsisdn() {
		transactionHistorySolicitationDTO.setMsisdn("55111111111AD");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("Msisdn format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidNegativeRecordsValue() {
		transactionHistorySolicitationDTO.setNumRecord("-1");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void numRecordsIsZero() {
		transactionHistorySolicitationDTO.setNumRecord("0");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidOutOfRangeNumRecords() {
		transactionHistorySolicitationDTO.setNumRecord("101");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void invalidNumRecords() {
		transactionHistorySolicitationDTO.setNumRecord("a");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("NumRecord format is invalid", apiResponse.getMessageDetail());
	}

	@Test
	void NumPageNotNullButNotNumber() {
		transactionHistorySolicitationDTO.setNumPage("abc");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertEquals("NumPage format is invalid", apiResponse.getMessageDetail());

	}

	@Test
	void NumPageValid() {
		transactionHistorySolicitationDTO.setNumPage("1234");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertNull(apiResponse);
	}

	@Test
	void NumPageBlank() {
		transactionHistorySolicitationDTO.setNumPage("");

		AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.fieldValidationFind(transactionHistorySolicitationDTO);
		assertNull(apiResponse);
	}
	
	@Test
    void validaReturnApiResponseErroAndContadoresIsNull() {
        transactionHistoryBusiness.setjUnitTest(true);
        transactionHistorySolicitationDTO.setMsisdn("55XXX");
        transactionHistoryBusiness.setTransactionHistoryModels(new ArrayList<AgendaClienteModel>());
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertNull(apiResponse.getCurrentPage());
        assertNull(apiResponse.getTotalNumPage());
        assertNull(apiResponse.getTotalNumRecord());
        assertNull(apiResponse.getTransactions());
        assertEquals(new Integer(2), apiResponse.getCode());
    }
	
	@Test
    void validaHistoryModesIsEmpty() {
        transactionHistoryBusiness.setjUnitTest(true);
        transactionHistoryBusiness.setTransactionHistoryModels(new ArrayList<AgendaClienteModel>());
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertEquals(new Long(0), apiResponse.getTotalNumRecord());
        assertTrue(apiResponse.getTransactions().isEmpty());
    }
	
	@Test
    void validateReturnTransactionHistoryModesNotIsEmptyAndEqualsvalidTransactionPayload() {
        transactionHistoryBusiness.setjUnitTest(true);
	    transactionHistoryBusiness.setTransactionHistoryModels(validTransactionPayload());
        
	    AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
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
    void validateReturnTransactionHistoryModesNotStartDateAndDateEnd() {
    
        when(transactionHistoryServiceMock.findTransactionHistoryByMsisdn(transactionHistorySolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        transactionHistoryBusiness.setjUnitTest(false);
        transactionHistorySolicitationDTO.setNumPage(null);
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
        assertEquals(new Long(0), apiResponse.getTotalNumRecord());
        assertTrue(apiResponse.getTransactions().isEmpty());
    }
	
	@Test
    void validateReturnTransactionHistoryModesBetwenStartDateAndDateEnd() {
    
        when(transactionHistoryServiceMock.findTransactionHistoryByMsisdn(transactionHistorySolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        transactionHistoryBusiness.setjUnitTest(false);
        transactionHistorySolicitationDTO.setNumPage("");
        transactionHistorySolicitationDTO.setStartDate("20191201");
        transactionHistorySolicitationDTO.setEndDate("20200131");
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
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
    
        when(transactionHistoryServiceMock.findTransactionHistoryByMsisdn(transactionHistorySolicitationDTO.getMsisdn())).thenReturn(validTransactionPayload());

        transactionHistoryBusiness.setjUnitTest(false);
        transactionHistorySolicitationDTO.setNumPage("");
        transactionHistorySolicitationDTO.setStartDate("20190101");
        transactionHistorySolicitationDTO.setEndDate("20200101");
        
        AgendaClienteApiResponse apiResponse = transactionHistoryBusiness.findTransactionHistoryDocuments(transactionHistorySolicitationDTO, bffKey);
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
		transactionHistoryModel = new AgendaClienteModel();
		transactionHistoryModel.setAmount("123");
		transactionHistoryModel.setDescription("descriptionPayload");
		transactionHistoryModel.setEventTimeStamp(ZonedDateTime.now().toString());
		transactionHistoryModel.setRequestId(Utils.generateId());
		transactionHistoryModel.setMsisdn("5511999999999");
		transactionHistoryModel.setTimestamp("20200101T23:59:59");
		transactionHistoryModel.setTransactionID("12346789");
		transactionHistoryModel.setId(transactionHistoryModel.getTransactionID());
		transactionHistoryModel.setType("refil");

		List<AgendaClienteModel> transactions = new ArrayList<AgendaClienteModel>();
		transactions.add(transactionHistoryModel);
		return transactions;
	}
	
}