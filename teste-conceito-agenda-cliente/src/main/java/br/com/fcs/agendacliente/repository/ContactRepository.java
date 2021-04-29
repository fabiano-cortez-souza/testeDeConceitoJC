package br.com.fcs.agendacliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fcs.agendacliente.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {} 
      