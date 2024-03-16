package com.book_store.full.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.dto.Order;
import com.book_store.full.dto.authenticationdto.AuthResponse;
import com.book_store.full.dto.bookdto.Book;
import com.book_store.full.services.HomeService;
import com.book_store.full.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
// @CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService user_service;

    @Autowired
    private HomeService home_service;

    @PostMapping("/addstar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.addStar(user_id, book_id);
    }

    @PostMapping("/removestar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removeStar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.removeStar(user_id, book_id);
    }

    @PostMapping("/addcart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.addCart(user_id, book_id);
    }

    @PostMapping("/removecart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removeCart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.removeCart(user_id, book_id);
    }

    @PostMapping("/addorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        return user_service.addOrder(order);
    }

    @PostMapping("/removeorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removeOrder(@RequestBody Order order) {
        return user_service.removeOrder(order);

    }

    @GetMapping("/getorders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Order>> getOrders(@RequestParam String user_id) {
        return user_service.getOrders(user_id);
    }

}
