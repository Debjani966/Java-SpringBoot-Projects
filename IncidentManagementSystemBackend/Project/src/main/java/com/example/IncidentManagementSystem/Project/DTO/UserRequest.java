package com.example.IncidentManagementSystem.Project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;

    @NotNull(message = "Username is mandatory")
    @Size(min = 3,message = "Username must be more than 3 characters")
    private String userName;

    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be more than 8 characters")
    private String password;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    @Pattern(regexp = "^[0-9]{5,10}$", message = "Pin code should be between 5 and 10 digits")
    private String pinCode;

    @Size(max = 100, message = "City must be less than 100 characters")
    private String city;

    @Size(max = 100, message = "Country must be less than 100 characters")
    private String country;
}
