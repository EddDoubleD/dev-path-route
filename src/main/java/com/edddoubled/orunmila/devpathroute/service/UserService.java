package com.edddoubled.orunmila.devpathroute.service;

import com.edddoubled.orunmila.devpathroute.exception.NotFoundException;
import com.edddoubled.orunmila.devpathroute.model.Department;
import com.edddoubled.orunmila.devpathroute.model.user.User;
import com.edddoubled.orunmila.devpathroute.repository.DepartmentRepository;
import com.edddoubled.orunmila.devpathroute.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	UserRepository userRepository;
	DepartmentRepository departmentRepository;

	public User getUserByName(String userName) throws NotFoundException {
		return userRepository.findByUsername(userName)
				.orElseThrow(() -> new NotFoundException(String.format("Сотрудник [%s] не найден", userName)));
	}

	public List<User> getUsersByDepartment(String departmentName) {
		// TODO
		List<User> users = userRepository.findAllByDepartment_Name(departmentName);
		return users;
	}

	public List<User> getUsersByManager() {
		// TODO
		return null;
	}

	public User addUserToDepartment(@NotNull User user, @NotNull Department department) {
		user.setDepartment(department);
		user = userRepository.save(user);
		return user;
	}

	public User addUserToDepartment(User user, String departmentName) throws NotFoundException {
		Department department = departmentRepository.findByName(departmentName)
				.orElseThrow(() -> new NotFoundException(String.format("Отдел [%s] не найден", departmentName)));
		return addUserToDepartment(user, department);
	}

	public User deleteUserByName(String userName) throws NotFoundException {
		return userRepository.deleteByUsername(userName)
				.orElseThrow(() -> new NotFoundException(String.format("Сотрудник [%s] не найден", userName)));
	}
}
