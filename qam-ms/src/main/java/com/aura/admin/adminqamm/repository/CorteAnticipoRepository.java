/**
 * 
 */
package com.aura.admin.adminqamm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aura.admin.adminqamm.model.CorteAnticipo;

/**
 * @author Cesar Agustin Soto
 *
 */
@Repository
public interface CorteAnticipoRepository extends JpaRepository<CorteAnticipo, Integer> {

	List<CorteAnticipo> findAllByCentroCostosIdAndEsActivo(Integer idCentroCosto, Boolean esActivo);
	
	List<CorteAnticipo> findAllByCentroCostosIdAndPeriodicidadAndEsActivo(Integer idCentroCosto, String periodicidad,
			Boolean esActivo);
	
	@Query("SELECT ca FROM CorteAnticipo ca WHERE ca.centroCostosId = ?1 AND ca.corteId NOT IN ( ?2 ) AND ca.periodicidad = ?3 AND ?4 BETWEEN ca.fchInicio AND ca.fchFin")
	List<CorteAnticipo> consultarCortePorFechas(Integer centroCostoId, Integer corteAnticipoId, String periodicidad, Date fechaEdit);

	
}
