/**
 * 
 */
package com.aura.admin.adminqamm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aura.admin.adminqamm.model.CuentaBanco;

/**
 * @author Cesar Agustin Soto
 *
 */
@Repository
public interface CuentaBancoRepository extends JpaRepository<CuentaBanco,String> {

	@Query("SELECT cb FROM CuentaBanco cb WHERE cb.clabe = ?1")
	CuentaBanco buscarCuentaPorId(String clabe);

	@Query("SELECT cb FROM CuentaBanco cb WHERE cb.persona.personaId = ?1 AND cb.esActivo = 1")
	List<CuentaBanco> buscarCuentaActivaColaborador(Integer idColaborador);

	@Query("SELECT cb FROM CuentaBanco cb WHERE cb.persona.personaId = ?1 ORDER BY cb.esActivo DESC")
	List<CuentaBanco> buscarCuentaColaborador(Integer idColaborador);

}
