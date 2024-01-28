package com.book_store.full.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.book_store.full.data.Book;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.validation.Admin_Service_validation;

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
public class Admin_ServiceTest {

    @Mock
    private Book_Repo book_repo;

    @Mock
    private User_Repo user_repo;

    @InjectMocks
    private Admin_Service admin_Service;

    @Mock
    private Admin_Service_validation admin_validation;

    @Test
    void testAddBook() {
        
        Book book = new Book();
        when(admin_validation.validatebook(book)).thenReturn(null); 


        
        String added = admin_Service.addbook(book);

        assertNotNull(added);
        verify(book_repo, times(1)).save(book);
        assertEquals("Book added successfully", added);
    }

    @Test
    void testRemoveBook() {
        
        String bookId = "123";
        when(admin_validation.validatebookid(bookId)).thenReturn(null);

        
        String removed = admin_Service.removebook(bookId);

        
        assertNotNull(removed);
        verify(book_repo, times(1)).deleteById(bookId);
        assertEquals("Book removed successfully", removed);
    }

    @Test
    void testUpdateBook() {
        
        Book book = new Book();
        when(admin_validation.validatebookid(book.getId())).thenReturn(null);
        when(admin_validation.validatebook(book)).thenReturn(null);

        
        String updated = admin_Service.updatebook(book);

        
        assertNotNull(updated);
        verify(book_repo, times(1)).save(book);
        assertEquals("Book updated successfully", updated);
    }
}
