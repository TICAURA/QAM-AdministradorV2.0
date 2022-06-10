package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServicioRepositorio extends JpaRepository<TipoServicio,Integer> {
    TipoServicio findByNombre(String nombre);
}
