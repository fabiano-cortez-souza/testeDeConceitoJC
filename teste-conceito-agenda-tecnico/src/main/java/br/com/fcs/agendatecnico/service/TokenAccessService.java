package br.com.fcs.agendatecnico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fcs.agendatecnico.conector.HttpRequest;
import br.com.fcs.agendatecnico.model.TokenAccessModel;
import br.com.fcs.agendatecnico.repository.TokenAccessRepository;

@Service
public class TokenAccessService extends HttpRequest {
    
	@Autowired
	private TokenAccessRepository tokenAccessRepository;

	public List<TokenAccessModel> findAll() {
	    return tokenAccessRepository.findAll();
	}

	public TokenAccessRepository getTokenAccessRepository() {
		return tokenAccessRepository;
	}

	public void setTokenAccessRepository(TokenAccessRepository tokenAccessRepository) {
		this.tokenAccessRepository = tokenAccessRepository;
	}
}
