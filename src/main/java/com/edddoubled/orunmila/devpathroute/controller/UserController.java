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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	/*@PostMapping("/change-role")
	@AdminRequired()
	public ResponseEntity<UserDTO> changeRole(@RequestBody @NotNull UserRequest userRequest) throws NotFoundException {

		return ResponseEntity.ok(...);
	}*/

	@PostMapping("/add-to-department/")
	@ManagerRequired()
	public ResponseEntity<UserDTO> addUserToDepartment(@RequestBody @NotNull UserDepartmentRequest userDepartmentRequest) throws NotFoundException {
		User user = userService.getUserByName(userDepartmentRequest.getUsername());
		user = userService.addUserToDepartment(user, userDepartmentRequest.getDepartmentName());
		return ResponseEntity.ok(mapper.userToDto(user));
	}

	@GetMapping("/get/{userName}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String userName) throws NotFoundException {
		UserDTO user = userService.getUserDtoByName(userName);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/get/by-department/{departmentName}")
	public ResponseEntity<List<UserDTO>> getUserByDepartment(@PathVariable String departmentName) throws NotFoundException {
		return ResponseEntity.ok(userService.getUsersByDepartment(departmentName));
	}
}
