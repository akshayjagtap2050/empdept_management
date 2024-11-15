package com.aks.empmgmnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeNotFoundExcpetion extends RuntimeException {

	public EmployeeNotFoundExcpetion(String message) {
		super(message);
	}
}
