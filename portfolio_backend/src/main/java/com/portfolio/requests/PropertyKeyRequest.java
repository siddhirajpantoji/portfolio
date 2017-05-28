package com.portfolio.requests;

import org.hibernate.validator.constraints.NotBlank;

import com.portfolio.util.ValidationMessages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
@NoArgsConstructor
public class PropertyKeyRequest extends BaseRequest {

	@NotBlank (message = ValidationMessages.PROPERTY_KEY_NOT_EMPTY)
	private String name;
}
