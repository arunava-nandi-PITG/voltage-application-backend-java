package com.example.voltage.clientimtation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username must be required")
    private String userName;
    @NotBlank (message = "Email must be required")
    private String email;
    @NotBlank(message = "password must be required")
    private String password;
    @NotBlank(message = "phone number must be required")
    private  String phoneNumber;
    private Set<String> role;

}
