package com.portfolio.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.portfolio.entities.Property;
import com.portfolio.entities.PropertyKey;
import com.portfolio.repositories.PropertyKeyRepository;
import com.portfolio.requests.PropertyKeyRequest;
import com.portfolio.requests.PropertyRequest;
import com.portfolio.response.PropertyKeyResponse;
import com.portfolio.response.PropertyResponse;
import com.portfolio.util.RestEndpointConstants;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedProperty;
import com.portfolio.validatedObjects.ValidatedPropertyKey;
import com.portfolio.validators.PropertyKeyValidator;

public class PropertyKeyController {

	@Autowired
	PropertyKeyValidator validator;
	
	@Autowired
	PropertyKeyRepository propertyKeyRepo;
	
	// Single get
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_KEY_END_POINT)
	public @ResponseBody ResponseEntity<PropertyKeyResponse> getTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_KEY_ID_NOT_EMPTY) Long Id)
	{
		ValidatedPropertyKey key = validator.getPropertyFromId(Id);
		if( key.getIsErrorPresent() )
		{
			return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(HttpStatus.BAD_REQUEST,key.getMessage()),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(key.getKey()),HttpStatus.OK);
	}
	
	// Create a property key
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= RequestMethod.POST, value= RestEndpointConstants.PROPERTY_KEY_BASE_VAR)
	public @ResponseBody ResponseEntity<PropertyKeyResponse> createPlayerInfo(@RequestBody @Valid PropertyKeyRequest propertyKeyRequest)
	{
		ValidatedPropertyKey validatedPropertykey  = validator.validatePropertyKeyRequest(propertyKeyRequest);
		if( validatedPropertykey.getIsErrorPresent() )
		{
			return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(HttpStatus.BAD_REQUEST, validatedPropertykey.getMessage()), HttpStatus.BAD_REQUEST);
		}
		PropertyKey  key = propertyKeyRepo.save(validatedPropertykey.getKey());
		return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(key), HttpStatus.OK);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= {RequestMethod.PUT, RequestMethod.PATCH }, value= RestEndpointConstants.PROPERTY_KEY_END_POINT)
	public @ResponseBody ResponseEntity<PropertyKeyResponse> updatePropertyKeyInfo( @PathVariable @NotBlank(message =ValidationMessages.PROPERTY_KEY_ID_NOT_EMPTY) Long Id,  @Valid @RequestBody PropertyKeyRequest request)
	{
		ValidatedPropertyKey propertyKey = validator.getPropertyFromId(Id);
		if( propertyKey.getIsErrorPresent() )
		{
			return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(HttpStatus.BAD_REQUEST,propertyKey.getMessage()), HttpStatus.BAD_REQUEST);
		}
		PropertyKey key = propertyKey.getKey();
		ValidatedPropertyKey newPropkey = validator.validatePropertyKeyRequest(request);
		if(newPropkey.getIsErrorPresent())
		{
				return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(HttpStatus.BAD_REQUEST,newPropkey.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		BeanUtils.copyProperties(newPropkey.getKey(), key);
		key = propertyKeyRepo.save(key);
		return new ResponseEntity<PropertyKeyResponse>(new PropertyKeyResponse(key), HttpStatus.OK);
	}
}
