package com.edddoubled.orunmila.devpathroute.controller;

import com.edddoubled.orunmila.devpathroute.annotation.AdminRequired;
import com.edddoubled.orunmila.devpathroute.model.dto.DepartmentDTO;
import com.edddoubled.orunmila.devpathroute.model.dto.DepartmentRequest;
import com.edddoubled.orunmila.devpathroute.model.mapper.Mapper;
import com.edddoubled.orunmila.devpathroute.service.DepartmentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/management/department")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

	DepartmentService departmentService;
	Mapper mapper;

	@PostMapping("/create-department")
	@AdminRequired()
	public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody @NotNull DepartmentRequest departmentRequest) {
		return ResponseEntity.ok(mapper.departmentToDto(departmentService.createDepartment(departmentRequest.getDepartmentName())));
	}
}
