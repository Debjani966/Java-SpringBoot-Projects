package com.example.IncidentManagementSystem.Project.Repository;

import com.example.IncidentManagementSystem.Project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findOneByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
