package com.book_store.full.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;

@Service
public class Home_Service {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Book> home() {
        return book_repo.findAll();
    }

    public List<Book> resentllyadded() {
        List<Book> books = book_repo.findAll();
        if (books.size() > 10) {
            return books.subList(0, 4);
        }
        return books;
    }

    public List<Book> topselling() {
        List<Book> books = book_repo.findAll(Sort.by(Sort.Direction.DESC, "buyed"));
        if (books.size() > 10) {
            return books.subList(0, 4);
        }
        return books;
    }

    public String addUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user_repo.save(user);
        return "User Added";
    }
}
