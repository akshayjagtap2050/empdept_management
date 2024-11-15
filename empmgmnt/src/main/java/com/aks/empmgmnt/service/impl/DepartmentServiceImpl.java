package com.aks.empmgmnt.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.entity.Employee;
import com.aks.empmgmnt.exception.DeaprtmentExistException;
import com.aks.empmgmnt.exception.DepartmentNotFoundExcpetion;
import com.aks.empmgmnt.exception.EmployeeNotFoundExcpetion;
import com.aks.empmgmnt.repository.DepartmentRepository;
import com.aks.empmgmnt.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public List<Department> getAllDepartmentList() {
		logger.info("Fetching all departments");

		List<Department> departments = departmentRepository.findAll();

		logger.info("departments size:" + departments.size());

		return departments;
	}

	@Override
	public void saveDepartment(Department department) {
		// TODO Auto-generated method stub
		Optional<Department> dep = departmentRepository.findBydeptName(department.getDeptName());
		if (dep.isPresent()) {
			logger.error("there is already an department existed with this name: {}", department.getDeptName());
			throw new DeaprtmentExistException(
					"there is already an department existed with this name:{} " + department.getDeptName());
		}

		Department dept = new Department();
		dept.setDeptName(department.getDeptName().toUpperCase());
		Department savedDept = departmentRepository.save(dept);
		logger.info("Saved new department with ID: {}", savedDept.getDeptId());
	}

	@Override
	public Department findDeptById(Integer id) {
		// TODO Auto-generated method stub
		Department department = departmentRepository.findById(id).get();

		return department;
	}

	@Override
	public void updateDepartment(Department department) {
		// TODO Auto-generated method stub
		logger.info("Updating dept with id: {}", department.getDeptId());

		Optional<Department> existingDept = departmentRepository.findById(department.getDeptId());

		if (!existingDept.isPresent()) {
			logger.error("Dept not found with id: {}", department.getDeptId());
			throw new DepartmentNotFoundExcpetion("Dept not found: " + department.getDeptId());
		}

		existingDept.get().setDeptName(department.getDeptName());

		departmentRepository.save(existingDept.get());

		logger.info("Successfully updated dept with ID: {}" + department.getDeptId());

		// return null;
	}

}
