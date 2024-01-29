package com.book_store.full.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;

@Component
public class AdminServiceValidation {

    @Autowired
    Book_Repo book_repo;

    public String validateBook(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String book_information = book.getBook_information();
        String price = book.getPrice();
        String category = book.getCategory();

        if (title == null || title.length() == 0) {
            return "Title cannot be empty";
        }
        if (author == null || author.length() == 0) {
            return "Author cannot be empty";
        }
        if (book_information == null || book_information.length() == 0) {
            return "Book information cannot be empty";
        }
        if (price == null || price.length() == 0) {
            return "Price cannot be empty";
        }
        if (category == null || category.length() == 0) {
            return "Category cannot be empty";
        }

        return null;
    }

    public String validateBookId(String book_id) {
        if (book_id == null || book_id.length() == 0) {
            return "Book id cannot be empty";
        }
        if (!book_repo.existsById(book_id)) {
            return "Book id does not exist";
        }
        return null;
    }

    public String validateUser(User user) {
        String username = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();

        if (username == null || username.length() == 0) {
            return "Username cannot be empty";
        }
        if (password == null || password.length() == 0) {
            return "Password cannot be empty";
        }
        if (email == null || email.length() == 0) {
            return "Email cannot be empty";
        }
        if (phone == null || phone.length() == 0) {
            return "Phone cannot be empty";
        }

        return null;
    }

}
