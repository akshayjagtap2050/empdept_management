package com.aks.empmgmnt.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.aks.empmgmnt.dto.DeptRequestDto;
import com.aks.empmgmnt.entity.Department;

public class DeptMapper {

	public static DeptRequestDto mapToDeptDto(Department department) {

		DeptRequestDto deptRequestDto = new DeptRequestDto();
		deptRequestDto.setDeptId(department.getDeptId());
		deptRequestDto.setDeptName(department.getDeptName());

		return deptRequestDto;
	}

	public static List<DeptRequestDto> toDeptDtoList(List<Department> depList) {

		return depList.stream().map(DeptMapper::mapToDeptDto).collect(Collectors.toList());
	}

	public static Department mapToDept(DeptRequestDto deptRequestDto) {

		Department department = new Department();
		department.setDeptId(deptRequestDto.getDeptId());
		department.setDeptName(deptRequestDto.getDeptName());

		return department;
	}

}
