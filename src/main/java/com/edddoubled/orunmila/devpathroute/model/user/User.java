package com.edddoubled.orunmila.devpathroute.model.user;

import com.edddoubled.orunmila.devpathroute.model.Department;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {

	String username;

	String password;

	String email;

	Role role;

	@DocumentReference(lazy = true)
	Department department;

	boolean accountNonExpired = true;

	boolean accountNonLocked = true;

	boolean credentialsNonExpired = true;

	boolean enabled = true;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}
}
