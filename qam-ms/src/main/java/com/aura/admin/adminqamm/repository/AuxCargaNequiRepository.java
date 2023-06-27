package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.AuxCargaNequi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuxCargaNequiRepository extends JpaRepository<AuxCargaNequi,Integer> {
	
	@Query("SELECT a FROM AuxCargaNequi a WHERE a.hash = ?1")
	AuxCargaNequi findFileByHash(String hashText);
}
