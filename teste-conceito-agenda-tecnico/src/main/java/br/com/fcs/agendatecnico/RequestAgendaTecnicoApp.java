package br.com.fcs.agendatecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = { "br.com.ericsson.flex.requestcustomerbalance"})
public class RequestAgendaTecnicoApp{
    
    public static void main( String[] args ) {
    	SpringApplication.run(RequestAgendaTecnicoApp.class, args);
    }

}
