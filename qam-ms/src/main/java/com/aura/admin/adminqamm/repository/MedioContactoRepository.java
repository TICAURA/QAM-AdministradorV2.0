package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.MedioContacto;
import com.aura.admin.adminqamm.model.MedioContactoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedioContactoRepository extends JpaRepository<MedioContacto, MedioContactoId> {
}
