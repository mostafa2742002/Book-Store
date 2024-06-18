package com.book_store.full.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.book_store.full.dto.Order;
import com.book_store.full.dto.userdto.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.Order_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.security.UserInfoUserDetailsService;

@Service
public class UserService {

    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;

    @Autowired
    Order_Repo order_repo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

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
            // Find the user associated with the order
            User user = user_repo.findById(order.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Initialize user orders if null
            if (user.getOrder() == null) {
                user.setOrder(new ArrayList<>());
            }

            // Increment and set the order number
            int sum = order_repo.countOrders();
            order.setNumber(sum + 1);

            // Save the order to the database
            order = order_repo.save(order); // Save first to ensure it gets an ID

            // Update user's order list
            user.getOrder().add(order.getId());
            user_repo.save(user);

            return ResponseEntity.ok("Order added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding order: " + e.getMessage());
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

            ArrayList<String> orders = new ArrayList<>(user.getOrder());

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

    public ResponseEntity<ArrayList<Order>> getOrders(String userId) {
        try {
            User user = user_repo.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ArrayList<String> orderIds = user.getOrder();

            if (orderIds == null || orderIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
            }

            ArrayList<Order> orders = new ArrayList<>();

            for (String id : orderIds) {
                Order order = order_repo.findById(id).orElse(null);

                if (order != null) {
                    orders.add(order);
                }
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

    public ResponseEntity<String> updateUser(String user_id, String name, String phone, String image) {
        try {
            User user = user_repo.findById(user_id).orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (name != null && !name.isEmpty())
                user.setName(name);
            if (phone != null && !phone.isEmpty())
                user.setPhone(phone);
            if (image != null && !image.isEmpty())
                user.setImage(image);

            user_repo.save(user);

            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }

    public ResponseEntity<Integer> getOrderByNumber(String number, String user_id) {
        try {
            Order order = order_repo.findByNumber(Integer.parseInt(number));

            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (!order.getUser_id().equals(user_id)
                    && !userDetailsService.loadUserByUsername(user_id).getAuthorities().contains("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(Integer.parseInt(number));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
