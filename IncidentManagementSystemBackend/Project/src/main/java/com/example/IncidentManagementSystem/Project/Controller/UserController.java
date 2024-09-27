package com.example.IncidentManagementSystem.Project.Controller;

import com.example.IncidentManagementSystem.Project.DTO.ChangePasswordRequest;
import com.example.IncidentManagementSystem.Project.Entity.User;
import com.example.IncidentManagementSystem.Project.DTO.UserRequest;
import com.example.IncidentManagementSystem.Project.Service.UserService;
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
@RequestMapping(ApiPaths.User.PATH)
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping(ApiPaths.User.CREATE)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        logger.info("Create User");
        User createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping(ApiPaths.User.UPDATE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequest userRequest) {
        logger.info("Update User");
        Optional<User> updatedUser = userService.updateUser(userRequest);
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(ApiPaths.User.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {

            userService.validateCurrentPassword(changePasswordRequest);
            userService.changePassword(changePasswordRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("Password updated successfully.");

    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Get All Users");
        return userService.getAll();
    }

    /*
    @GetMapping(ApiPaths.User.BY_ID)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Get User By Id");
        User user =userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }*/

    /*@DeleteMapping(ApiPaths.User.BY_ID)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        logger.info("Delete User");
        return userService.deleteUser(id);
    }*/
}
