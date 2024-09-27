package com.example.IncidentManagementSystem.Project.Controller;

import com.example.IncidentManagementSystem.Project.DTO.LoginRequest;
import com.example.IncidentManagementSystem.Project.Service.UserService;
import com.example.IncidentManagementSystem.Project.Util.ApiPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.User.PATH)
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(ApiPaths.User.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.authenticateAndGenerateToken(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body(token);
    }
}
