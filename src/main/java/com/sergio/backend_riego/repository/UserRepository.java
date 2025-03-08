package com.sergio.backend_riego.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.backend_riego.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
