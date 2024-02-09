package com.book_store.full.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.book_store.full.dto.bookdto.Book;
import com.book_store.full.dto.bookdto.BookElasticsearch;
// import com.book_store.full.repository.BookElasticsearchRepository;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.validation.AdminServiceValidation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringJUnitConfig
public class AdminServiceTest {

    @Mock
    private Book_Repo book_repo;

    @Mock
    private User_Repo user_repo;

    @InjectMocks
    private AdminService admin_Service;

    @Mock
    private AdminServiceValidation admin_validation;

    // @Mock
    // private BookElasticsearchRepository book_elastic_repo;

    @Test
    void testAddBook() {
        
        Book book = new Book();
        BookElasticsearch book_elastic = new BookElasticsearch();
        // when(admin_validation.validateBook(book)).thenReturn(null); 
        // when(book_elastic_repo.save(book_elastic)).thenReturn(null); 
        when(book_repo.save(book)).thenReturn(book);

        
        String added = admin_Service.addBook(book);

        assertNotNull(added);
        verify(book_repo, times(1)).save(book);
        assertEquals("Book added successfully", added);
    }

    @Test
    void testRemoveBook() {
        
        String bookId = "123";
        // when(admin_validation.validateBookId(bookId)).thenReturn(null);

        
        String removed = admin_Service.removeBook(bookId);

        
        assertNotNull(removed);
        verify(book_repo, times(1)).deleteById(bookId);
        assertEquals("Book removed successfully", removed);
    }

    @Test
    void testUpdateBook() {
        
        Book book = new Book();
        // when(admin_validation.validateBookId(book.getId())).thenReturn(null);
        // when(admin_validation.validateBook(book)).thenReturn(null);

        
        String updated = admin_Service.updateBook(book);

        
        assertNotNull(updated);
        verify(book_repo, times(1)).save(book);
        assertEquals("Book updated successfully", updated);
    }
}
