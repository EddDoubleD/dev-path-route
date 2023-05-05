package com.edddoubled.orunmila.devpathroute.model;

import com.edddoubled.orunmila.devpathroute.model.user.User;
import lombok.*;
import lombok.EqualsAndHashCode.Include;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends BaseEntity {

	@Include
	String name;

	@DBRef
	Set<User> users;
}
