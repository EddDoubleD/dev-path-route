package com.edddoubled.orunmila.devpathroute.controller;

import com.edddoubled.orunmila.devpathroute.model.dto.AuthenticationRequest;
import com.edddoubled.orunmila.devpathroute.model.dto.AuthenticationResponse;
import com.edddoubled.orunmila.devpathroute.model.dto.RegisterRequest;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import com.edddoubled.orunmila.devpathroute.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

	AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.refreshToken(request, response);
	}

	@GetMapping("/check-jwt")
	public ResponseEntity<User> checkJWT(HttpServletRequest request, HttpServletResponse response) {
		return service.checkJWT(request).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
