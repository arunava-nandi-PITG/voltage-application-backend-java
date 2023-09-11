package com.example.voltage.clientimtation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserResponse user;
    private String token;


}
