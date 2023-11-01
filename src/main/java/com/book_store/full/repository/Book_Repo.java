package com.book_store.full.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book_store.full.data.Book;

@Repository
public interface Book_Repo extends MongoRepository<Book, String>{
    
}
