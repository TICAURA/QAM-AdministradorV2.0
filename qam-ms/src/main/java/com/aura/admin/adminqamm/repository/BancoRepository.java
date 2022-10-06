package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco,Integer> {
	
	@Query("SELECT b FROM Banco b WHERE b.descripcion = ?1")
	Banco consultarBancoPorDescripcion(String descripcionBanco);
}
