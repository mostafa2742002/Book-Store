package com.book_store.full.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class BookQuantity implements Serializable{
    private String bookId;
    private int quantity;
}
