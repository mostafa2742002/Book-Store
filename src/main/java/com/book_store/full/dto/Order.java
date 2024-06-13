package com.book_store.full.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.book_store.full.dto.bookdto.BookQuantity;

import lombok.Data;

@Data
@Document(collection = "orders")
public class Order implements Serializable{
    
    @Id
    private String id;

    private int number = 0;
    private String user_id;
    private List<BookQuantity> books; // book_id, quantity
    private String address;
    private String phone;
    private String total;
    private String payment; // "cash" or "fawry"
    private String status = "pending"; 
}
