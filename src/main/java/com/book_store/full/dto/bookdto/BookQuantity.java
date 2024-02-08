package com.book_store.full.dto.bookdto;

import java.io.Serializable;

import lombok.Data;

@Data
public class BookQuantity implements Serializable{
    private String bookId;
    private int quantity;
}
