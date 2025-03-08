package com.sergio.backend_riego.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.backend_riego.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
