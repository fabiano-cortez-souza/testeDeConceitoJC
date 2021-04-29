package br.com.fcs.agendacliente;

import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.fcs.agendacliente.model.Contact;
import br.com.fcs.agendacliente.repository.ContactRepository;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@ComponentScan(basePackages = { "br.com.fcs.agendacliente"})
public class AgendaClienteApp{
    
    int numero = 1111;
    
    public static void main( String[] args ) {
    	SpringApplication.run(AgendaClienteApp.class, args);
    }

    @Bean
    CommandLineRunner init(ContactRepository repository) {
        return args -> {
            repository.deleteAll();
            LongStream.range(1, 11)
                      .mapToObj(i -> {
                        Contact c = new Contact();
                        c.setName("Contact " + i);
                        c.setEmail("contact" + i + "@email.com");
                        c.setPhone("(111) 111-" + numero);
                        numero++;
                        return c;
                    })
                    .map(v -> repository.save(v))
                    .forEach(System.out::println);
        };
    }
}
