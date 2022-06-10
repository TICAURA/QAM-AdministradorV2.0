package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.TipoAmbiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAmbienteRepositorio extends JpaRepository<TipoAmbiente,Integer> {
    TipoAmbiente findByNombre(String nombre);
}
