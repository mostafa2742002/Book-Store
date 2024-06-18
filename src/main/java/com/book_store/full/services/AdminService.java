package com.book_store.full.services;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.book_store.full.dto.Order;
import com.book_store.full.dto.bookdto.Book;
// import com.book_store.full.dto.bookdto.BookElasticsearch;
import com.book_store.full.dto.userdto.User;
// import com.book_store.full.repository.BookElasticsearchRepository;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.Order_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.validation.AdminServiceValidation;
// import java.*;
import java.util.ArrayList;

import jakarta.validation.OverridesAttribute.List;

@Service
public class AdminService {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    Order_Repo order_repo;

    @Autowired
    AdminServiceValidation admin_validation;

    // @Autowired
    // BookElasticsearchRepository book_elastic_repo;

    public String addBook(Book book) {

        // String response = admin_validation.validateBook(book);

        // if (response != null) {
        // return response;
        // }

        // save to mongodb
        book_repo.save(book);

        // save to elasticsearch
        // BookElasticsearch book_elastic = new BookElasticsearch();
        // book_elastic.setId(book.getId());
        // book_elastic.setTitle(book.getTitle());
        // book_elastic.setAuthor(book.getAuthor());
        // book_elastic.setCategory(book.getCategory());
        // book_elastic.setTranslator(book.getTranslator());
        // book_elastic.setPublisher(book.getPublisher());
        // book_elastic.setPrice(book.getPrice());
        // book_elastic.setBook_information(book.getBook_information());
        // book_elastic.setAuthor_information(book.getAuthor_information());
        // book_elastic.setImage(book.getImage());
        // book_elastic.setBuyed(book.getBuyed());

        // save to elasticsearch
        // book_elastic_repo.save(book_elastic);

        return "Book added successfully";

    }

    public String removeBook(String book_id) {

        // String response = admin_validation.validateBookId(book_id);

        // if (response != null) {
        // return response;
        // }

        book_repo.deleteById(book_id);
        return "Book removed successfully";
    }

    public String updateBook(Book book) {
        // String book_id = book.getId();
        // String response = admin_validation.validateBookId(book_id);
        // String response2 = admin_validation.validateBook(book);

        // if (response != null || response2 != null) {
        // return response;
        // }

        book_repo.save(book);
        return "Book updated successfully";

    }

    public ResponseEntity<ArrayList<Order>> getAllOrders() {
        ArrayList<Order> orders = (ArrayList<Order>) order_repo.findAll();
        ArrayList<Order> response = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.getStatus().equals("pending")) {
                response.add(order);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> updateOrderStatus(String order_id, String status) {

        if (order_id == null || status == null) {
            return new ResponseEntity<>("Order id or status is null", HttpStatus.BAD_REQUEST);
        }

        Order order = order_repo.findById(order_id).orElse(null);

        if (order == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        order.setStatus(status);
        order_repo.save(order);

        return new ResponseEntity<>("Order status updated successfully", HttpStatus.OK);
    }

}
