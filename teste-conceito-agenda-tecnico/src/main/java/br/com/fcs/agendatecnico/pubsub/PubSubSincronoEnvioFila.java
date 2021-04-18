package br.com.fcs.agendatecnico.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
public class PubSubSincronoEnvioFila extends PubSubSenderCore{

    public PubSubSincronoEnvioFila(PubSubTemplate pubSubTemplate) {
        super(pubSubTemplate);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PubSubSincronoEnvioFila.class);
    
	@Value("${GCP.Topic.sincrono}")
	private String sincronoTopicCustomerBalance;
	
	@Autowired
	private PubSubSenderCore pubSubSenderCore;
	
	public boolean sendMessageGetStatus(PubSubTopicObject pubSubTopicObject) {
	    
	    LOGGER.info("[sendMessageGetStatus] - sincronoTopicReprocessing {}", sincronoTopicCustomerBalance);
	    
		pubSubSenderCore.setTopic(sincronoTopicCustomerBalance);
		pubSubSenderCore.sendMessage(pubSubTopicObject);
		return pubSubSenderCore.isStatusEnvio();
	}
	
	public void setPubSubSenderCore(PubSubSenderCore pubSubSenderCore) {
		this.pubSubSenderCore = pubSubSenderCore;
	}

    public void setSincronoTopicCustomerBalance(String sincronoTopicCustomerBalance) {
        this.sincronoTopicCustomerBalance = sincronoTopicCustomerBalance;
    }
	
}