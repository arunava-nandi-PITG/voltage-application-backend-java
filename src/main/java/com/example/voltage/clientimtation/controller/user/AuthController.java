package com.example.voltage.clientimtation.controller.user;

import com.example.voltage.clientimtation.dto.request.LoginRequest;
import com.example.voltage.clientimtation.dto.request.RegisterRequest;
import com.example.voltage.clientimtation.service.user.UserService;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

   private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) throws RoleNotFoundException {
       return userService.registerUser(registerRequest);
    }

}
