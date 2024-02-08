package com.book_store.full.dto.userdto;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class UserResponse  implements Serializable{

    private UserDetails userDetails;
    private User user;

    public UserResponse(UserDetails userDetails, User user) {
        this.userDetails = userDetails;
        this.user = user;
    }

    public UserResponse() {
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public User getUser() {
        return user;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
