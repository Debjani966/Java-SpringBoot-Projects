package com.example.IncidentManagementSystem.Project.Service;

import com.example.IncidentManagementSystem.Project.DTO.ChangePasswordRequest;
import com.example.IncidentManagementSystem.Project.Entity.User;
import com.example.IncidentManagementSystem.Project.Exception.EmailAlreadyExistsException;
import com.example.IncidentManagementSystem.Project.Exception.IncidentManagementException;
import com.example.IncidentManagementSystem.Project.DTO.UserRequest;
import com.example.IncidentManagementSystem.Project.DTO.LocationResponse;
import com.example.IncidentManagementSystem.Project.Repository.UserRepository;
import com.example.IncidentManagementSystem.Project.Security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    Logger logger= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    GeolocationService geolocationService;

    public User createUser(UserRequest userRequest) {
        LocationResponse location = geolocationService.getLocationByPinCode(userRequest.getPinCode());
        userRequest.setCity(location.getCity());
        userRequest.setCountry(location.getCountry());

        User user= new User(userRequest.getId(),userRequest.getUserName(),this.passwordEncoder.encode(userRequest.getPassword()),
                userRequest.getEmail(),userRequest.getPhoneNumber(),userRequest.getAddress(),
                userRequest.getPinCode(),userRequest.getCity(),userRequest.getCountry(),null);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        return userRepository.save(user);
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();  // assuming username is the email
        } else {
            return principal.toString();
        }
    }

    @PreAuthorize("isAuthenticated()")
    public Optional<User> updateUser(UserRequest userRequest) {
        String currentUsername = getCurrentUserEmail();

        User user = userRepository.findByEmail(currentUsername);

        LocationResponse location = geolocationService.getLocationByPinCode(userRequest.getPinCode());
        userRequest.setCity(location.getCity());
        userRequest.setCountry(location.getCountry());

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());
        user.setPinCode(userRequest.getPinCode());
        user.setCity(userRequest.getCity());
        user.setCountry(userRequest.getCountry());

        userRepository.save(user);
        return Optional.of(user);
    }

    /*public ResponseEntity<?> deleteUser(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(user.get().getId());
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.OK);
    }*/

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String authenticateAndGenerateToken(String email, String password) {
        String s=validateEmailAndPassword(email, password);
        if(s==null) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtUtil.generateToken(email);
        }
        return s;
    }

    public String validateEmailAndPassword(String email, String password)
    {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (user.isEmpty()) {
            throw new IncidentManagementException("User/Email not found ",
                    null, HttpStatus.NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {

            throw new IncidentManagementException("Invalid current password ",
                    null, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    public String validateCurrentPassword(ChangePasswordRequest changePasswordRequest) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(changePasswordRequest.getEmail()));

        if (user.isEmpty()) {
            throw new IncidentManagementException("User not found ",
                    null, HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.get().getPassword())) {

            throw new IncidentManagementException("Invalid current password ",
                    null, HttpStatus.NOT_FOUND);
        }
        return null;
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(changePasswordRequest.getEmail()));
        user.get().setPassword(this.passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user.get());
    }


    /*public LoginMessage loginUser(LoginRequest loginDto) {
        String msg="";
        User user1=userRepository.findByEmail(loginDto.getEmail());
        if(user1!=null)
        {
            String password=loginDto.getPassword();
            String encodedPassword=user1.getPassword();
            boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
            if(isPwdRight)
            {
                Optional<User> user=userRepository.findOneByEmailAndPassword(loginDto.getEmail(),encodedPassword);
                if(user.isPresent()){
                    return  new LoginMessage("Login Success",true);
                } else {
                    return new LoginMessage("Login Failed", false);
                }
            }else {
                return new LoginMessage("password Not Match", false);
            }
        }else {
            return new LoginMessage("Email not exits", false);
        }
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
        {
            throw new UserNotFoundException("User Not Found "+id);
        }
        return user.orElse(null);
    }*/

//    public Optional<User> updateUser(Long id, UserRequest userRequest) {
//        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found "+id));
//        LocationResponse location = geolocationService.getLocationByPinCode(userRequest.getPinCode());
//        userRequest.setCity(location.getCity());
//        userRequest.setCountry(location.getCountry());
//
//        user.setIncidents(user.getIncidents());
//        user.setUserName(userRequest.getUserName());
//        user.setEmail(userRequest.getEmail());
//        user.setPhoneNumber(userRequest.getPhoneNumber());
//        user.setAddress(userRequest.getAddress());
//        user.setPinCode(userRequest.getPinCode());
//        user.setCity(userRequest.getCity());
//        user.setCountry(userRequest.getCountry());
//        //User updatedUser=userRepository.save(user);
//        return Optional.of(user);
//    }
}
