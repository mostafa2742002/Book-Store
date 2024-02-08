package com.book_store.full.dto.userdto;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Document(collection = "users")
public class User implements Serializable{

    @Id
    private String id;
    @NotNull(message = "username shouldn't be null")
    private String name;
    @Email(message = "invalid email address")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9*_#.]{3,20}$",message = "password must be between 3 and 20 characters long and can only contain letters and numbers")
    private String password;
    @Pattern(regexp = "^[0-9]{11}$",message = "invalid mobile number entered ")
    private String phone;
    private String token;
    private String image;
    private String gender;
    private List<String> cart;
    private List<String> star;
    private List<String> order;
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN",message = "Role must be either ROLE_USER or ROLE_ADMIN")
    private String roles;
    private boolean emailVerified;
    private String verificationToken;

}
