package com.aks.empmgmnt.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DeptRequestDto {
	
	private Integer deptId;

	@NotEmpty(message = "Department name can not be null or empty")
	private String deptName;

}
