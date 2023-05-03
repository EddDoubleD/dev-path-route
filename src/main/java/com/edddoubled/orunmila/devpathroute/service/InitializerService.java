package com.edddoubled.orunmila.devpathroute.service;

import com.edddoubled.orunmila.devpathroute.model.user.User;
import com.edddoubled.orunmila.devpathroute.repository.UserRepository;
import com.edddoubled.orunmila.devpathroute.utils.JsonUtils;
import com.edddoubled.orunmila.devpathroute.utils.ResourceLoader;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Slf4j
public class InitializerService implements CommandLineRunner {

	boolean disable;

	UserRepository userRepository;

	PasswordEncoder passwordEncoder;

	public InitializerService(UserRepository userRepository,
							  @Value("${application.data.disable}") boolean disable,
							  PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.disable = disable;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void run(String... args) throws Exception {
		if (disable) {
			log.info("Data migration is disabled");
			return;
		}

		log.info("Start data migration...");
		String json = ResourceLoader.loadTextFile("src/main/resources/data", "users.json");
		Arrays.stream(JsonUtils.deserialize(json, User[].class))
				.forEach(user -> {
					List<User> findUsers = userRepository.findAllByUsername(user.getUsername());
					if (findUsers.isEmpty() || findUsers.size() == 1) {
						// saveOrUpdate, password must be encode
						user.setPassword(passwordEncoder.encode(user.getPassword()));
						userRepository.save(findUsers.isEmpty() ? user : findUsers.get(0));
						log.info("User {} saved", user.getUsername());
					} else {
						log.warn("User {} duplicated: {}", user.getUsername(), findUsers.stream().map(User::getId).toList());
					}
				});
	}
}
