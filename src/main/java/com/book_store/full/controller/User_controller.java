package com.book_store.full.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class User_controller {

    @Autowired
    User_Service user_service;

    @Autowired
    private Home_Service home_service;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Book> home() {
        return home_service.home();
    }


    @PostMapping("/addstar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void addstar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        user_service.addstar(user_id, book_id);
    }

    @PostMapping("/removestar")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void removestar(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        user_service.removestar(user_id, book_id);
    }

    @PostMapping("/addcart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void addcart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        user_service.addcart(user_id, book_id);
    }

    @PostMapping("/removecart")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void removecart(@RequestBody JsonNode jsonNode) {
        String user_id = jsonNode.get("user_id").asText();
        String book_id = jsonNode.get("book_id").asText();

        user_service.removecart(user_id, book_id);
    }

    @PostMapping("/addorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void addorder(@RequestBody Order order) {
        user_service.addorder(order);
    }

    @PostMapping("/removeorder")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void removeorder(@RequestBody Order order) {
        user_service.removeorder(order);
        
    }

    @GetMapping("/getorders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Order> getorders(@RequestParam String user_id) {
        return user_service.getorders(user_id);
    }
    
}
