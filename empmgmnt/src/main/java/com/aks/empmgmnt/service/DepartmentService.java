package com.aks.empmgmnt.service;

import java.util.List;

import com.aks.empmgmnt.dto.DeptRequestDto;

public interface DepartmentService {

	public List<DeptRequestDto> getAllDepartmentList();

	public void saveDepartment(DeptRequestDto deptRequestDto);

	public DeptRequestDto findDeptById(Integer id);

	public void updateDepartment(DeptRequestDto dpDeptRequestDto);
	
}
