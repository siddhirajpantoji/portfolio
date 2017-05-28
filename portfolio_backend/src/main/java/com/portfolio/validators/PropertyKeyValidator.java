package com.portfolio.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portfolio.entities.PropertyKey;
import com.portfolio.repositories.PropertyKeyRepository;
import com.portfolio.requests.PropertyKeyRequest;
import com.portfolio.requests.PropertyRequest;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedPropertyKey;

@Component
public class PropertyKeyValidator {


	@Autowired
	PropertyKeyRepository propertyKeyRepository;

	public ValidatedPropertyKey validatePropertyKeyRequest( PropertyKeyRequest request)
	{
		PropertyKey key = propertyKeyRepository.findByName(request.getName());
		if ( null != key )
			return new ValidatedPropertyKey(true, ValidationMessages.PROPERTY_KEY_ALREADY_EXIST);

		return new ValidatedPropertyKey(new PropertyKey(request.getName()));
	}


	public ValidatedPropertyKey getPropertyFromId( Long id)
	{
		PropertyKey key = propertyKeyRepository.findOne(id);
		if( null == key )
			return new ValidatedPropertyKey(true, ValidationMessages.PROPERTY_KEY_NOT_EXIST);
		
		return new ValidatedPropertyKey(key);
	}
}
