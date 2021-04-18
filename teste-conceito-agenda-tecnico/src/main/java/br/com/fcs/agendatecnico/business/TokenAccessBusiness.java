package br.com.fcs.agendatecnico.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fcs.agendatecnico.model.TokenAccessModel;
import br.com.fcs.agendatecnico.service.TokenAccessService;

@Component
public class TokenAccessBusiness {
	
	@Autowired
	private TokenAccessService tokenAccessService;

	private TokenAccessModel tokenAccessModel;
	
	private String tokenAccess; 

	public String getTokenAccess() {
	    
		List<TokenAccessModel> listTokenAccessModel = tokenAccessService.findAll();
		
        if (!listTokenAccessModel.isEmpty()) {
        
            listTokenAccessModel.sort((TokenAccessModel o1, TokenAccessModel o2)->o2.getTimeStampInsere().compareTo(o1.getTimeStampInsere()));
            
            tokenAccessModel = listTokenAccessModel.get(0);
           
            tokenAccess = tokenAccessModel.getToken_type() + " " + tokenAccessModel.getAccess_token();
        }
        return tokenAccess;
	}
	
    public TokenAccessService getTokenAccessService() {
        return tokenAccessService;
    }

    public void setTokenAccessService(TokenAccessService tokenAccessService) {
        this.tokenAccessService = tokenAccessService;
    }

    public TokenAccessModel getTokenAccessModel() {
        return tokenAccessModel;
    }

    public void setTokenAccessModel(TokenAccessModel tokenAccessModel) {
        this.tokenAccessModel = tokenAccessModel;
    }
}
