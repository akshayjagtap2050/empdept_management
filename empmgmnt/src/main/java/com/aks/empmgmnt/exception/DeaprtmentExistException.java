package com.aks.empmgmnt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DeaprtmentExistException extends RuntimeException {

	public DeaprtmentExistException(String s) {
		// TODO Auto-generated constructor stub
		super(s);
	}

}
