package com.edddoubled.orunmila.devpathroute.model.user;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Getter
public abstract class BaseEntity {
	@MongoId
	String id;
}
