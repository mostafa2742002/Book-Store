package com.book_store.full.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book_store.full.dto.userdto.User;

@Repository
public interface User_Repo extends MongoRepository <User, String>{
    
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
}
