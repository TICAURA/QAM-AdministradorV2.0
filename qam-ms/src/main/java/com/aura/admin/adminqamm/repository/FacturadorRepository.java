package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Facturador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturadorRepository extends JpaRepository<Facturador,Integer> {
}
