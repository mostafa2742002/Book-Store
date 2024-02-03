package com.book_store.full.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.data.AuthRequest;
import com.book_store.full.data.AuthResponse;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.services.HomeService;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService home_service;

    @GetMapping("/home")
    @Cacheable(value = "booksHome")
    public List<Book> home() {
        return home_service.home();
    }

    @GetMapping("/home/resentllyadded")
    @Cacheable(value = "booksRecentlyAdded")
    public List<Book> recentlyAdded() {
        System.out.println("recently added");
        return home_service.resentllyAdded();
    }

    @GetMapping("/home/topselling")
    @Cacheable(value = "booksTopSelling")
    public List<Book> topSelling() {
        return home_service.topSelling();
    }

    @PostMapping("/home/addnewuser")
    public ResponseEntity<String> adduser(@RequestBody User user) {
        return home_service.addUser(user);
    }

    @GetMapping("/home/verifyemail")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return home_service.verifyEmail(token);
    }

    @PostMapping("/home/authenticate")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return home_service.authenticateAndGetToken(authRequest);
    }

    @PostMapping("/home/validateToken")
    public ResponseEntity<?> validateToken(@RequestBody AuthResponse t) {
        String token = t.getAccess_token();
        return home_service.validateToken(token);
    }

    @PostMapping("/home/refreshtoken")
    public String refreshToken(@RequestBody AuthResponse token) {
        return home_service.refreshToken(token.getRefresh_token());
    }

    @GetMapping("/home/search")
    public ResponseEntity<List<Book>> search(@RequestParam String search) {
        return home_service.search(search);
    }

}
