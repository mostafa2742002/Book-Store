package com.book_store.full.services;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.validation.Admin_Service_validation;

@Service
public class Admin_Service {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    Admin_Service_validation admin_validation;

    public ResponseEntity<String> addbook(Book book) {
        try {
            ResponseEntity<String> response = admin_validation.validatebook(book);

            if (response != null) {
                return response;
            }

            book_repo.save(book);
            return ResponseEntity.ok("Book added successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while adding book");
        }
        
    }

    public ResponseEntity<String> removebook(String book_id) {
        try {
            ResponseEntity<String> response = admin_validation.validatebookid(book_id);

            if (response != null) {
                return response;
            }

            book_repo.deleteById(book_id);
            return ResponseEntity.ok("Book removed successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while adding book");
        }
    }

    public ResponseEntity<String> updatebook(Book book) {
        try {
            String book_id = book.getId();
            ResponseEntity<String> response = admin_validation.validatebookid(book_id);
            ResponseEntity<String> response2 = admin_validation.validatebook(book);
            
            if (response != null || response2 != null) {
                return response;
            }

            book_repo.save(book);
            return ResponseEntity.ok("Book updated successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while adding book");
        }
    }

}
