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

import com.book_store.full.data.Book;
import com.book_store.full.data.Order;
import com.book_store.full.services.Home_Service;
import com.book_store.full.services.User_Service;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins = "*")
public class User_controller {

    @Autowired
    User_Service user_service;

    @Autowired
    private Home_Service home_service;

    @PostMapping("/addstar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addstar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.addStar(user_id, book_id);
    }

    @PostMapping("/removestar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removestar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.removeStar(user_id, book_id);
    }

    @PostMapping("/addcart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addcart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.addCart(user_id, book_id);
    }

    @PostMapping("/removecart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removecart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        return user_service.removeCart(user_id, book_id);
    }

    @PostMapping("/addorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> addorder(@RequestBody Order order) {
        return user_service.addOrder(order);
    }

    @PostMapping("/removeorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> removeorder(@RequestBody Order order) {
        return user_service.removeOrder(order);

    }

    @GetMapping("/getorders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Order>> getorders(@RequestParam String user_id) {
        return user_service.getOrders(user_id);
    }

}
