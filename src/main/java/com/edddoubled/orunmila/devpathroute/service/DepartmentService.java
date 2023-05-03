package com.edddoubled.orunmila.devpathroute.service;

import com.edddoubled.orunmila.devpathroute.model.Department;
import com.edddoubled.orunmila.devpathroute.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
	DepartmentRepository departmentRepository;

	public Department createDepartment(String departmentName) {
		Optional<Department> foundDepartment = departmentRepository.findByName(departmentName);
		if (foundDepartment.isPresent()) {
			return foundDepartment.get();
		}
		Department department = new Department();
		department.setName(departmentName);
		return departmentRepository.save(department);
	}
}
