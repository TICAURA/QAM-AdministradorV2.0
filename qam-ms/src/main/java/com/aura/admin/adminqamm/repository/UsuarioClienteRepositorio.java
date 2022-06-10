package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.UsuarioCliente;
import com.aura.admin.adminqamm.model.UsuarioClienteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioClienteRepositorio extends JpaRepository<UsuarioCliente, UsuarioClienteKey> {

}
