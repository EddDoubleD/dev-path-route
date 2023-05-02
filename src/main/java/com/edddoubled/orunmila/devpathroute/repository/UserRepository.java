package com.edddoubled.orunmila.devpathroute.repository;

import com.edddoubled.orunmila.devpathroute.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
