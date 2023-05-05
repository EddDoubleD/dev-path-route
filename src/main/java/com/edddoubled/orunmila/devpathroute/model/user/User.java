package com.edddoubled.orunmila.devpathroute.model.user;

import com.edddoubled.orunmila.devpathroute.model.BaseEntity;
import com.edddoubled.orunmila.devpathroute.model.Department;
import lombok.*;
import lombok.EqualsAndHashCode.Include;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Document
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {

	@Include
	String username;

	String password;

	@Include
	String email;

	Role role;

	@DBRef
	Department department;

	boolean accountNonExpired = true;

	boolean accountNonLocked = true;

	boolean credentialsNonExpired = true;

	boolean enabled = true;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				", department=" + (null == department ? null : department.getName()) +
				", accountNonExpired=" + accountNonExpired +
				", accountNonLocked=" + accountNonLocked +
				", credentialsNonExpired=" + credentialsNonExpired +
				", enabled=" + enabled +
				"} " + super.toString();
	}
}
