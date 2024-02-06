package com.book_store.full.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "bookstore")
public class BookElasticsearch {
    @Id
    private String id;
    private String title;
    private String author;
    private String category;
    private String translator;
    private String publisher;
    private String price;
    private String book_information;
    private String author_information;
    private String image;
    private int buyed;

    // public BookElasticsearch(Book book) {
    //     this.id = book.getId();
    //     this.title = book.getTitle();
    //     this.author = book.getAuthor();
    //     this.category = book.getCategory();
    //     this.translator = book.getTranslator();
    //     this.publisher = book.getPublisher();
    //     this.price = book.getPrice();
    //     this.book_information = book.getBook_information();
    //     this.author_information = book.getAuthor_information();
    //     this.image = book.getImage();
    //     this.buyed = book.getBuyed();
    // }

}
