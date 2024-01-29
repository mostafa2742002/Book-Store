package com.book_store.full.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.book_store.full.data.Order;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.repository.Order_Repo;

@Service
public class UserService {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    Order_Repo order_repo;

    public ResponseEntity<String> addStar(String userId, String bookId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getStar() == null) {
                user.setStar(new ArrayList<>());
            }

            List<String> stars = new ArrayList<>(user.getStar());

            if (stars.contains(bookId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already in stars");
            }

            stars.add(bookId);
            user.setStar(stars);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Book added to stars successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book to stars");
        }
    }

    public ResponseEntity<String> removeStar(String userId, String bookId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getStar() == null || user.getStar().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No books in stars to remove");
            }

            List<String> stars = new ArrayList<>(user.getStar());

            if (!stars.contains(bookId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not found in stars");
            }

            stars.remove(bookId);
            user.setStar(stars);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Book removed from stars successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing book from stars");
        }
    }

    public ResponseEntity<String> addCart(String userId, String bookId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getCart() == null) {
                user.setCart(new ArrayList<>());
            }

            List<String> carts = new ArrayList<>(user.getCart());

            if (carts.contains(bookId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already in the cart");
            }

            carts.add(bookId);
            user.setCart(carts);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Book added to the cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding book to the cart");
        }
    }

    public ResponseEntity<String> removeCart(String userId, String bookId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getCart() == null || user.getCart().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No books in the cart to remove");
            }

            List<String> carts = new ArrayList<>(user.getCart());

            if (!carts.contains(bookId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book not found in the cart");
            }

            carts.remove(bookId);
            user.setCart(carts);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Book removed from the cart successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing book from the cart");
        }
    }

    public ResponseEntity<String> addOrder(Order order) {
        try {
            Order savedOrder = order_repo.save(order);

            User user = user_repo.findById(order.getUser_id()).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getOrder() == null) {
                user.setOrder(new ArrayList<>());
            }

            List<String> orders = new ArrayList<>(user.getOrder());

            if (orders.contains(savedOrder.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order already associated with the user");
            }

            orders.add(savedOrder.getId());
            user.setOrder(orders);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Order added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding order");
        }
    }

    public ResponseEntity<String> removeOrder(Order order) {
        try {
            order_repo.deleteById(order.getId());

            User user = user_repo.findById(order.getUser_id()).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getOrder() == null || user.getOrder().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No orders to remove");
            }

            List<String> orders = new ArrayList<>(user.getOrder());

            if (!orders.contains(order.getId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order not found for the user");
            }

            orders.remove(order.getId());
            user.setOrder(orders);
            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("Order removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing order");
        }
    }

    public ResponseEntity<List<Order>> getOrders(String userId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            List<String> orderIds = user.getOrder();

            if (orderIds == null || orderIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }

            List<Order> orders = new ArrayList<>();

            for (String id : orderIds) {
                order_repo.findById(id).ifPresent(orders::add);
            }

            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public User get_user(String t, String email, String pass) {
        Optional<User> u = user_repo.findByEmail(email);
        if (u.isEmpty()) {
            return null;
        }
        User user = u.get();
        user.setToken(t);

        return user;
    }
}
