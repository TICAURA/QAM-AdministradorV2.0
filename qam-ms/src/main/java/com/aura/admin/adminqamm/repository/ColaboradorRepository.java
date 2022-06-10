/**
 * 
 */
package com.aura.admin.adminqamm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aura.admin.adminqamm.model.Colaborador;
import com.aura.admin.adminqamm.model.ColaboradorId;

/**
 * @author Cesar Agustin Soto
 *
 */
public interface ColaboradorRepository extends JpaRepository<Colaborador, ColaboradorId>{

	Colaborador findAllByMailRegistro(String email);

}
