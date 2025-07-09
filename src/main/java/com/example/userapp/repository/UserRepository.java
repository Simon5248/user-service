// ===================================================================================
// FILE: src/main/java/com/example/taskapp/repository/UserRepository.java
// ===================================================================================
package com.example.userapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userapp.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
