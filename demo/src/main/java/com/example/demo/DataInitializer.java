package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.DocumentEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.service.DocumentService;
import com.example.demo.service.UserService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(DocumentService documentService, UserService userService) {
        return args -> {/*
            DocumentEntity doc = new DocumentEntity();
            doc.setName("Sample Document");
            doc.setCode("DOC001");
            doc.setContent("This is a sample document.");
            documentService.save(doc);

            UserEntity user = new UserEntity();
            user.setEmail("test@example.com");
            user.setPassword("password");
            userService.addUser(user);*/
        };
    }
}