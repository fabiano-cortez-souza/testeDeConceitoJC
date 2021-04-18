package br.com.fcs.agendatecnico.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

import br.com.fcs.agendatecnico.utils.Utils;

@Entity(name = "tokenAccess")
public class TokenAccessModel {

    @Id
    @Field(name = "scheduledTokenAccesssId")
    private String scheduledTokenAccessId;
    
    @Field(name = "timeStampInsere")
    private String timeStampInsere;

    @Field(name = "expires_in")
    private String expires_in;

	@Field(name = "token_type")
    private String token_type;
    
    @Field(name = "access_token")
    private String access_token;
    
    @Field(name = "timeStampExpira")
    private String timeStampExpira;
    
    public TokenAccessModel() {}

    public TokenAccessModel(String timeStampInsere, 
                            String expires_in,
                            String token_type,
                            String access_token, 
                            String timeStampExpira) {

        this.timeStampInsere = timeStampInsere;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.access_token = access_token;
        this.timeStampExpira = timeStampExpira;
        this.scheduledTokenAccessId = Utils.generateId();
    }

    public String getScheduledTokenAccessId() {
        return scheduledTokenAccessId;
    }

    public void setScheduledTokenAccessId(String scheduledTokenAccessId) {
        this.scheduledTokenAccessId = scheduledTokenAccessId;
    }

    public String getTimeStampInsere() {
        return timeStampInsere;
    }

    public void setTimeStampInsere(String timeStampInsere) {
        this.timeStampInsere = timeStampInsere;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTimeStampExpira() {
        return timeStampExpira;
    }

    public void setTimeStampExpira(String timeStampExpira) {
        this.timeStampExpira = timeStampExpira;
    }
    
    public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
}
