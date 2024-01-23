package com.book_store.full.validation;

import org.springframework.stereotype.Component;

import com.book_store.full.data.User;

@Component
public class Home_Service_validation {

    public String validate_user(User user) {

        String username = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        String role = user.getRoles();

        if (username== null || !username.matches("^[a-zA-Z0-9 ]{3,20}$")) {
            System.out.println(username);
            return "Username must be between 3 and 20 characters long and can only contain letters, numbers, and spaces";
        }   

        if (password == null || !password.matches("^[a-zA-Z0-9*_#.]{3,20}$")) {
            return "Password must be between 3 and 20 characters long and can only contain letters and numbers";
        }

        if (email == null || !email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            return "Email must be valid";
        }

        if (phone == null || !phone.matches("^[0-9]{11}$")) {
            return "Phone must be 10 digits long";
        }

        if (role == null || !role.matches("ROLE_USER|ROLE_ADMIN")) {
            return "Role must be either ROLE_USER or ROLE_ADMIN";
        }

        return null;
    }
}
