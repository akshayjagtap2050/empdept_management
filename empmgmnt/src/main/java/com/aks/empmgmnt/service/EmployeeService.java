package com.aks.empmgmnt.service;

import java.util.List;

import com.aks.empmgmnt.dto.EmpRequestDto;

public interface EmployeeService {

	public List<EmpRequestDto> getAllEmployeeList();

	public EmpRequestDto saveEmployee(EmpRequestDto empRequestDto);

	public boolean deleteEmployeeById(Integer id);

	public EmpRequestDto findEmpById(Integer id);

	public void updateEmployee(EmpRequestDto em);

}
