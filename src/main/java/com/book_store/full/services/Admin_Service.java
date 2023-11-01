package com.book_store.full.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;

@Service
public class Admin_Service {
    
    @Autowired
    Book_Repo book_repo;
    
    @Autowired
    User_Repo user_repo;

    public void addbook(Book book) {
        book_repo.save(book);
    }

    public void removebook(String book_id) {
        book_repo.deleteById(book_id);
    }

    public void adduser(User user) {
        user_repo.save(user);
    }
}
