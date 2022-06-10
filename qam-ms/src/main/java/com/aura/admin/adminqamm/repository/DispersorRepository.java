package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Dispersor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispersorRepository extends JpaRepository<Dispersor,Integer> {
}
