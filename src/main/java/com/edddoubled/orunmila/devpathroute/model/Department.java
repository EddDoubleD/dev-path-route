package com.edddoubled.orunmila.devpathroute.model;

import com.edddoubled.orunmila.devpathroute.model.user.BaseEntity;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends BaseEntity {

	String name;

	@DocumentReference(lazy = true)
	List<User> users;
}
