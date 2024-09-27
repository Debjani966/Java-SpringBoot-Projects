package com.example.IncidentManagementSystem.Project.DTO;

import com.example.IncidentManagementSystem.Project.Entity.Priority;
import com.example.IncidentManagementSystem.Project.Entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRequest {
    private Long id;

    @NotBlank(message = "Reporter name is mandatory")
    @Size(max = 100, message = "Reporter name must be less than 100 characters")
    private String reporterName;

    @NotBlank(message = "Incident details are mandatory")
    @Size(max = 1000, message = "Incident details must be less than 1000 characters")
    private String incidentDetails;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    protected void onCreate() {
        if (this.priority == null) {
            this.priority = Priority.LOW;  // Default value for priority
        }
        if (this.status == null) {
            this.status = Status.OPEN;  // Default value for status
        }
    }
}
