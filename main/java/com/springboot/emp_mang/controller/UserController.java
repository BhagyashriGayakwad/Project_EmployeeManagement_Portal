package com.springboot.emp_mang.controller;

import com.springboot.emp_mang.config.JwtHelper;
import com.springboot.emp_mang.dto.UserRequest;
import com.springboot.emp_mang.entities.User;
import com.springboot.emp_mang.repository.UserRepository;
import com.springboot.emp_mang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserRequest userRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password");
        }

        final UserDetails userDetails = userService.loadUserByUsername(userRequest.getEmail());
        final String jwt = jwtHelper.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }


    @GetMapping("/api/users/role")
    public ResponseEntity<?> getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        UserDetails userDetails = userService.loadUserByUsername(userEmail);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(role);
    }
    @GetMapping("/api/users/email")
    public ResponseEntity<?> getUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }
        User user = optionalUser.get();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            authentication.setAuthenticated(false);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return ResponseEntity.ok("Successfully logged out");
    }

}
