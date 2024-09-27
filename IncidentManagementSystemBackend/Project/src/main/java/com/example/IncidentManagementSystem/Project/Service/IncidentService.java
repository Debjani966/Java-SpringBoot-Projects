package com.example.IncidentManagementSystem.Project.Service;

import com.example.IncidentManagementSystem.Project.Exception.IncidentManagementException;
import com.example.IncidentManagementSystem.Project.Exception.IncidentNotFoundException;
import com.example.IncidentManagementSystem.Project.Exception.UserNotFoundException;
import com.example.IncidentManagementSystem.Project.Entity.Incident;
import com.example.IncidentManagementSystem.Project.Entity.Status;
import com.example.IncidentManagementSystem.Project.Entity.User;
import com.example.IncidentManagementSystem.Project.DTO.IncidentRequest;
import com.example.IncidentManagementSystem.Project.Repository.IncidentRepository;
import com.example.IncidentManagementSystem.Project.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class IncidentService {
    Logger logger = LoggerFactory.getLogger(IncidentService.class);

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Incident createIncident(IncidentRequest incidentRequest) {
        logger.info("Create Incident service layer");

        String email=getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        if(user==null)
        {
            throw new UserNotFoundException("User Not Found");
        }
        Incident incident= new Incident(null,generateIncidentId(),incidentRequest.getReporterName(),
                incidentRequest.getIncidentDetails(),LocalDateTime.now(),incidentRequest.getPriority(),
                incidentRequest.getStatus(),user);

        user.getIncidents().add(incident);
        //userRepository.save(user);
        incidentRepository.save(incident);
        return incident;
    }

    public Incident getIncidentByIncidentId(String incidentId) {
        logger.info("Get Incident by IncidentId service layer");
        String currentUserEmail = getCurrentUserEmail();
        Optional<Incident> optionalIncident = incidentRepository.findByIncidentId(incidentId);
        if (optionalIncident.isEmpty()) {
            throw new IncidentNotFoundException("Requested Incident Not Exist");
        }
        Incident incident = optionalIncident.get();
        if (!incident.getUser().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You are not authorized to view this incident");
        }
        return incident;
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();  // assuming username is the email
        } else {
            return principal.toString();
        }
    }

    public List<Incident> getAllIncidentsByUserId(Long userId) {
        logger.info("Get All Incidents By UserId Service Layer");
        return incidentRepository.findAllByUserId(userId);
    }

    public Incident updateIncident(String incidentId, IncidentRequest incidentRequest) {
        logger.info("Update Incident Service Layer");

        //Optional<Incident> existingIncidentOptional = incidentRepository.findByIncidentId(incidentId);
        Optional<Incident> existingIncidentOptional= Optional.ofNullable(getIncidentByIncidentId(incidentId));
        if (existingIncidentOptional.isPresent()) {
            Incident existingIncident = existingIncidentOptional.get();
            if (existingIncident.getStatus() == Status.CLOSED) {
                throw new IncidentManagementException("Cannot update incident with status 'Closed'.",null,HttpStatus.BAD_REQUEST);
            }
            existingIncident.setIncidentDetails(incidentRequest.getIncidentDetails());
            existingIncident.setPriority(incidentRequest.getPriority());
            existingIncident.setStatus(incidentRequest.getStatus());
            existingIncident.setReporterName(incidentRequest.getReporterName());
            Incident updatedIncident = incidentRepository.save(existingIncident);
            return updatedIncident;
        } else {
            throw new IncidentNotFoundException("IncidentId not Found "+incidentId);
        }
    }

    public ResponseEntity<?> deleteIncident(String incidentId) {
        //Optional<Incident> incident = incidentRepository.findByIncidentId(incidentId);
        Optional<Incident> incident= Optional.ofNullable(getIncidentByIncidentId(incidentId));
        if (incident.isEmpty()) {
            return new ResponseEntity<>("Incident not found with ID: " + incidentId, HttpStatus.NOT_FOUND);
        }
        incidentRepository.deleteById(incident.get().getId());
        return new ResponseEntity<>("Incident deleted successfully.", HttpStatus.OK);
    }

    private String generateIncidentId() {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String incidentId;
        do {
            String randomPart = String.format("%05d", new Random().nextInt(99999));
            incidentId = "RMG" + randomPart + year;
        } while (incidentRepository.existsByIncidentId(incidentId));
        return incidentId;
    }


    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public List<Incident> getIncidentsForCurrentUser() {
        String email = getCurrentUsername();
        User user=userRepository.findByEmail(email);
        return incidentRepository.findAllByUserId(user.getId());
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getEmail();
        } else {
            return principal.toString();
        }
    }
    public List<Incident> getIncidentsByUserEmail(String email) {
        User user=userRepository.findByEmail(email);
        return incidentRepository.findAllByUserId(user.getId());
    }
}
