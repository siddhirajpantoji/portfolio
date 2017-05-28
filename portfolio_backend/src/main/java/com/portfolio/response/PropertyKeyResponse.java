package com.portfolio.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.portfolio.entities.PropertyKey;
import com.portfolio.util.Messages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
@JsonInclude(value = Include.NON_NULL)
public class PropertyKeyResponse  extends BaseResponse{

	private PropertyKey key;
	
	private List<PropertyKey> keys;
	
	

	public PropertyKeyResponse(PropertyKey key) {
		super(HttpStatus.OK, Messages.EVERYTHING_OK);
		this.key = key;
	}

	public PropertyKeyResponse(List<PropertyKey> keys) {
		super(HttpStatus.OK, Messages.EVERYTHING_OK);
		this.keys = keys;
	}

	public PropertyKeyResponse(HttpStatus status, String message) {
		super(status, message);
	}
}
