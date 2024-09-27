/*package com.example.IncidentManagementSystem.Project.Repository;

import com.example.IncidentManagementSystem.Project.Entity.Incident;
import com.example.IncidentManagementSystem.Project.Entity.Priority;
import com.example.IncidentManagementSystem.Project.Entity.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@DataJpaTest
public class IncidentRepositoryTest {

    @Autowired
    private IncidentRepository incidentRepository;

    Incident incident;

    @BeforeEach
    void Setup()
    {
        LocalDateTime time=LocalDateTime.now();
        incident=new Incident(null, "RMG640302024", "Debjani", "demo", time, Priority.LOW, Status.OPEN, null);
        incidentRepository.save(incident);
    }

    @AfterEach
    void remove()
    {
        incident=null;
        incidentRepository.deleteAll();
    }

    //Test case for success
    @Test
    void testByIncidentId()
    {
        Optional<Incident> incident1=incidentRepository.findByIncidentId("RMG640302024");
        assertThat(incident1.isEmpty()).isTrue();
    }

}*/
