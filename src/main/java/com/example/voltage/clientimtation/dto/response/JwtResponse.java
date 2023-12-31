package com.example.voltage.clientimtation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private List<String> roles;
    private Set<TokenResponse> token;
    private String type = "Bearer ";


}


