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
import com.book_store.full.validation.AdminServiceValidation;

@Service
public class AdminService {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    AdminServiceValidation admin_validation;

    public String addBook(Book book) {
        try {
            String response = admin_validation.validateBook(book);

            if (response != null) {
                return response;
            }

            book_repo.save(book);
            return "Book added successfully";

        } catch (Exception e) {
            return "Error while adding book";
        }

    }

    public String removeBook(String book_id) {
        try {
            String response = admin_validation.validateBookId(book_id);

            if (response != null) {
                return response;
            }

            book_repo.deleteById(book_id);
            return "Book removed successfully";

        } catch (Exception e) {
            return "Error while adding book";
        }
    }

    public String updateBook(Book book) {
        try {
            String book_id = book.getId();
            String response = admin_validation.validateBookId(book_id);
            String response2 = admin_validation.validateBook(book);

            if (response != null || response2 != null) {
                return response;
            }

            book_repo.save(book);
            return "Book updated successfully";

        } catch (Exception e) {
            return "Error while adding book";
        }
    }

}
