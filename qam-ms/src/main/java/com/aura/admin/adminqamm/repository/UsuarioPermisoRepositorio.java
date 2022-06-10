package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.UsuarioPermiso;
import com.aura.admin.adminqamm.model.UsuarioPermisoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPermisoRepositorio extends JpaRepository<UsuarioPermiso, UsuarioPermisoKey> {
}
