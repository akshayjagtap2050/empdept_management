package com.aks.empmgmnt.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.empmgmnt.dto.DeptRequestDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.entity.Employee;
import com.aks.empmgmnt.exception.DeaprtmentExistException;
import com.aks.empmgmnt.exception.DepartmentNotFoundExcpetion;
import com.aks.empmgmnt.exception.EmployeeNotFoundExcpetion;
import com.aks.empmgmnt.mapper.DeptMapper;
import com.aks.empmgmnt.repository.DepartmentRepository;
import com.aks.empmgmnt.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public List<DeptRequestDto> getAllDepartmentList() {

		List<Department> departments = departmentRepository.findAll();

		logger.info("departments size:" + departments.size());

		return DeptMapper.toDeptDtoList(departments);
	}

	@Override
	public void saveDepartment(DeptRequestDto DeptRequestDto) {
		// TODO Auto-generated method stub

		Department department = DeptMapper.mapToDept(DeptRequestDto);

		Optional<Department> dep = departmentRepository.findBydeptName(department.getDeptName());
		if (dep.isPresent()) {
			logger.error("there is already an department existed with this name: {}", department.getDeptName());
			throw new DeaprtmentExistException(
					"there is already an department existed with this name:{} " + department.getDeptName());
		}

		department.setDeptName(department.getDeptName().toUpperCase());
		Department savedDept = departmentRepository.save(department);
		logger.info("Saved new department with ID: {}", savedDept.getDeptId());
	}

	@Override
	public DeptRequestDto findDeptById(Long id) {
		// TODO Auto-generated method stub
		Optional<Department> dep = departmentRepository.findById(id);
		if (!dep.isPresent()) {
			logger.error("Dept not found with id: {}", id);
			throw new DepartmentNotFoundExcpetion("Dept not found: " + id);
		}

		return DeptMapper.mapToDeptDto(dep.get());
	}

	@Override
	public void updateDepartment(DeptRequestDto deptRequestDto) {

		Optional<Department> existingDept = departmentRepository.findById(deptRequestDto.getDeptId());

		if (!existingDept.isPresent()) {
			logger.error("Dept not found with id: {}", deptRequestDto.getDeptName());
			throw new DepartmentNotFoundExcpetion("Dept not found: " + deptRequestDto.getDeptName());
		}

		existingDept.get().setDeptName(deptRequestDto.getDeptName().toUpperCase());

		departmentRepository.save(existingDept.get());

		logger.info("Successfully updated dept with ID: {}" + deptRequestDto.getDeptName());
	}

}
