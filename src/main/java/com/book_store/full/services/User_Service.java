package com.book_store.full.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book_store.full.data.Order;
import com.book_store.full.data.User;
import com.book_store.full.repository.Book_Repo;
import com.book_store.full.repository.User_Repo;
import com.book_store.full.repository.Order_Repo;

@Service
public class User_Service {
    
    @Autowired
    Book_Repo book_repo;

    @Autowired
    User_Repo user_repo;
    
    @Autowired
    Order_Repo order_repo;
    

    public void addstar(String user_id, String Book_id) {
        User user = user_repo.findById(user_id).get();
        if (user.getStar() == null) {
            user.setStar(new ArrayList<String>());
        }
        List<String> stars = new ArrayList<>(user.getStar());
        stars.add(Book_id);
        user.setStar(stars);
        user_repo.save(user);
    }

    public void removestar(String user_id, String Book_id) {
        User user = user_repo.findById(user_id).get();
        List<String> stars = new ArrayList<>(user.getStar());
        stars.remove(Book_id);
        user.setStar(stars);
        user_repo.save(user);
    }

    public void addcart(String user_id, String Book_id) {
        User user = user_repo.findById(user_id).get();
        if (user.getCart() == null) {
            user.setCart(new ArrayList<String>());
        }
        List<String> carts = new ArrayList<>(user.getCart());
        carts.add(Book_id);
        user.setCart(carts);
        user_repo.save(user);
    }


    public void removecart(String user_id, String Book_id) {
        User user = user_repo.findById(user_id).get();
        List<String> carts = new ArrayList<>(user.getCart());
        carts.remove(Book_id);
        user.setCart(carts);
        user_repo.save(user);
    }

    public void addorder(Order order) {
        Order o = order_repo.save(order);
        User user = user_repo.findById(order.getUser_id()).get();

        if (user.getOrder() == null) {
            user.setOrder(new ArrayList<String>());
        }

        List<String> orders = new ArrayList<>(user.getOrder());
        orders.add(o.getId());
        user.setOrder(orders);
        user_repo.save(user);
    }

    public void removeorder(Order order) {
        order_repo.deleteById(order.getId());
        User user = user_repo.findById(order.getUser_id()).get();
        List<String> orders = new ArrayList<>(user.getOrder());
        orders.remove(order.getId());
        user.setOrder(orders);
        user_repo.save(user);
    }

    public List<Order> getorders(String user_id) {
        User user = user_repo.findById(user_id).get();
        List<String> orders = new ArrayList<>(user.getOrder());
        List<Order> order = new ArrayList<>();
        for (String id : orders) {
            order.add(order_repo.findById(id).get());
        }
        return order;
    }

    public User get_user(String t, String email,String pass)
    {
        Optional<User> u = user_repo.findByEmail(email);
        if(u.isEmpty())
        {
            return null;
        }
        User user = u.get();
        user.setToken(t);

        return user;
    }
}
