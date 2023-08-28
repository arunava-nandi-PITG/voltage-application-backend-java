package com.example.voltage.clientimtation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userName;
    private String email;
    private String password;
    private  String phoneNumber;
    private Set<String> role;


}
