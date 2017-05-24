package com.portfolio.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse {

	protected HttpStatus status;
	protected String message;

	public BaseResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

}
