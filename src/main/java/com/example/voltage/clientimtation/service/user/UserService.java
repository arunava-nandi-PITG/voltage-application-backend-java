package com.example.voltage.clientimtation.service.user;

import com.example.voltage.clientimtation.dto.request.LoginRequest;
import com.example.voltage.clientimtation.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

import javax.management.relation.RoleNotFoundException;

public interface UserService {

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(RegisterRequest registerRequest) throws RoleNotFoundException;
}
