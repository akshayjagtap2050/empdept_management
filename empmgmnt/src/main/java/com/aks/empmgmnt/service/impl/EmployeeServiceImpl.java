package com.aks.empmgmnt.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.entity.Employee;
import com.aks.empmgmnt.exception.DepartmentNotFoundExcpetion;
import com.aks.empmgmnt.exception.EmployeeNotFoundExcpetion;
import com.aks.empmgmnt.mapper.EmpMapper;
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
	public List<EmpRequestDto> getAllEmployeeList() {

		List<Employee> employeeList = employeeRepository.findAll();

		List<EmpRequestDto> empRequestDtos = EmpMapper.empToDtoList(employeeList);

		return empRequestDtos;
	}

	@Override
	public EmpRequestDto saveEmployee(EmpRequestDto empRequestDto) {

		Optional<Employee> existingUser = employeeRepository.findByempEmail(empRequestDto.getEmpEmail());

		if (existingUser.isPresent()) {
			logger.error("there is already an account existed with this email: {}", empRequestDto.getEmpEmail());
			throw new EmployeeNotFoundExcpetion(
					"there is already an account existed with this email:{} " + empRequestDto.getEmpEmail());
		}

		Optional<Department> dept = departmentRepository
				.findBydeptName(empRequestDto.getDepartmentName().toUpperCase());
		if (!dept.isPresent()) {
			logger.error("Department not found with name: {}", empRequestDto.getDepartmentName());
			throw new DepartmentNotFoundExcpetion("Department not found: " + empRequestDto.getDepartmentName());
		}

		Employee emp = EmpMapper.mapToEmp(empRequestDto, dept.get());

		emp = employeeRepository.save(emp);

		logger.info("Successfully saved employee with ID: {}", emp.getEmpEmail());

		return null;
	}

	@Override
	public boolean deleteEmployeeById(Long id) {
		// TODO Auto-generated method stub
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundExcpetion("id not found:" + id));

		employeeRepository.deleteById(id);
		return true;
	}

	@Override
	public EmpRequestDto findEmpById(Long id) {
		// TODO Auto-generated method stub

		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundExcpetion("id not found:" + id));

		return EmpMapper.mapToEmpDto(emp);
	}

	@Override
	public void updateEmployee(EmpRequestDto empRequestDto) {

		Optional<Employee> existingEmp = employeeRepository.findById(empRequestDto.getEmpId());

		if (!existingEmp.isPresent()) {
			logger.error("Employee not found with id: {}", empRequestDto.getEmpEmail());
			throw new EmployeeNotFoundExcpetion("Employee not found: " + empRequestDto.getEmpEmail());
		}

		Optional<Department> dept = departmentRepository
				.findBydeptName(empRequestDto.getDepartmentName().toUpperCase());

		if (!dept.isPresent()) {
			logger.error("Department not found with name: {}", empRequestDto.getDepartmentName());
			throw new DepartmentNotFoundExcpetion("Department not found: " + empRequestDto.getDepartmentName());
		}

		existingEmp = Optional.of(EmpMapper.mapToEmp(empRequestDto, dept.get()));

		employeeRepository.save(existingEmp.get());

		logger.info("Successfully updated employee with ID: {}" + empRequestDto.getEmpId());

		// return null;
	}

}
