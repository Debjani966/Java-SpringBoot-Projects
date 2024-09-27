package com.example.IncidentManagementSystem.Project.Controller;

import com.example.IncidentManagementSystem.Project.Entity.Incident;
import com.example.IncidentManagementSystem.Project.DTO.IncidentRequest;
import com.example.IncidentManagementSystem.Project.Security.JwtUtil;
import com.example.IncidentManagementSystem.Project.Service.IncidentService;
import com.example.IncidentManagementSystem.Project.Util.ApiPaths;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(ApiPaths.Incident.PATH)
public class IncidentController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IncidentService incidentService;

    Logger logger=LoggerFactory.getLogger(IncidentController.class);

    @PostMapping
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody IncidentRequest incidentRequest) {
        logger.info("Creating Incident");
        Incident createdIncident = incidentService.createIncident(incidentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIncident);

    }

    @GetMapping(ApiPaths.Incident.SEARCH_BY_INCIDENT_ID)
    public ResponseEntity<Incident> searchIncidentByIncidentId(@PathVariable String incidentId) {
        logger.info("Get Incident By IncidentId if it belong to the logged in user");
        Incident incident = incidentService.getIncidentByIncidentId(incidentId);
        return ResponseEntity.status(HttpStatus.FOUND).body(incident);
    }

    @PutMapping(ApiPaths.Incident.BY_INCIDENT_ID)
    public ResponseEntity<Incident> updateIncident(@PathVariable String incidentId, @Valid @RequestBody IncidentRequest incidentRequest) {
        logger.info("Update Incident if it belong to the logged in user");
        Incident updatedIncident = incidentService.updateIncident(incidentId, incidentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedIncident);
    }

    @GetMapping(ApiPaths.Incident.MY_INCIDENTS)
    public List<Incident> getMyIncidents(@RequestHeader("Authorization") String token) {
        logger.info("Display Incident list of authenticated user");
        String email = jwtUtil.extractEmail(token.substring(7));
        return incidentService.getIncidentsByUserEmail(email);
    }

    @DeleteMapping(ApiPaths.Incident.BY_INCIDENT_ID)
    public ResponseEntity<?> deleteIncident(@PathVariable String incidentId) {
        logger.info("Delete Incident of authenticated user");
        return incidentService.deleteIncident(incidentId);
    }

    /*@GetMapping
    public List<Incident> getAllIncidents()
    {
        logger.info("Get All Incidents");
        return incidentService.getAllIncidents();
    }*/

    /*@GetMapping(ApiPaths.Incident.BY_INCIDENT_ID)
    public ResponseEntity<Incident> getIncidentByIncidentId(@PathVariable String incidentId) {
        logger.info("Get Incident By IncidentId");
        Incident incident = incidentService.getIncidentByIncidentId(incidentId);
        return ResponseEntity.status(HttpStatus.FOUND).body(incident);
    }*/

    /*@GetMapping(ApiPaths.Incident.USER_ID)
    public ResponseEntity<List<Incident>> getAllIncidentsByUserId(@PathVariable Long userId) {
        logger.info("Get All Incidents By UserId");
        List<Incident> incidents = incidentService.getAllIncidentsByUserId(userId);
        return ResponseEntity.ok(incidents);
    }*/
}
