package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Interfaz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceRepository extends JpaRepository<Interfaz,Integer> {
    Interfaz findByNombre(String nombre);

}
