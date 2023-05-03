package com.edddoubled.orunmila.devpathroute.repository;

import com.edddoubled.orunmila.devpathroute.model.token.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

	List<Token> findTokensByUserId(String userId);

	Optional<Token> findByToken(String token);
}
