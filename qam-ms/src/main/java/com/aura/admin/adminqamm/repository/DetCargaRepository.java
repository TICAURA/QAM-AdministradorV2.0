package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.DetCarga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetCargaRepository extends JpaRepository<DetCarga,Integer>{

	
	@Query("SELECT COUNT(dc) FROM DetCarga dc WHERE dc.idCargaMasiva = ?1 AND (dc.idSituacion = ?2 OR dc.idSituacion = ?3)")
	Integer countSituacionExito(Integer idCargaMasiva, String situacionD, String situacionN);
	
	@Query("SELECT COUNT(dc) FROM DetCarga dc WHERE dc.idCargaMasiva = ?1 AND dc.idSituacion = ?2")
	Integer countSituacionError(Integer idCargaMasiva, String situacionE);

	@Query("SELECT dc FROM DetCarga dc WHERE dc.idCargaMasiva = ?1")
	List<DetCarga> getByIdCargaMasiva(Integer idCargaMasiva);

}
