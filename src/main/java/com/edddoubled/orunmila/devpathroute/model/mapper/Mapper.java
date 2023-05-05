package com.edddoubled.orunmila.devpathroute.model.mapper;

import com.edddoubled.orunmila.devpathroute.model.Department;
import com.edddoubled.orunmila.devpathroute.model.dto.DepartmentDTO;
import com.edddoubled.orunmila.devpathroute.model.dto.UserDTO;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
// TODO
public class Mapper {
	public UserDTO userToDto(@NotNull User user) {
		Department department = user.getDepartment();
		return new UserDTO(user.getUsername(), null == department ? null : department.getName());
	}

	public DepartmentDTO departmentToDto(@NotNull Department department) {
		return new DepartmentDTO(department.getName(),
				Optional.ofNullable(department.getUsers()).orElse(Collections.emptySet())
						.stream()
						.map(this::userToDto).toList());
	}
}
