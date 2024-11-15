package com.aks.empmgmnt.dto;

public class EmpRequestDto {

	private Integer empId;

	private String empName;
	private String empDesignation;

	private String empEmail;

	private Integer empAge;
	private String departmentName;

	public EmpRequestDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmpRequestDto(Integer empId, String empName, String empDesignation, String empEmail, Integer empAge,
			String departmentName) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empDesignation = empDesignation;
		this.empEmail = empEmail;
		this.empAge = empAge;
		this.departmentName = departmentName;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public Integer getEmpAge() {
		return empAge;
	}

	public void setEmpAge(Integer empAge) {
		this.empAge = empAge;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}
