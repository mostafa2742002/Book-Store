package com.book_store.full.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.book_store.full.dto.Order;

public interface Order_Repo extends MongoRepository <Order, String>{
    @Query(value = "{}", sort = "{ 'number' : -1 }")
    Order findTopByOrderByNumberDesc();

    // query to get the number of records in the database
    @Query(value = "{}", count = true)
    int countOrders();
    
    Order findByNumber(int number);
}
