package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.TipoContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContactoRepository extends JpaRepository<TipoContacto,Integer> {
}
