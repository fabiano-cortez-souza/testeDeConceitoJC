package br.com.fcs.agendatecnico.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.fcs.agendatecnico.constants.Constants;

public class DateUtils {
    
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }
    
	public static LocalDateTime timestampValidation(String timestamp){
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Constants.TIMESTAMP_FORMAT);
        return LocalDateTime.parse(timestamp, format);
	}

	public static LocalDate dateValidation(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        return LocalDate.parse(date, format);
	}
		
	public static LocalDate getLocalDateOf(String date) {
		return dateValidation(date);
	}
}
