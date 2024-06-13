package com.book_store.full.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.book_store.full.dto.Order;

public interface Order_Repo extends MongoRepository <Order, String>{
    Order findTopByNumberByNumberDesc();
    Order findByNumber(int number);
}
