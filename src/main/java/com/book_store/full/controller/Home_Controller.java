package com.book_store.full.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
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
    public String adduser(@RequestBody User user)
    {
        return home_service.addUser(user);
    }
    
    @PostMapping("/home/authenticate")
    public User authenticateAndGetToken(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if(authentication.isAuthenticated())
        {
            String t = jwtService.generateToken(authRequest.getEmail());
            return user_Service.get_user(t,authRequest.getEmail(),authRequest.getPassword());
        }
        else 
        {
            throw new RuntimeException("Authentication failed");
        }
    }
}
