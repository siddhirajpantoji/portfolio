package com.portfolio.response;

import org.springframework.http.HttpStatus;

import com.portfolio.entities.Property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
public class TeamResponse extends BaseResponse{

	private Property property;
	public TeamResponse(HttpStatus status, String message) {
		super(status, message);
	}
	
	
	public TeamResponse(HttpStatus status, String message, Property team) {
		super(status, message);
		this.property = team;
	}
}
