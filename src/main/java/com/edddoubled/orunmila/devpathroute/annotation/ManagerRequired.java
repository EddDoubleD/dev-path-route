package com.edddoubled.orunmila.devpathroute.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isFullyAuthenticated() && hasAnyRole(@CommunityRole.MANAGER, @CommunityRole.ADMIN)")
public @interface ManagerRequired {
}
