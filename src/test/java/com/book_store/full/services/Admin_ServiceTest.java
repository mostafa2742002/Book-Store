package com.book_store.full.services;

import org.junit.jupiter.api.Test;
import com.book_store.full.data.Book;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
public class Admin_ServiceTest {

    @Mock
    private Book_Repo book_repo;

    @Mock
    private User_Repo user_repo;

    @InjectMocks
    private Admin_Service admin_Service;

    @Test
    void testAddBook() {

        Book book = new Book();

        admin_Service.addbook(book);

        verify(book_repo, times(1)).save(book);
    }

    @Test
    void testRemoveBook() {

        String bookId = "123";

        admin_Service.removebook(bookId);

        verify(book_repo, times(1)).deleteById(bookId);
    }

    @Test
    void testAddUser() {

        User user = new User();

        admin_Service.adduser(user);

        verify(user_repo, times(1)).save(user);
    }

    @Test
    void testUpdateBook() {

        Book book = new Book();

        admin_Service.updatebook(book);

        verify(book_repo, times(1)).save(book);
    }
}
