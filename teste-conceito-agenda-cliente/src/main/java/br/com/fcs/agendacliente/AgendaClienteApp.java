package br.com.fcs.agendacliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = { "br.com.ericsson.flex.transactionhistory"})
public class AgendaClienteApp{
    
    public static void main( String[] args ) {
    	SpringApplication.run(AgendaClienteApp.class, args);
    }

}
