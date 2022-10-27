package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.AuxCarga;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuxCargaRepository extends JpaRepository<AuxCarga,Integer> {
	
	@Query("SELECT a FROM AuxCarga a WHERE a.hash = ?1")
	AuxCarga findFileByHash(String hashText);
}
