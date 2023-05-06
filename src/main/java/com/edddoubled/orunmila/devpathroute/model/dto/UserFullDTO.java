package com.edddoubled.orunmila.devpathroute.model.dto;

import com.edddoubled.orunmila.devpathroute.model.user.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFullDTO extends UserDTO {
	String email;
	Role role;
	boolean accountNonExpired;
	boolean accountNonLocked;
	boolean credentialsNonExpired;
	boolean enabled;
}
