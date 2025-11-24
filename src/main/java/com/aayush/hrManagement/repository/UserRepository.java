package com.aayush.hrManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aayush.hrManagement.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserEmail(String userEmail);
}
