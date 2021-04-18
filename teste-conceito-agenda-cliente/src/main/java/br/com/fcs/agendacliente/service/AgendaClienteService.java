package br.com.fcs.agendacliente.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fcs.agendacliente.model.AgendaClienteModel;
import br.com.fcs.agendacliente.repository.AgendaClienteRepository;

@Service
public class AgendaClienteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaClienteService.class);

	@Autowired
	private AgendaClienteRepository transactionHistoryRepository;

	public boolean save(AgendaClienteModel transactionHistoryDocument) {
		try {
			transactionHistoryRepository.save(transactionHistoryDocument);
			return true;
		} catch (Exception ex) {
			LOGGER.error("[SAVE] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
			return false;
		}
	}

	public boolean deleteMsisdn(String msisdn) {
        try {
            List<AgendaClienteModel> transactionHistoryModels = findTransactionHistoryByMsisdn(msisdn);
            for (AgendaClienteModel transactionHistoryModel : transactionHistoryModels) {
                transactionHistoryRepository.delete(transactionHistoryModel);    
            }
            return true;
        } catch (Exception ex) {
        	LOGGER.error("[DELETEMSISDN] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return false;
        }
    }
	
	public boolean deleteAll() {
        try {
            transactionHistoryRepository.deleteAll();    
            return true;
        } catch (Exception ex) {
        	LOGGER.error("[DELETEALL] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return false;
        }
    }
	
	public String eventExists(String transactionId) {
	    String retorno = "N";
	    try {
	        if(transactionHistoryRepository.existsById(transactionId)) {
	            retorno = "S";
	        }
	        return retorno;    
        } catch (Exception ex) {
            LOGGER.error("[EVENTEXISTS] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return "ERRO";
        }
	}

	public List<AgendaClienteModel> findTransactionHistoryByMsisdn(String msisdn) {
		return transactionHistoryRepository.findBymsisdn(msisdn);
	}

	public long countDocuments(String msisdn, String strDate, String endDate) {
		return transactionHistoryRepository.countWithTimeStampRange(msisdn, strDate, endDate);

	}

	public AgendaClienteRepository getTransactionHistoryRepository() {
		return transactionHistoryRepository;
	}

	public void setTransactionHistoryRepository(AgendaClienteRepository transactionHistoryRepository) {
		this.transactionHistoryRepository = transactionHistoryRepository;
	}
}
