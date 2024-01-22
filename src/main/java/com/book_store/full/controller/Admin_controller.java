package com.book_store.full.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {
            @CacheEvict(value = "booksHome", allEntries = true),
            @CacheEvict(value = "booksRecentlyAdded", allEntries = true),
            @CacheEvict(value = "booksTopSelling", allEntries = true)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addBook(@RequestBody Book book) {
        return admin_service.addbook(book);
    }

    @PostMapping("/removebook")
    @Caching(evict = {
            @CacheEvict(value = "booksHome", allEntries = true),
            @CacheEvict(value = "booksRecentlyAdded", allEntries = true),
            @CacheEvict(value = "booksTopSelling", allEntries = true)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeBook(@RequestBody String book_id) {
        return admin_service.removebook(book_id);
    }

    @PostMapping("/updatebook")
    @Caching(put = {
            @CachePut(value = "booksHome"),
            @CachePut(value = "booksRecentlyAdded"),
            @CachePut(value = "booksTopSelling")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateBook(@RequestBody Book book) {
        return admin_service.updatebook(book);
    }

    // get all orders
}
