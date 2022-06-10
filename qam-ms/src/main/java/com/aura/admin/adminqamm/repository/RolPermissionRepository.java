package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.RolPermiso;
import com.aura.admin.adminqamm.model.RolPermisoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolPermissionRepository extends JpaRepository<RolPermiso, RolPermisoKey> {
}
