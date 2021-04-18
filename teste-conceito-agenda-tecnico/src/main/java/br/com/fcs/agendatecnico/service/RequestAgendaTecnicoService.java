package br.com.fcs.agendatecnico.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import br.com.fcs.agendatecnico.conector.HttpRequest;
import br.com.fcs.agendatecnico.pubsub.PubSubSincronoEnvioFila;
import br.com.fcs.agendatecnico.vo.TransactionHistoryVO;


@Service
@EnableAutoConfiguration
@ComponentScan(basePackages = {"br.com.ericsson.flex.pubsub"})
public class RequestAgendaTecnicoService extends HttpRequest{
	
	private boolean jUnitTest = false;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAgendaTecnicoService.class);

	@Autowired
	private final PubSubSincronoEnvioFila pubSubSincronoEnvioFila;
	
	public RequestAgendaTecnicoService(PubSubSincronoEnvioFila pubSubSincronoEnvioFila) {
		this.pubSubSincronoEnvioFila = pubSubSincronoEnvioFila;
	}

	public void envioPubSub(TransactionHistoryVO transactionHistoryVO) {
	    
	    LOGGER.info("[ENVIOPUBSUB - {}] - Eviando para Mensageria.", transactionHistoryVO.getTransactionID());

		try {
			if (!pubSubSincronoEnvioFila.sendMessageGetStatus(transactionHistoryVO)) {
			    LOGGER.info("[ERRO - {}] - Falha envio pra fila PUBSUB.", transactionHistoryVO.getTransactionID());
			}
		} catch (Exception ex) {
		    LOGGER.error("[EXCEPTION] - Falha ao Inserir no historico de transações. {} ", ex.getMessage());
		}
	}
	
	public String envioPostRequest(Object payload, String address, String content_type, String token) throws Exception {
	    
	    String response = null; 
	    LOGGER.info("[EnvioPostRequest] - enviando post Request IP {} - CONTENT-TYPE: {} ", address, content_type);	            
	   	response = sendPostRequest(payload.toString(), address, content_type, token);
	    
	    return response;
    }
	
    public PubSubSincronoEnvioFila getPubSubSincronoEnvioFila() {
        return pubSubSincronoEnvioFila;
    }
    
    public boolean isjUnitTest() {
        return jUnitTest;
    }

    public void setjUnitTest(boolean jUnitTest) {
        this.jUnitTest = jUnitTest;
    }
}