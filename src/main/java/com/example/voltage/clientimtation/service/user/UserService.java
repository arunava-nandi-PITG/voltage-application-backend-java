package com.example.voltage.clientimtation.service.user;

import com.example.voltage.clientimtation.dto.request.LoginRequest;
import com.example.voltage.clientimtation.dto.request.RegisterRequest;
import com.example.voltage.clientimtation.dto.response.AuthResponse;
import com.example.voltage.clientimtation.model.User;
import org.springframework.http.ResponseEntity;

import javax.management.relation.RoleNotFoundException;

public interface UserService {

    AuthResponse authenticateUser(LoginRequest loginRequest);
    User registerUser(RegisterRequest registerRequest) throws RoleNotFoundException;
}
