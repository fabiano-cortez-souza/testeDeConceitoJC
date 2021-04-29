package br.com.fcs.agendacliente.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * As seguintes anotações (annotations) são do projeto Lombok 
 * AllArgsConstructor: cria automaticamente um construtor com todas os atributos da classe como argumento.
 * NoArgsConstructor: cria automaticamente um construtor vazio (sem argumentos).
 * Data: cria automaticamente os métodos toString, equals, hashCode, getters e setters.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contact {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name="contact_name")
   private String name;
   private String email;
   private String phone;
}