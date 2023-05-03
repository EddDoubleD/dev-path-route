package com.edddoubled.orunmila.devpathroute.service;

import com.edddoubled.orunmila.devpathroute.model.dto.AuthenticationRequest;
import com.edddoubled.orunmila.devpathroute.model.dto.AuthenticationResponse;
import com.edddoubled.orunmila.devpathroute.model.dto.RegisterRequest;
import com.edddoubled.orunmila.devpathroute.model.token.Token;
import com.edddoubled.orunmila.devpathroute.model.token.TokenType;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import com.edddoubled.orunmila.devpathroute.repository.TokenRepository;
import com.edddoubled.orunmila.devpathroute.repository.UserRepository;
import com.edddoubled.orunmila.devpathroute.service.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	TokenRepository tokenRepository;
	PasswordEncoder passwordEncoder;
	JwtService jwtService;
	AuthenticationManager authenticationManager;

	public AuthenticationResponse register(@NotNull RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setRole(request.getRole());

		User savedUser = userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		saveUserToken(savedUser, jwtToken);

		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}

	public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword()
				)
		);

		var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);

		revokeAllUserTokens(user);

		saveUserToken(user, jwtToken);

		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}

	public void refreshToken(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String username;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		refreshToken = authHeader.substring(7);
		username = jwtService.extractUsername(refreshToken);
		if (username != null) {
			var user = this.userRepository.findByUsername(username).orElseThrow();

			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	private void saveUserToken(@NotNull User user, String jwtToken) {
		Token token = new Token();
		token.setToken(jwtToken);
		token.setUserId(user.getId());
		token.setExpired(false);
		token.setRevoked(false);
		token.setTokenType(TokenType.BEARER);
		// save the prepared token
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(@NotNull User user) {
		var validUserTokens = tokenRepository.findTokensByUserId(user.getId());
		if (validUserTokens.isEmpty()) {
			return;
		}

		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});

		tokenRepository.saveAll(validUserTokens);
	}

	public Optional<User> checkJWT(@NotNull HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return Optional.empty();
		}
		String token = authHeader.substring(7);
		String userName = jwtService.extractUsername(token);
		User user = userRepository.findByUsername(userName).orElse(null);
		return Optional.ofNullable(user);
	}
}
