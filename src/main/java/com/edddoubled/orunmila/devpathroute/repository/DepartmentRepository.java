package com.edddoubled.orunmila.devpathroute.repository;

import com.edddoubled.orunmila.devpathroute.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DepartmentRepository extends MongoRepository<Department, String> {
	Optional<Department> findByName(String departmentName);
}
