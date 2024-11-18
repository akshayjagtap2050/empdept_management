package com.aks.empmgmnt.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpRequestDto {

	private Long empId;

	@NotEmpty(message = "Employee name can not be null or empty")
	private String empName;

	private String empDesignation;

	@NotEmpty(message = "Employee email can not be null or empty")
	private String empEmail;

	private Integer empAge;

	@NotEmpty(message = "Employee department name can not be null or empty")
	private String departmentName;

}
