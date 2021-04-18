package br.com.fcs.agendatecnico.conector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fcs.agendatecnico.enums.ErrorType;
import br.com.fcs.agendatecnico.response.ApiResponse;

@Component
public class HttpRequest {
    
    private boolean jUnit = false;
	private int responseCode;
	private URL url; 
	private HttpURLConnection con;
	
	public String sendPostRequest(Object payload, String address, String contentType, String token) throws IOException {
		Map<String, String> headers = prepareRestHeaders(contentType, token);

		String body = payload.toString();
		
		try {
			if (!jUnit) {
				url = new URL(address);
				con = (HttpURLConnection) url.openConnection();
			}

			con.setRequestMethod("POST");
			con.setConnectTimeout(60000);
			headers.forEach((key, value) -> con.setRequestProperty(key, value));
			con.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);

			wr.write(body);
			wr.flush();
			wr.close();
			this.responseCode = con.getResponseCode();
			return getResponseBody(con);
		} catch(SocketTimeoutException socketTimeoutException) {
			return new ApiResponse(ErrorType.HTTP_CLIENT_TIMEOUT).toString();
		}
	}
	
	private String getResponseBody(HttpURLConnection con) throws IOException {
		if (isSuccessResponse(con))
			return extractStreamText(con.getInputStream());
		else
			return extractStreamText(con.getErrorStream());
	}
	
	private boolean isSuccessResponse(HttpURLConnection con) throws IOException {
		return con.getResponseCode() == HttpURLConnection.HTTP_OK || con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED;
	}
	
	private String extractStreamText(InputStream inputStream) throws IOException {
		BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(inputStream));

		String inputLine;
		StringBuilder responseBuilder = new StringBuilder();

		while ((inputLine = bufferedInput.readLine()) != null)
			responseBuilder.append(inputLine);

		bufferedInput.close();

		return responseBuilder.toString();
	}

	
	public Map<String, String> prepareRestHeaders(String contentType, String token){
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-type", contentType);
		
		if(!StringUtils.isBlank(token)) {
		    headers.put("x-client-auth", token);
		}
		
		return headers;
	}
	
	@ExceptionHandler({MalformedURLException.class, IOException.class})
	public String invalidURL(MalformedURLException malformedURLException) {
		return malformedURLException.getMessage();
	}
	
	@ExceptionHandler(IOException.class)
	public String responseError(IOException iOException) {
		return iOException.getMessage();
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

    public boolean isjUnit() {
        return jUnit;
    }
    
    public void setjUnit(boolean jUnit) {
        this.jUnit = jUnit;
    }

    public URL getUrl() {
        return url;
    }
    
    public void setUrl(URL url) {
        this.url = url;
    }
   
	public HttpURLConnection getCon() {
		return con;
	}

	public void setCon(HttpURLConnection con) {
		this.con = con;
	}
    
}
