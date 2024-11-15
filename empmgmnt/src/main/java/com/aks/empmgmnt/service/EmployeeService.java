package com.aks.empmgmnt.service;

import java.util.List;

import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.dto.EmployeeDto;
import com.aks.empmgmnt.entity.Employee;

public interface EmployeeService {

	public List<EmployeeDto> getAllEmployeeList();

	public EmpRequestDto saveEmployee(EmpRequestDto empRequestDto);

	public void deleteEmployeeById(Integer id);

	public EmpRequestDto findEmpById(Integer id);

	public void updateEmployee(EmpRequestDto em);

}
