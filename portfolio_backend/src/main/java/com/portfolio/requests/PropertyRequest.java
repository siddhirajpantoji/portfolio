package com.portfolio.requests;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.portfolio.util.ValidationMessages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
@NoArgsConstructor
public class PropertyRequest extends BaseRequest {

	@NotBlank (message = ValidationMessages.TEAM_NAME_NOT_EMPTY)
	private String name;
	
	private String value;

	@NotBlank( message = ValidationMessages.PROPERTY_TYPE_NOT_EMPTY)
	private Integer propertyType;
	// Will Change to property key Object
	@NotBlank( message = ValidationMessages.PROPERTY_KEY_NOT_EMPTY)
	private String key;

	
}
