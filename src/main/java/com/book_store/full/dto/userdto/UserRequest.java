package com.book_store.full.dto.userdto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "username shouldn't be null")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9*_#.]{3,20}$",message = "password must be between 3 and 20 characters long and can only contain letters and numbers")
    private String password;
    @Email(message = "invalid email address")
    private String email;
    @Pattern(regexp = "^[0-9]{11}$",message = "invalid mobile number entered ")
    private String phone;
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN",message = "Role must be either ROLE_USER or ROLE_ADMIN")
    private String roles;
}
