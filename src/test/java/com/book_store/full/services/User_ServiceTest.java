package com.book_store.full.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.book_store.full.data.Order;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.Order_Repo;
import com.book_store.full.repository.User_Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@SpringJUnitConfig
public class User_ServiceTest {

    @Mock
    private Book_Repo book_repo;

    @Mock
    private User_Repo user_repo;

    @Mock
    private Order_Repo order_repo;

    @InjectMocks
    private User_Service user_Service;

    @Test
    void testAddStar() {
        // Arrange
        User user = new User();
        user.setId("1");
        user.setStar(new ArrayList<>());

        when(user_repo.findById("1")).thenReturn(Optional.of(user));

        // Act
        user_Service.addstar("1", "bookId");

        // Assert
        verify(user_repo, times(1)).save(user);
        assertEquals(1, user.getStar().size());
    }

    @Test
    void testRemoveStar() {
        // Arrange
        User user = new User();
        user.setId("1");
        user.setStar(new ArrayList<>());
        user.getStar().add("bookId");

        when(user_repo.findById("1")).thenReturn(Optional.of(user));

        // Act
        user_Service.removestar("1", "bookId");

        // Assert
        verify(user_repo, times(1)).save(user);
        assertEquals(0, user.getStar().size());
    }

    @Test
    void testAddCart() {
        // Arrange
        User user = new User();
        user.setId("1");
        user.setCart(new ArrayList<>());

        when(user_repo.findById("1")).thenReturn(Optional.of(user));

        // Act
        user_Service.addcart("1", "bookId");

        // Assert
        verify(user_repo, times(1)).save(user);
        assertEquals(1, user.getCart().size());
    }

    @Test
    void testRemoveCart() {
        // Arrange
        User user = new User();
        user.setId("1");
        user.setCart(new ArrayList<>());
        user.getCart().add("bookId");

        when(user_repo.findById("1")).thenReturn(Optional.of(user));

        // Act
        user_Service.removecart("1", "bookId");

        // Assert
        verify(user_repo, times(1)).save(user);
        assertEquals(0, user.getCart().size());
    }

    @Test
    void testAddOrder() {
        // Arrange
        Order order = new Order();
        order.setId("orderId");
        order.setUser_id("userId");

        User user = new User();
        user.setId("userId");
        user.setOrder(new ArrayList<>());

        when(order_repo.save(order)).thenReturn(order);
        when(user_repo.findById("userId")).thenReturn(Optional.of(user));

        // Act
        user_Service.addorder(order);

        // Assert
        verify(order_repo, times(1)).save(order);
        verify(user_repo, times(1)).save(user);
        assertEquals(1, user.getOrder().size());
    }

    // @Test
    // void testRemoveOrder() {
    //     Order order1 = new Order();
    //     order1.setId("1");

    //     User user1 = new User();
    //     user1.setId("1");
    //     user1.setOrder(new ArrayList<>());
    //     user1.getOrder().add("1");

    //     when(order_repo.findById("1")).thenReturn(Optional.of(order1));
    //     when(user_repo.findById("1")).thenReturn(Optional.of(user1));

    //     doNothing().when(order_repo).deleteById(any());

    //     user_Service.removeorder(order1);

    //     verify(order_repo, times(1)).deleteById("1");
    //     verify(user_repo, times(1)).save(user1);
    //     assertEquals(0, user1.getOrder().size());

    // }

    @Test
    void testGetOrders() {
        // Arrange
        User user = new User();
        user.setId("userId");
        user.setOrder(new ArrayList<>());
        user.getOrder().add("orderId");

        Order order = new Order();
        order.setId("orderId");

        when(user_repo.findById("userId")).thenReturn(Optional.of(user));
        when(order_repo.findById("orderId")).thenReturn(Optional.of(order));

        // Act
        List<Order> orders = user_Service.getorders("userId");

        // Assert
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("orderId", orders.get(0).getId());
    }

    @Test
    void testGetUser() {
        // Arrange
        String token = "token";
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(user_repo.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = user_Service.get_user(token, email, password);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(password, result.getPassword());

    }
}