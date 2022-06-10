package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.UsuarioRol;
import com.aura.admin.adminqamm.model.UsuarioRolKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRolRepositorio extends JpaRepository<UsuarioRol, UsuarioRolKey> {

}
