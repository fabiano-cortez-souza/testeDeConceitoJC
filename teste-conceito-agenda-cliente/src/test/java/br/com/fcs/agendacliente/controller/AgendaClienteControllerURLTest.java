package br.com.fcs.agendacliente.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.fcs.agendacliente.business.AgendaClienteBusiness;
import br.com.fcs.agendacliente.controller.AgendaClienteController;
import br.com.fcs.agendacliente.repository.AgendaClienteRepository;
import br.com.fcs.agendacliente.service.AgendaClienteService;
import br.com.fcs.agendacliente.vo.AgendaClienteVO;

@WebMvcTest(controllers = AgendaClienteController.class)
@ActiveProfiles("test")
class AgendaClienteControllerURLTest {
	
    @Autowired                           
    private MockMvc mockMvc;
	
    @MockBean
	AgendaClienteController agendaClienteControllerMock;

	@Mock
	private AgendaClienteService agendaClienteServiceMock;

	@Mock
	public AgendaClienteBusiness transactionHistoryBusinessMock;

	@MockBean
	private AgendaClienteRepository agendaClienteRepositoryMock;

	private StringBuilder sbGetPathVariable = new StringBuilder();
	private StringBuilder sbGetRequestParam = new StringBuilder();
	private StringBuilder sbPostAgendaCliente = new StringBuilder();

	private final String basePath = "/api/v1/transactionHistory/";
	private final String msisdn = "5590988205339";
	private final String startDate = "20191128";
	private final String endDate = "20191129";
	private final String numRecord = "2";
	private final String numPage = "10";
	
	@BeforeEach
	public void setup() {
		initMocks(this);
		agendaClienteServiceMock.setTransactionHistoryRepository(agendaClienteRepositoryMock);
		transactionHistoryBusinessMock.setTransactionHistoryService(agendaClienteServiceMock);
	}

	@Test
	void contexLoadsNotNull() throws Exception {
		agendaClienteControllerMock = new AgendaClienteController();
		assertThat(agendaClienteControllerMock).isNotNull();
	}

	@Test
	void shouldGetPathVariableURLSucess() throws Exception {
		sbGetPathVariable.append(basePath);
		sbGetPathVariable.append(msisdn).append("/");
		sbGetPathVariable.append(startDate).append("/");
		sbGetPathVariable.append(endDate).append("/");
		sbGetPathVariable.append(numRecord);

		this.mockMvc.perform(get(sbGetPathVariable.toString())).andDo(print())
		                                                       .andExpect(status().isOk());
	}
	
	@Test
	void shouldGetRequestParamURLSucess() throws Exception {
		sbGetRequestParam.append(basePath).append("?");
		sbGetRequestParam.append("endDate=").append(endDate).append("&");
		sbGetRequestParam.append("msisdn=").append(msisdn).append("&");
		sbGetRequestParam.append("numPage=").append(numPage).append("&");
		sbGetRequestParam.append("numRecord=").append(numRecord).append("&");
		sbGetRequestParam.append("startDate=").append(startDate);

		this.mockMvc.perform(get(sbGetRequestParam.toString())).andDo(print())
		                                                       .andExpect(status().isOk());
	}
	/**
	@Test
	void shouldPostURLSucess() throws Exception {
		sbPostAgendaCliente.append(basePath);

		AgendaClienteVO agendaClienteVO = new AgendaClienteVO();
		agendaClienteVO.setAmount("123123158");
		agendaClienteVO.setDescription("teste");
		agendaClienteVO.setMsisdn("5511444445449");
		agendaClienteVO.setTimestamp("20191128T14:00:17-0301");
		agendaClienteVO.setTransactionID("AasDAsSaAaaSaQqLAaa0");
		agendaClienteVO.setType("refil");

		this.mockMvc.perform(post(sbPostAgendaCliente.toString())
				  .content(agendaClienteVO.toString()).contentType("application/json;charset=UTF-8"))
				  .andDo(print())
				  .andExpect(status().isOk());
	}
    /**/
	
	@Test
	void shouldGetPathVariableURLNotSucess() throws Exception {
		sbGetPathVariable.append(basePath);

		sbGetPathVariable.append(startDate).append("/");
		sbGetPathVariable.append(endDate).append("/");
		sbGetPathVariable.append(numRecord);

		this.mockMvc.perform(get(sbGetPathVariable.toString())).andDo(print())
		                                                       .andExpect(status().is4xxClientError());
	}
	
	@Test
	void shouldGetRequestParamURLNotSucess() throws Exception {
		sbGetRequestParam.append(basePath).append("?");

		sbGetRequestParam.append("msisdn=").append(msisdn).append("&");
		sbGetRequestParam.append("numPage=").append(numPage).append("&");
		sbGetRequestParam.append("numRecord=").append(numRecord).append("&");
		sbGetRequestParam.append("startDate=").append(startDate);

		this.mockMvc.perform(get(sbGetRequestParam.toString())).andDo(print())
		                                                       .andExpect(status().is4xxClientError());
	}
	/**
	@Test
	void shouldPostURLNotSucess() throws Exception {
		sbPostAgendaCliente.append(basePath);

		AgendaClienteVO transactionHistoryVO = new AgendaClienteVO();
		transactionHistoryVO.setAmount("123123158");

		transactionHistoryVO.setMsisdn("5511444445449");
		transactionHistoryVO.setTimestamp("20191128T14:00:17-0301");
		transactionHistoryVO.setTransactionID("AasDAsSaAaaSaQqLAaa0");
		transactionHistoryVO.setType("refil");

		this.mockMvc.perform(post(sbPostAgendaCliente.toString())
				  .content(transactionHistoryVO.toString()).contentType("application/json;charset=UTF-8"))
				  .andDo(print())
				  .andExpect(status().isOk());
	}
	/**/
}