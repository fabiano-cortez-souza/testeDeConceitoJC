package br.com.fcs.agendacliente.repository;

import java.util.List;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fcs.agendacliente.model.AgendaClienteModel;

@Repository
public interface AgendaClienteRepository extends DatastoreRepository<AgendaClienteModel, String> {
	
	//@Query(value = "{ $and: [{ 'msisdn' : { $eq : ?0 } }, { 'timestamp' : { $gte : ?1, $lte: ?2 } } ]}", count = true)
	@Query(value = "SELECT * FROM transactionHistory WHERE msisdn = @_msisdn AND timestamp >= @_strDate AND timestamp <= @_endDate", count = true)
	long countWithTimeStampRange(@Param("_msisdn")  String msisdn,
			                     @Param("_strDate") String strDate,
			                     @Param("_endDate") String endDate);
	
	
	//@Query("{ $and: [{ 'msisdn' : { $eq : ?0 } }, { 'timestamp' : { $gte : ?1, $lte: ?2 } } ]}, sort = { 'timeStamp' : -1 }")
	@Query("SELECT * FROM transactionHistory WHERE msisdn = @_msisdn AND timestamp >= @_strDate AND timestamp <= @_endDate ORDER BY timestamp DESC")
	List<AgendaClienteModel> getMsisdnByTimestamp(@Param("_msisdn")  String msisdn,
											           @Param("_strDate") String strDate,
													   @Param("_endDate") String endDate, 
													   Pageable pageable);
	
	List<AgendaClienteModel> findBymsisdn(String msisdn);
	
}
