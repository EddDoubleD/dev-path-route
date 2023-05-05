package com.edddoubled.orunmila.devpathroute.controller;

import com.edddoubled.orunmila.devpathroute.annotation.AdminRequired;
import com.edddoubled.orunmila.devpathroute.annotation.ManagerRequired;
import com.edddoubled.orunmila.devpathroute.exception.NotFoundException;
import com.edddoubled.orunmila.devpathroute.model.dto.UserDTO;
import com.edddoubled.orunmila.devpathroute.model.dto.UserDepartmentRequest;
import com.edddoubled.orunmila.devpathroute.model.dto.UserRequest;
import com.edddoubled.orunmila.devpathroute.model.mapper.Mapper;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import com.edddoubled.orunmila.devpathroute.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

	UserService userService;
	Mapper mapper;

	@PostMapping("/remove")
	@AdminRequired()
	public ResponseEntity<UserDTO> removeUser(@RequestBody @NotNull UserRequest userRequest) throws NotFoundException {
		User deletedUser = userService.deleteUserByName(userRequest.getUsername());
		return ResponseEntity.ok(mapper.userToDto(deletedUser));
	}

	@PostMapping("/add-to-department/")
	@ManagerRequired()
	public ResponseEntity<UserDTO> addUserToDepartment(@RequestBody @NotNull UserDepartmentRequest userDepartmentRequest) throws NotFoundException {
		User user = userService.getUserByName(userDepartmentRequest.getUsername());
		user = userService.addUserToDepartment(user, userDepartmentRequest.getDepartmentName());
		return ResponseEntity.ok(mapper.userToDto(user));
	}

	@GetMapping("/get/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		try {
			User user = userService.getUserByName(username);
			return ResponseEntity.ok(user);
		} catch (NotFoundException e) {
			log.warn("User{} not found", username);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
