package com.example.IncidentManagementSystem.Project.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^RMG\\d{5}\\d{4}$", message = "Incident ID must follow the pattern RMG + 5-digit number + Year")
    @NotNull(message = "Incident ID is mandatory")
    private String incidentId;  // RMG + Random 5-digit number + Year

    @NotBlank(message = "Reporter name is mandatory")
    @Size(max = 100, message = "Reporter name must be less than 100 characters")
    private String reporterName;

    @NotBlank(message = "Incident details are mandatory")
    @Size(max = 1000, message = "Incident details must be less than 1000 characters")
    private String incidentDetails;

    @NotNull(message = "Reported date and time is mandatory")
    private LocalDateTime reportedDateTime;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

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

