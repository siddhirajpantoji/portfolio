package com.portfolio.validatedObjects;

import lombok.Data;

@Data
public class BaseValidEntity {
	
	protected Boolean isErrorPresent;
	
	protected String message;
	public BaseValidEntity(Boolean isErrorPresent, String message) {
		this.isErrorPresent = isErrorPresent;
		this.message = message;
	}
}
