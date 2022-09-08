package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Cliente,Integer> {

	@Query("SELECT c FROM Cliente c WHERE c.centroCostosId IS NULL ORDER BY c.razon ASC")
	List<Cliente> getCentroCostoAll();

	List<Cliente> findByEsSubcontractorOrderByRazonAsc(Boolean esSubcontrato);
	
	@Query("SELECT c FROM Cliente c WHERE c.rfc = ?1 AND c.registroPatronal = ?2 AND (c.subRFC IS NULL OR c.subRFC='')")
	Cliente getByRfcAndRegistroPatronal(String rfc, String registroPatronal);
	
	Cliente findByRfcAndRegistroPatronalAndSubRFC(String rfc, String registroPatronal, String subRFC);
}
