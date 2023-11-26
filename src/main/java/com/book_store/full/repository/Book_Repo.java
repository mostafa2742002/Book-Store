package com.book_store.full.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book_store.full.data.Book;

@Repository
public interface Book_Repo extends MongoRepository<Book, String>{
    
    // find by title or author or category or translator or publisher
    List<Book> findByTitleIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrCategoryIgnoreCaseContainingOrTranslatorIgnoreCaseContainingOrPublisherIgnoreCaseContaining(String title, String author, String category, String translator, String publisher);
}
