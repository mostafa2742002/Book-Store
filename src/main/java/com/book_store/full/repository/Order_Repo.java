package com.book_store.full.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.book_store.full.data.Order;

public interface Order_Repo extends MongoRepository <Order, String>{
    
}
