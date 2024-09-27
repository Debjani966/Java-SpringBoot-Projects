package com.example.IncidentManagementSystem.Project.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String email;
    private String currentPassword;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be more than 8 characters")
    private String newPassword;
}
