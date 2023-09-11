package com.example.voltage.clientimtation.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/authentication")
public class TestController {

    @GetMapping("/userAuth")
    public String message(){
        return  "I am USER";
    }


    @GetMapping("/adminAuth")
    public String admin(){
        return  "I am ADMIN";
    }
}
