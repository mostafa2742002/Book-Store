package com.book_store.full.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.data.UserResponse;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.security.UserInfoUserDetailsService;
import com.book_store.full.services.Home_Service;
import com.book_store.full.services.JwtService;
import com.book_store.full.services.User_Service;

@RestController
@CrossOrigin(origins = "*")
public class Home_Controller {

    @Autowired
    private Home_Service home_service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private User_Service user_Service;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    private User_Repo user_repo;

    @GetMapping("/home")
    public List<Book> home() {
        return home_service.home();
    }

    @GetMapping("/home/resentllyadded")
    public List<Book> resentllyadded() {
        return home_service.resentllyadded();
    }

    @GetMapping("/home/topselling")
    public List<Book> topselling() {
        return home_service.topselling();
    }

    @PostMapping("/home/addnewuser")
    public String adduser(@RequestBody User user) {
        return home_service.addUser(user);
    }

    @PostMapping("/home/authenticate")
    public User authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String t = jwtService.generateToken(authRequest.getEmail());
            return user_Service.get_user(t, authRequest.getEmail(), authRequest.getPassword());
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    @GetMapping("/home/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        String email = jwtService.extractEmail(token);

        if (email != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token, userDetails)) {
                Optional<User> user = user_repo.findByEmail(email);

                if (user != null) {
                    // Create a custom response JSON object
                    User user1 = user.get();
                    UserResponse userResponse = new UserResponse(userDetails, user1);
                    return ResponseEntity.ok(userResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Token");
        }
    }
}
