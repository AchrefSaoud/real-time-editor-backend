package com.example.demo.Repo;

import org.springframework.stereotype.Repository;

import com.example.demo.model.UserEntity;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}
