package br.com.fcs.agendatecnico.repository;

import java.util.List;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import br.com.fcs.agendatecnico.model.TokenAccessModel;

@Repository
public interface TokenAccessRepository extends DatastoreRepository<TokenAccessModel, String>{
    
    List<TokenAccessModel> findAll();
}
