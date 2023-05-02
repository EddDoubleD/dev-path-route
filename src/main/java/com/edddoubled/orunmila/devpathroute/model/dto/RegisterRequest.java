package com.edddoubled.orunmila.devpathroute.model.dto;

import com.edddoubled.orunmila.devpathroute.model.user.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest implements Serializable {
    String username;
    String email;
    String password;
    Role role;
}
