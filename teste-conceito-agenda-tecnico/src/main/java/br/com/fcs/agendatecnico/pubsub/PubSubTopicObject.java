package br.com.fcs.agendatecnico.pubsub;

import java.io.Serializable;

import br.com.fcs.agendatecnico.utils.JsonUtils;

public class PubSubTopicObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;
	
    @Override
    public String toString() {
        return JsonUtils.parseToJsonString(this);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
