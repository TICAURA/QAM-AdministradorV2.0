package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permiso,Integer> {
}
