package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repo.DocumentRepository;
import com.example.demo.Repo.UserRepository;
import com.example.demo.model.DocumentEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.UsersDocument;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DocumentRepository documentRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,DocumentRepository documentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.documentRepository=documentRepository;
    }

    public UserEntity addUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public UserEntity loadByUsername(String username) {
        return this.userRepository.findByEmail(username).get();
    }

    public List<DocumentEntity> findDocumentsByUserId(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        UserEntity user = optionalUser.get();
        List<UsersDocument> userDocuments = user.getDocuments();
        List<DocumentEntity> documents = new ArrayList<>();

        for (UsersDocument usersDocument : userDocuments) {
            Optional<DocumentEntity> documentOptional = documentRepository.findById(usersDocument.getDocumentId());
            documentOptional.ifPresent(documents::add);
        }

        return documents;
    }

    
    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }
}
