package com.aura.admin.adminqamm.repository;

import com.aura.admin.adminqamm.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario,Integer> {
     Usuario findByUser(String user);
}
