package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.MedioContacto;
import com.aura.admin.adminqamm.model.MedioContactoId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioContactoRepository extends JpaRepository<MedioContacto, MedioContactoId> {

	@Query("SELECT mc FROM MedioContacto mc WHERE mc.mediContactoId.persId = ?1 AND mc.mediContactoId.numClave = ?2")
	MedioContacto buscarContactoPorId(Integer idPersona, String numClave);

	@Query("SELECT mc FROM MedioContacto mc WHERE mc.mediContactoId.persId = ?1 AND mc.esActivo = 1 AND mc.idTctk.id = 1")
	List<MedioContacto> buscarContactoCelularColaborador(Integer idPersona);
}
