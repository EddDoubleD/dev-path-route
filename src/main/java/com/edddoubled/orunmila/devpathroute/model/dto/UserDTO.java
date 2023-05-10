package com.edddoubled.orunmila.devpathroute.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
	String username;
	String department;
}
