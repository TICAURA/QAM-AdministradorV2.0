package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.DetCargaNequi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetCargaNequiRepository extends JpaRepository<DetCargaNequi,Integer>{

	
	@Query("SELECT COUNT(dc) FROM DetCargaNequi dc WHERE dc.idCargaMasiva = ?1 AND (dc.idEstatusCarga = ?2 OR dc.idEstatusCarga = ?3)")
	Integer countSituacionExito(Integer idCargaMasiva, Integer idEstatusCargaU, Integer idEstatusCargaD);
	
	@Query("SELECT COUNT(dc) FROM DetCargaNequi dc WHERE dc.idCargaMasiva = ?1 AND (dc.idEstatusCarga = ?2 OR dc.idEstatusCarga = ?3 OR dc.idEstatusCarga = ?4)")
	Integer countSituacionError(Integer idCargaMasiva, Integer idEstatusCarga, Integer idEstatusCargaU, Integer idEstatusCargaD);

	@Query("SELECT dc FROM DetCargaNequi dc WHERE dc.idCargaMasiva = ?1")
	List<DetCargaNequi> getByIdCargaMasiva(Integer idCargaMasiva);
	
	@Query(value = "select F_INSERTA_NEQUI ( ?1, ?2)", nativeQuery = true)
	String callFunctionNequi(Integer idCargaNequi, String rfcCliente);
	
	@Query("SELECT DISTINCT (dc.nit) FROM DetCargaNequi dc WHERE dc.idCargaMasiva = ?1")
	String getNit (Integer idCargaMasiva);
}
