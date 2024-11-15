package com.aks.empmgmnt.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.dto.EmployeeDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.entity.Employee;
import com.aks.empmgmnt.exception.DepartmentNotFoundExcpetion;
import com.aks.empmgmnt.exception.EmployeeNotFoundExcpetion;
import com.aks.empmgmnt.repository.DepartmentRepository;
import com.aks.empmgmnt.repository.EmployeeRepository;
import com.aks.empmgmnt.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public List<EmployeeDto> getAllEmployeeList() {
		// TODO Auto-generated method stub
		logger.info("Fetching all employees");

		List<Employee> employeeList = employeeRepository.findAll();

		return employeeList.stream()
				.map(employee -> new EmployeeDto(employee.getEmpId(), employee.getEmpName(),
						employee.getEmpDesignation(), employee.getEmpAge(),
						(employee.getDepartment() != null ? employee.getDepartment().getDeptName() : null)))
				.collect(Collectors.toList());
	}

	@Override
	public EmpRequestDto saveEmployee(EmpRequestDto empRequestDto) {
		// TODO Auto-generated method stub
		logger.info("checking if entered email already exists or not: {}", empRequestDto.getEmpName());

		Optional<Employee> existingUser = employeeRepository.findByempEmail(empRequestDto.getEmpEmail());

		if (existingUser.isPresent()) {
			logger.error("there is already an account existed with this email: {}", empRequestDto.getEmpEmail());
			throw new EmployeeNotFoundExcpetion(
					"there is already an account existed with this email:{} " + empRequestDto.getEmpEmail());
		}

		logger.info("Saving a new employee with name: {}", empRequestDto.getEmpName());
		Employee emp = new Employee();
		emp.setEmpName(empRequestDto.getEmpName());
		emp.setEmpDesignation(empRequestDto.getEmpDesignation());
		emp.setEmpEmail(empRequestDto.getEmpEmail());
		emp.setEmpAge(empRequestDto.getEmpAge());

		Optional<Department> dept = departmentRepository
				.findBydeptName(empRequestDto.getDepartmentName().toUpperCase());
		if (!dept.isPresent()) {
			logger.error("Department not found with name: {}", empRequestDto.getDepartmentName());
			throw new DepartmentNotFoundExcpetion("Department not found: " + empRequestDto.getDepartmentName());
		}

		logger.info("department availble: {}", empRequestDto.getEmpName());

		emp.setDepartment(dept.get());

		emp = employeeRepository.save(emp);

		logger.info("Successfully saved employee with ID: {}", emp.getEmpId());

		return null;
	}

	@Override
	public void deleteEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		this.employeeRepository.deleteById(id);
	}

	@Override
	public EmpRequestDto findEmpById(Integer id) {
		// TODO Auto-generated method stub

		Employee emp = employeeRepository.findById(id).get();

		return new EmpRequestDto(emp.getEmpId(), emp.getEmpName(), emp.getEmpDesignation(), emp.getEmpEmail(),
				emp.getEmpAge(), emp.getDepartment().getDeptName());

	}

	@Override
	public void updateEmployee(EmpRequestDto empRequestDto) {
		// TODO Auto-generated method stub
		logger.info("Updating employee with email: {}", empRequestDto.getEmpEmail());

		Optional<Employee> existingUser = employeeRepository.findById(empRequestDto.getEmpId());

		if (!existingUser.isPresent()) {
			logger.error("Employee not found with id: {}", empRequestDto.getEmpId());
			throw new EmployeeNotFoundExcpetion("Employee not found: " + empRequestDto.getEmpId());
		}

		existingUser.get().setEmpName(empRequestDto.getEmpName());
		existingUser.get().setEmpDesignation(empRequestDto.getEmpDesignation());
		existingUser.get().setEmpAge(empRequestDto.getEmpAge());
		existingUser.get().setEmpEmail(empRequestDto.getEmpEmail());

		Optional<Department> dept = departmentRepository
				.findBydeptName(empRequestDto.getDepartmentName().toUpperCase());

		if (!dept.isPresent()) {
			logger.error("Department not found with name: {}", empRequestDto.getDepartmentName());
			throw new DepartmentNotFoundExcpetion("Department not found: " + empRequestDto.getDepartmentName());
		}

		existingUser.get().setDepartment(dept.get());

		employeeRepository.save(existingUser.get());

		logger.info("Successfully updated employee with ID: {}" + empRequestDto.getEmpId());

		// return null;
	}

}
