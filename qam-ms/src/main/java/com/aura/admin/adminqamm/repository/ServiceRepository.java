package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Servicio,Integer> {
    Servicio findByNombre(String nombre);

}
