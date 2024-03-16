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

import com.book_store.full.dto.bookdto.Book;
import com.book_store.full.services.AdminService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
// @CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    AdminService admin_service;

    @PostMapping("/addbook")
    @Caching(evict = {
            @CacheEvict(value = "booksHome", allEntries = true),
            @CacheEvict(value = "booksRecentlyAdded", allEntries = true),
            @CacheEvict(value = "booksTopSelling", allEntries = true)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addBook(@RequestBody @Valid Book book) {
        return admin_service.addBook(book);
    }

    @PostMapping("/removebook")
    @Caching(evict = {
            @CacheEvict(value = "booksHome", allEntries = true),
            @CacheEvict(value = "booksRecentlyAdded", allEntries = true),
            @CacheEvict(value = "booksTopSelling", allEntries = true)
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String removeBook(@RequestBody @NotNull String book_id) {
        return admin_service.removeBook(book_id);
    }

    @PostMapping("/updatebook")
    @Caching(put = {
            @CachePut(value = "booksHome"),
            @CachePut(value = "booksRecentlyAdded"),
            @CachePut(value = "booksTopSelling")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateBook(@RequestBody @Valid Book book) {
        return admin_service.updateBook(book);
    }

    // get all orders
}
