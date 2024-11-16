package com.aks.empmgmnt.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.aks.empmgmnt.dto.DeptRequestDto;
import com.aks.empmgmnt.dto.EmpRequestDto;
import com.aks.empmgmnt.entity.Department;
import com.aks.empmgmnt.entity.Employee;

public class EmpMapper {

	public static EmpRequestDto mapToEmpDto(Employee employee) {

		EmpRequestDto empRequestDto = new EmpRequestDto();
		empRequestDto.setEmpId(employee.getEmpId());
		empRequestDto.setEmpEmail(employee.getEmpEmail());
		empRequestDto.setEmpName(employee.getEmpName());
		empRequestDto.setEmpAge(employee.getEmpAge());
		empRequestDto.setEmpDesignation(employee.getEmpDesignation());
		empRequestDto.setDepartmentName(employee.getDepartment().getDeptName());

		return empRequestDto;
	}

	public static List<EmpRequestDto> empToDtoList(List<Employee> employees) {

		return employees.stream().map(EmpMapper::mapToEmpDto).collect(Collectors.toList());
	}

	public static Employee mapToEmp(EmpRequestDto empRequestDto, Department department) {

		Employee employee = new Employee();
		employee.setEmpId(empRequestDto.getEmpId());
		employee.setEmpName(empRequestDto.getEmpName());
		employee.setEmpEmail(empRequestDto.getEmpEmail());
		employee.setEmpAge(empRequestDto.getEmpAge());
		employee.setEmpDesignation(empRequestDto.getEmpDesignation());
		employee.setDepartment(department);

		return employee;
	}

}
