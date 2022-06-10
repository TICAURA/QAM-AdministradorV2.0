package com.aura.admin.adminqamm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aura.admin.adminqamm.model.Segmento;

/**
 * @author Cesar Agustin Soto
 *
 */
public interface SegmentoRepository extends JpaRepository<Segmento, Integer> {
	
	List<Segmento> findByEsActivo(Boolean esActivo);

	Segmento findBySgmnId(Integer segmentoId); 

}
