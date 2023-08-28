package com.example.voltage.clientimtation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String phoneNumber;
    private List<String> roles;

    public JwtResponse(Long id, String token, String username, String email, String phoneNumber, List<String> roles) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}


