package com.example.voltage.clientimtation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class TestController {

    @GetMapping("/unAuth")
    public String message(){
        return  "I am unAuth";
    }
}
