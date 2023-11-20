package com.book_store.full.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.book_store.full.data.Book;
import com.book_store.full.services.Admin_Service;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class Admin_controller {
    

    @Autowired
    Admin_Service admin_service;

    @PostMapping("/addbook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addbook(@RequestBody Book book) {
        admin_service.addbook(book);
    }

    @PostMapping("/removebook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removebook(@RequestBody String book_id) {
        admin_service.removebook(book_id);
    }
    
    @PostMapping("/updatebook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updatebook(@RequestBody Book book) {
        admin_service.updatebook(book);
    }
}
