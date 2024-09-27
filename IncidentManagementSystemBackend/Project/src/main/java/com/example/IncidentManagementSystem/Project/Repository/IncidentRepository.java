package com.example.IncidentManagementSystem.Project.Repository;

import com.example.IncidentManagementSystem.Project.Entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Optional<Incident> findByIncidentId(String incidentId);
    List<Incident> findAllByUserId(Long userId);
    boolean existsByIncidentId(String incidentId);
    //List<Incident> findByEmail(String email);

}