package com.edddoubled.orunmila.devpathroute.service;

import com.edddoubled.orunmila.devpathroute.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static com.edddoubled.orunmila.devpathroute.utils.Const.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutService implements LogoutHandler {
	TokenRepository tokenRepository;


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader(AUTHORIZATION);
		final String jwt;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt).orElse(null);

		if (storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenRepository.save(storedToken);
			//  Explicitly clears the context value from the current thread
			SecurityContextHolder.clearContext();
		}
	}
}
