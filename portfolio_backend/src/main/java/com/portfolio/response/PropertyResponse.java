package com.portfolio.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.portfolio.entities.Property;
import com.portfolio.util.Messages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
@JsonInclude(value = Include.NON_NULL)
public class PropertyResponse extends BaseResponse{

	private Property property;
	
	private List<Property> properties;
	public PropertyResponse(HttpStatus status, String message) {
		super(status, message);
	}
	
	
	public PropertyResponse( Property team) {
		super(HttpStatus.OK, Messages.EVERYTHING_OK);
		this.property = team;
	}
	public PropertyResponse( List<Property> properties) {
		super(HttpStatus.OK, Messages.EVERYTHING_OK);
		this.properties = properties;
	} 
}
