package com.aks.empmgmnt.service;

import java.util.List;

import com.aks.empmgmnt.entity.Department;

public interface DepartmentService {

	public List<Department> getAllDepartmentList();

	public void saveDepartment(Department department);

	public Department findDeptById(Integer id);

	public void updateDepartment(Department department);
	
}
