package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {
    Rol findByNombre(String nombre);
}
