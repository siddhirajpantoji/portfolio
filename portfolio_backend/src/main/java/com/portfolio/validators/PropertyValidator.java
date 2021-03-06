package com.portfolio.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portfolio.entities.Property;
import com.portfolio.entities.PropertyKey;
import com.portfolio.repositories.PropertyKeyRepository;
import com.portfolio.repositories.PropertyRepository;
import com.portfolio.requests.PropertyRequest;
import com.portfolio.util.PropertyType;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedProperty;

@Component
public class PropertyValidator {

	@Autowired
	PropertyRepository teamRepository;
	
	@Autowired
	PropertyKeyRepository propertyKeyRepository;
	/**
	 * To Parse Request to Object 
	 * @param teamRequest
	 * @param playerId
	 * @return
	 */
	public ValidatedProperty getTeamFromRequest( PropertyRequest teamRequest)
	{
		PropertyKey  key = propertyKeyRepository.findByName(teamRequest.getName());
		if( null == key)
			return new ValidatedProperty(true,ValidationMessages.PROPERTY_KEY_NOT_EXIST);
		PropertyType type = PropertyType.getKeyByValue(teamRequest.getPropertyType());
		if( null == type )
			return new ValidatedProperty(true,ValidationMessages.PROPERTY_TYPE_NOT_EXIST);
		
		return new ValidatedProperty(new Property(teamRequest.getName(), teamRequest.getValue(), type, key));
	}
	/**
	 * To Validate the Player Information 
	 * @param teamId
	 * @return
	 */
	public ValidatedProperty getTeamFromId ( Long teamId)
	{
		if( null == teamId )
			return new ValidatedProperty(true, ValidationMessages.PROPERTY_ID_NOT_EMPTY);
		
		Property team = teamRepository.findOne(teamId);
		if( null == team )
		{
			return new ValidatedProperty(true, ValidationMessages.PROPERTY_DOES_NOT_EXIST);
		}
		return new ValidatedProperty(team);
	}
}
