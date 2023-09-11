package com.example.voltage.clientimtation.controller.user;

import com.example.voltage.clientimtation.dto.request.LoginRequest;
import com.example.voltage.clientimtation.dto.request.RegisterRequest;
import com.example.voltage.clientimtation.dto.response.AuthResponse;
import com.example.voltage.clientimtation.model.User;
import com.example.voltage.clientimtation.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws RoleNotFoundException {
       return new ResponseEntity<>(userService.registerUser(registerRequest), HttpStatus.CREATED);
    }

}
