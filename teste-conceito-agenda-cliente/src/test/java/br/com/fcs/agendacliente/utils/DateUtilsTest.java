package br.com.fcs.agendacliente.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.com.fcs.agendacliente.utils.DateUtils;

class DateUtilsTest {

    @Test
    void localDateOfIsValid() {
        String timeStamp = "20191101";
        LocalDate localDate = LocalDate.of(2019, 11, 01);
        assertEquals(localDate, DateUtils.getLocalDateOf(timeStamp));
    }
    
	@Test
	void dateValidationIsValid() {
	    String timeStamp = "20191101";
	    LocalDate localDate = LocalDate.of(2019, 11, 01);
	    assertEquals(localDate, DateUtils.dateValidation(timeStamp));
	}
	
	@Test
    void timestampValidationIsValid() {
        String timeStamp = "20191216T12:00:00-0300";
        LocalDateTime localDatetime = LocalDateTime.of(2019, 12, 16, 12, 00, 00);
        assertEquals(localDatetime, DateUtils.timestampValidation(timeStamp));
    }

	@Test
    void localDateOfIsNotValid() {
        String timeStamp = "20b9110A1";
        LocalDate localDate = LocalDate.of(2019, 11, 01);
        try {
            assertEquals(localDate, DateUtils.getLocalDateOf(timeStamp));
        } catch (Exception e) {
            assertEquals("Text '20b9110A1' could not be parsed at index 0", e.getMessage());
        }
    }
	
	@Test
    void dateValidationIsNotValid() {
        String timeStamp = "20d9b101";
        LocalDate localDate = LocalDate.of(2019, 11, 01);
        try {
            assertEquals(localDate, DateUtils.dateValidation(timeStamp));
        } catch (Exception e) {
            assertEquals("Text '20d9b101' could not be parsed at index 0", e.getMessage());
        }
    }
	
	@Test
    void timestampValidationIsNotValid() {
        String timeStamp = "2019b216T1k:40:00-0300";
        LocalDateTime localDatetime = LocalDateTime.of(2019, 12, 16, 12, 00, 00);
        try {
            assertEquals(localDatetime, DateUtils.timestampValidation(timeStamp));
        } catch (Exception e) {
            assertEquals("Text '2019b216T1k:40:00-0300' could not be parsed at index 3", e.getMessage());
        }
    }
}