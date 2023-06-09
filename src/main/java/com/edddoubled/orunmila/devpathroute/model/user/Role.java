package com.edddoubled.orunmila.devpathroute.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.edddoubled.orunmila.devpathroute.model.user.Permission.*;

/**
 * User Roles
 */
@RequiredArgsConstructor
public enum Role {
	USER(
			Set.of(
					USER_READ
			)
	),

	ADMIN(
			Set.of(
					ADMIN_READ,
					ADMIN_UPDATE,
					ADMIN_DELETE,
					ADMIN_CREATE,
					MANAGER_READ,
					MANAGER_UPDATE,
					MANAGER_DELETE,
					MANAGER_CREATE
			)
	),
	MANAGER(
			Set.of(
					MANAGER_READ,
					MANAGER_UPDATE,
					MANAGER_DELETE,
					MANAGER_CREATE
			)
	);

	@Getter
	private final Set<Permission> permissions;

	public @NotNull List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}

	@Component("CommunityRole")
	@Getter
	static class CommunityRole {
		private final Role USER = Role.USER;
		private final Role ADMIN = Role.ADMIN;
		private final Role MANAGER = Role.MANAGER;
	}
}
