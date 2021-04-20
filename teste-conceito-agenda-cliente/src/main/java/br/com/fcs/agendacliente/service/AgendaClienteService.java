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
	private AgendaClienteRepository agendaClienteRepository;

	public boolean save(AgendaClienteModel transactionHistoryDocument) {
		try {
			agendaClienteRepository.save(transactionHistoryDocument);
			return true;
		} catch (Exception ex) {
			LOGGER.error("[SAVE] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
			return false;
		}
	}

	public boolean deleteIdAgenda(String idAgenda) {
        try {
            List<AgendaClienteModel> agendaClienteModels = findAgendaByidAgenda(idAgenda);
            for (AgendaClienteModel agendaClienteModel : agendaClienteModels) {
                agendaClienteRepository.delete(agendaClienteModel);    
            }
            return true;
        } catch (Exception ex) {
        	LOGGER.error("[DELETEMSISDN] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return false;
        }
    }
	
	public boolean deleteAll() {
        try {
            agendaClienteRepository.deleteAll();    
            return true;
        } catch (Exception ex) {
        	LOGGER.error("[DELETEALL] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return false;
        }
    }
	
	public String eventExists(Double agendaID) {
	    String retorno = "N";
	    try {
	        if(agendaClienteRepository.existsById(agendaID.toString())) {
	            retorno = "S";
	        }
	        return retorno;    
        } catch (Exception ex) {
            LOGGER.error("[EVENTEXISTS] Exception Error: {} - Cause {}", ex.getMessage(), ex.getClass());
            return "ERRO";
        }
	}

	public List<AgendaClienteModel> findAgendaByidAgenda(String idAgenda) {
		return agendaClienteRepository.findByidAgenda(idAgenda);
	}
/*
	public long countDocuments(String idAgenda, String strDate, String endDate) {
		return agendaClienteRepository.countWithTimeStampRange(idAgenda, strDate, endDate);

	}
/**/
	public AgendaClienteRepository getAgendaClienteRepository() {
		return agendaClienteRepository;
	}

	public void setTransactionHistoryRepository(AgendaClienteRepository agendaClienteRepository) {
		this.agendaClienteRepository = agendaClienteRepository;
	}
}
