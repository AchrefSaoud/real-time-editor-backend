package com.example.demo.Repo;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DocumentEntity;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity, String> {

    Optional<DocumentEntity> findByCode(String code);
}
