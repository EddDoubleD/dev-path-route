package com.edddoubled.orunmila.devpathroute.repository;

import com.edddoubled.orunmila.devpathroute.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String userName);

	List<User> findAllByUsername(String userName);

	Optional<User> deleteByUsername(String userName);

	List<User> deleteAllByUsername(String userName);
}
