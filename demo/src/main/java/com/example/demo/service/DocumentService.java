package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repo.DocumentRepository;
import com.example.demo.Repo.UserRepository;
import com.example.demo.model.DocumentEntity;
import com.example.demo.model.DocumentUser;
import com.example.demo.model.UserEntity;
import com.example.demo.model.UsersDocument;

@Service
@Transactional
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public DocumentEntity save(DocumentEntity document) {
        return documentRepository.save(document);
    }

    public DocumentEntity addDocument(String name, String userEmail) {
        // Find the user by email
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        UserEntity userEntity = optionalUser.get();

        // Create and save the new document
        DocumentEntity document = new DocumentEntity();
        List<DocumentUser> users = new ArrayList<>();
        DocumentUser user = new DocumentUser(userEntity.getId(), "user");
        users.add(user);
        document.setName(name);
        document.setUsers(users);
        document.setCode(UUID.randomUUID().toString());
        document.setContent("");
        document = documentRepository.save(document);

        // Associate the document with the user
        List<UsersDocument> documents = userEntity.getDocuments();
        if (documents == null) {
            documents = new ArrayList<>();
        }
        UsersDocument usersDocument = new UsersDocument();
        usersDocument.setDocumentId(document.getId());
        usersDocument.setRole("user");
        documents.add(usersDocument);
        userEntity.setDocuments(documents);
        userRepository.save(userEntity);

        return document;
    }

    public DocumentEntity addUserToDocument(String code, String userEmail) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findByCode(code);
        if (optionalDocument.isEmpty()) {
            throw new RuntimeException("Document not found with code: " + code);
        }

        // Check if the user exists by email
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + userEmail);
        }

        UserEntity userEntity = optionalUser.get();
        DocumentEntity document = optionalDocument.get();

        boolean userExists = document.getUsers().stream()
            .anyMatch(user -> user.getUserId().equals(userEntity.getId()));
        if (!userExists) {
            document.getUsers().add(new DocumentUser(userEntity.getId(), "user"));
            documentRepository.save(document);

            // Associate the document with the user
            List<UsersDocument> documents = userEntity.getDocuments();
            if (documents == null) {
                documents = new ArrayList<>();
            }
            UsersDocument usersDocument = new UsersDocument();
            usersDocument.setDocumentId(document.getId());
            usersDocument.setRole("user");
            documents.add(usersDocument);
            userEntity.setDocuments(documents);
            userRepository.save(userEntity);
        }
        return document;
    }

    public List<DocumentUser> findDocumentUsers(String documentId) {
        Optional<DocumentEntity> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isEmpty()) {
            return null;
        }

        DocumentEntity document = optionalDocument.get();
        return document.getUsers();
    }

    public List<DocumentEntity> findAll() {
        return documentRepository.findAll();
    }
}
