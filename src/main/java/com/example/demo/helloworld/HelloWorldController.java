package com.example.demo.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/helloworld")
@RequiredArgsConstructor

public class HelloWorldController {
    @GetMapping("/sayHellow")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello world");
    }
}
