package com.edddoubled.orunmila.devpathroute.model.token;

import com.edddoubled.orunmila.devpathroute.model.user.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Token extends BaseEntity {

	String token;

	TokenType tokenType = TokenType.BEARER;

	boolean revoked;

	boolean expired;

	String userId;
}
