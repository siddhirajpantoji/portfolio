package com.portfolio.util;

public enum PropertyType {

	STRING(0),IMAGE(1);
	
	public final Integer value;

	PropertyType(final Integer value) {
		this.value = value;
	}

	public static PropertyType getKeyByValue(Integer value) {

		for(PropertyType e: PropertyType.values()) {
			if(e.value == value) {
				return e;
			}
		}
		return null;
	}
}
