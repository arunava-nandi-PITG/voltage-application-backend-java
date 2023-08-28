package com.example.voltage.clientimtation.repository;

import com.example.voltage.clientimtation.model.ERole;
import com.example.voltage.clientimtation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
   Optional<Role> findByName(ERole eRole);
}
