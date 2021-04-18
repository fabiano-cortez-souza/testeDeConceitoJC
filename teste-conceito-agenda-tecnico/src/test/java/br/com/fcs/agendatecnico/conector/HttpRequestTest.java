package br.com.fcs.agendatecnico.conector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.fcs.agendatecnico.conector.HttpRequest;
import br.com.fcs.agendatecnico.response.ApiResponse;
import br.com.fcs.agendatecnico.utils.JsonUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, URL.class, HttpURLConnection.class}) 
class HttpRequestTest {

	private	String url = "http://example.com";
	private	URL urlNotMock;
	private HttpURLConnection HttpURLConnectionMock;

	@InjectMocks
	private HttpRequest httpRequest;
	
	@BeforeEach
	public void setUp() throws MalformedURLException {
		initMocks(this);
		 httpRequest = new HttpRequest();
		 httpRequest.setjUnit(true);
		 urlNotMock = new URL(url);
		 HttpURLConnectionMock = PowerMockito.mock(HttpURLConnection.class);
	}
	
	@Test
	void testSuccessSendPostRequest() throws Exception, UnknownHostException {

		String page = "<!doctype html><html><head>    <title>Example Domain</title>    <meta charset=\"utf-8\" />    <meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\" />    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />    <style type=\"text/css\">    body {        background-color: #f0f0f2;        margin: 0;        padding: 0;        font-family: -apple-system, system-ui, BlinkMacSystemFont, \"Segoe UI\", \"Open Sans\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;            }    div {        width: 600px;        margin: 5em auto;        padding: 2em;        background-color: #fdfdff;        border-radius: 0.5em;        box-shadow: 2px 3px 7px 2px rgba(0,0,0,0.02);    }    a:link, a:visited {        color: #38488f;        text-decoration: none;    }    @media (max-width: 700px) {        div {            margin: 0 auto;            width: auto;        }    }    </style>    </head><body><div>    <h1>Example Domain</h1>    <p>This domain is for use in illustrative examples in documents. You may use this    domain in literature without prior coordination or asking for permission.</p>    <p><a href=\"https://www.iana.org/domains/example\">More information...</a></p></div></body></html>";
	    httpRequest.setUrl(urlNotMock);
	    httpRequest.setjUnit(false);
	    Object obj = httpRequest.sendPostRequest(new Object(), url, url, null);
	    assertEquals(page, obj.toString());
	}
	
	@Test
	void testExceptionSendPostRequest() throws Exception {
	    
		httpRequest.setCon(HttpURLConnectionMock);	
		PowerMockito.when(HttpURLConnectionMock.getOutputStream()).thenThrow(SocketTimeoutException.class);
	    
	    Object obj = httpRequest.sendPostRequest(new Object(), url, url, "teste");
	    ApiResponse apiResponse = JsonUtils.getGson().fromJson(obj.toString(), ApiResponse.class);
	    
	    assertEquals("Request timeout", apiResponse.getMessageDetail());
	}	
}
