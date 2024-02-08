package com.book_store.full.dto.authenticationdto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Serializable{
    private String email;
    private String password;
}
