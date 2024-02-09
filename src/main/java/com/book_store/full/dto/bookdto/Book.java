package com.book_store.full.dto.bookdto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import lombok.Data;

@Data
@Document(collection = "books")
public class Book implements Serializable{
    
    @Id
    private String  id;

    @NotNull(message = "Title cannot be empty")
    private String  title;

    @NotNull(message = "Author cannot be empty")
    private String  author;

    @NotNull(message = "Category cannot be empty")
    private String  category;
    
    @NotNull(message = "Price cannot be empty")
    private String  price;

    @NotNull(message = "Book information cannot be empty")
    private String  book_information;
    
    private String  translator;
    private String  publisher;
    private String  author_information;
    private String  image;
    private int     buyed;
}
