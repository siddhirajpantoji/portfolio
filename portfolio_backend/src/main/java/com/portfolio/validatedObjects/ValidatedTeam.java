package com.portfolio.validatedObjects;

import com.portfolio.entities.Property;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
public class ValidatedTeam   extends BaseValidEntity {

	private Property property;
	public ValidatedTeam(Boolean isErrorPresent, String message) {
		super(isErrorPresent, message);
	}

	public ValidatedTeam(Property team) {
		super(false, "");
		this.property = team;
	}

}
