package com.book_store.full.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;

@Component
public class Admin_Service_validation {

    @Autowired
    Book_Repo book_repo;

    public ResponseEntity<String> validatebook(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String book_information = book.getBook_information();
        String price = book.getPrice();
        String category = book.getCategory();


        if (title == null || title.length() == 0) {
            return ResponseEntity.ok("Title cannot be empty");
        }
        if (author == null || author.length() == 0) {
            return ResponseEntity.ok("Author cannot be empty");
        }
        if (book_information == null || book_information.length() == 0) {
            return ResponseEntity.ok("Book information cannot be empty");
        }
        if (price == null || price.length() == 0) {
            return ResponseEntity.ok("Price cannot be empty");
        }
        if (category == null || category.length() == 0) {
            return ResponseEntity.ok("Category cannot be empty");
        }

        return null;
    }

    public ResponseEntity<String> validatebookid(String book_id) {
        if (book_id == null || book_id.length() == 0) {
            return ResponseEntity.ok("Book id cannot be empty");
        }
        if (!book_repo.existsById(book_id)) {
            return ResponseEntity.ok("Book id does not exist");
        }
        return null;
    }

    public ResponseEntity<String> validateuser(User user) {
        String username = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();

        if (username == null || username.length() == 0) {
            return ResponseEntity.ok("Username cannot be empty");
        }
        if (password == null || password.length() == 0) {
            return ResponseEntity.ok("Password cannot be empty");
        }
        if (email == null || email.length() == 0) {
            return ResponseEntity.ok("Email cannot be empty");
        }
        if (phone == null || phone.length() == 0) {
            return ResponseEntity.ok("Phone cannot be empty");
        }

        return null;
    }

    

}
