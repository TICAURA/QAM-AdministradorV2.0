package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.DetCarga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetCargaRepository extends JpaRepository<DetCarga,Integer>{

	
	@Query("SELECT COUNT(dc) FROM DetCarga dc WHERE dc.detCargaId.idCargaMasiva = ?1 AND dc.idSituacion IN ?2")
	Integer countByIdsituacion(Integer idCargaMasiva, String valorSituacion);

	@Query("SELECT dc FROM DetCarga dc WHERE dc.detCargaId.idCargaMasiva = ?1")
	List<DetCarga> getByIdCargaMasiva(Integer idCargaMasiva);

}
