package br.com.fcs.agendatecnico.pubsub;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import br.com.fcs.agendatecnico.utils.Utils;

@Component
@EnableAutoConfiguration
public class PubSubSenderCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(PubSubSenderCore.class);

	@Autowired
	private final PubSubTemplate pubSubTemplate;
	
	private String Topic;
	private boolean statusEnvio = false;

	public PubSubSenderCore(PubSubTemplate pubSubTemplate) {
		this.pubSubTemplate = pubSubTemplate;
	}
	
	public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this.Topic = Topic;
    }

    public boolean isStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(boolean statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    /**
	 * Executa o Pub Sub e captura o resultado de processamento Sincorno e Assincorno entre
	 * Nuvem e Client Ciclo de 3 tentativas, com intervalo de 1s em cada tentativa,
	 * antes de lançar Exception
	 */
	public void sendMessage(PubSubTopicObject pubSubTopicObject) {

		boolean loopEnvioAtivo = true;
		boolean lancaException = false;
		int contador = 1;

		pubSubTopicObject.setId(Utils.generateId());
		
		do {
			if (callPubSub(pubSubTopicObject, lancaException)) {
				loopEnvioAtivo = false;
			} else {
				
			    try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					LOGGER.warn("Erro no sleep {}", e.getMessage());
				}
				
				if (contador < 3) {
					contador++;
					LOGGER.warn("Nova tentativa de n° {} , no envio ao PubSub: topico {} do payload: {}", contador, Topic, pubSubTopicObject);
					if (callPubSub(pubSubTopicObject, lancaException)) {
						loopEnvioAtivo = false;
					}
				} else {
					lancaException = true;
					LOGGER.warn("Ultima tentativa de envio ao PubSub: topico {} ", Topic); 
					callPubSub(pubSubTopicObject, lancaException);
					loopEnvioAtivo = false;
				}
			}
		} while (loopEnvioAtivo);
	}

	private boolean callPubSub(PubSubTopicObject pubSubTopicObject, boolean lancaException) {
		try {
			ListenableFuture<String> listenableFuture = pubSubTemplate.publish(Topic, pubSubTopicObject.toString());
			listenableFuture.get(60, TimeUnit.SECONDS);

			if (listenableFuture.isDone()) {
				LOGGER.info("Enviado para o topico: {} ", Topic);
				setStatusEnvio(true);
				return isStatusEnvio();
			} else {
				LOGGER.warn("Falha no envio para o topico {} ", Topic);
				if (lancaException) {
					/**
					 * Chamada para PubSub lançar Exception, podendo ser de = Cancelamento, via
					 * solicitação; = Erro de processamento interno no PUB Sub; = Interrupção, via
					 * solicitação.
					 */
					listenableFuture.get();
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Erro no envio sincrono ao PubSub - topico {} - Exception - {} ", Topic, ex.getMessage());
		}
		setStatusEnvio(false);
		return isStatusEnvio();
	}
}