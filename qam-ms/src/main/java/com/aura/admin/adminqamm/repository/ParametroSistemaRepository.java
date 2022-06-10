package com.aura.admin.adminqamm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aura.admin.adminqamm.model.ParametroSistema;

public interface ParametroSistemaRepository extends JpaRepository<ParametroSistema, Integer> {

	List<ParametroSistema> findBySgmnIdAndEsActivo(Integer idSegmento, Boolean esActivo);

	@Query("SELECT p FROM  ParametroSistema p WHERE p.esActivo=true")
	List<ParametroSistema> buscarParametrosAll();

	ParametroSistema findByConsecutivo(Integer consecutivo);

}
