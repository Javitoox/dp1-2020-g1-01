package org.springframework.samples.petclinic.service.exceptions;

public class BadRequestException extends RuntimeException {
	
	public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
	
}