package com.example.voltage.clientimtation.repository;

import com.example.voltage.clientimtation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);
}
