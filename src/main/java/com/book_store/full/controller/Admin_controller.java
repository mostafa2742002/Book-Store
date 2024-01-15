package com.book_store.full.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.data.Book;
import com.book_store.full.services.Admin_Service;

@RestController
@CrossOrigin(origins = "*")
public class Admin_controller {
    
    @Autowired
    Admin_Service admin_service;

    @PostMapping("/addbook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addbook(@RequestBody Book book) {
        return admin_service.addbook(book);
    }

    @PostMapping("/removebook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> removebook(@RequestBody String book_id) {
        return admin_service.removebook(book_id);
    }
    
    @PostMapping("/updatebook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updatebook(@RequestBody Book book) {
        return admin_service.updatebook(book);
    }

    // get all orders 
}
