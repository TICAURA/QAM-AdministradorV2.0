package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda,Integer> {
}
