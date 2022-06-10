package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco,Integer> {
}
