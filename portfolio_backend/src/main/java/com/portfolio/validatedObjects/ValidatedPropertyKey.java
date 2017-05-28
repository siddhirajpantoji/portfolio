package com.portfolio.validatedObjects;

import com.portfolio.entities.PropertyKey;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
public class ValidatedPropertyKey extends BaseValidEntity {

	PropertyKey key;
	public ValidatedPropertyKey(Boolean isErrorPresent, String message) {
		super(isErrorPresent, message);
	}

	public ValidatedPropertyKey(PropertyKey key) {
		super(false, "");
		this.key = key;
	}
}
