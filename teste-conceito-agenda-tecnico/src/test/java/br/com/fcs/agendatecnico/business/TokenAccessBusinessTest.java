package br.com.fcs.agendatecnico.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.fcs.agendatecnico.business.TokenAccessBusiness;
import br.com.fcs.agendatecnico.model.TokenAccessModel;
import br.com.fcs.agendatecnico.service.TokenAccessService;
import br.com.fcs.agendatecnico.utils.JsonUtils;

@RunWith(MockitoJUnitRunner.class)
class TokenAccessBusinessTest {

	private TokenAccessBusiness tokenAccessBusiness;
	
	@Mock
	private TokenAccessService tokenAccessServiceMock;
	
	private TokenAccessModel tokenAccessModel;
	
	private List<TokenAccessModel> listTokenAccessModel = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		initMocks(this);
		tokenAccessBusiness = new TokenAccessBusiness();
		tokenAccessBusiness.setTokenAccessService(tokenAccessServiceMock);
		tokenAccessBusiness.setTokenAccessModel(tokenAccessModel);
		
		tokenAccessModel = new TokenAccessModel((DateTime.now()).toString(), 
                								"86399",
                								"Bearer",
                								"123456",
                								(DateTime.now().minusMinutes(10)).toString());
		listTokenAccessModel.clear();
	}

	@Test
	void processaTokenIdentificaClassifacacaoDescendente() {
	    
	    DateTime dataAtual = DateTime.now();
	    
	    for(int i = 0; i < 3; i ++) {
	        TokenAccessModel tokenAccess = new TokenAccessModel((dataAtual.minusMinutes(i)).toString(), 
                                                                "86399",
                                                                "Bearer",
                                                                "123456",
                                                                (DateTime.now().minusMinutes(i+10)).toString());
	        listTokenAccessModel.add(tokenAccess);    
	    }
	    
	    when(tokenAccessServiceMock.findAll()).thenReturn(listTokenAccessModel);
	    
	    String tokenAccess = tokenAccessBusiness.getTokenAccess();
	    
	    assertEquals(dataAtual.minusMinutes(0).toString(), tokenAccessBusiness.getTokenAccessModel().getTimeStampInsere());
	    assertEquals("Bearer 123456" , tokenAccess);
	}
	
	@Test
    void processaTokenListaNull() {
        
        when(tokenAccessServiceMock.findAll()).thenReturn(listTokenAccessModel);
        
        String tokenAccess = tokenAccessBusiness.getTokenAccess();
        
        assertNull(tokenAccess);
    }

}
