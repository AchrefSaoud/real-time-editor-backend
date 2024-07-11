package com.example.demo.controller;

import java.util.List;

import com.example.demo.model.DocumentEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserEntityController {

    private final UserService userService;

    public UserEntityController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/all")
    public List<UserEntity> findAll(){
        return this.userService.findAll();
    }
    
    @GetMapping("/find/{email}")
    public List<DocumentEntity> findDocumentsByUserId(@PathVariable("email") String email){
        return this.userService.findDocumentsByUserId(email);
    }
}
