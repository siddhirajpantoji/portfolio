package com.portfolio.controllers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.entities.Property;
import com.portfolio.entities.PropertyKey;
import com.portfolio.repositories.PropertyKeyRepository;
import com.portfolio.repositories.PropertyRepository;
import com.portfolio.requests.PropertyRequest;
import com.portfolio.response.PropertyResponse;
import com.portfolio.util.RestEndpointConstants;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedProperty;
import com.portfolio.validators.PropertyValidator;

@RestController
public class PropertyController {

	@Autowired
	private	PropertyRepository propertyRepository;
	
	@Autowired
	private PropertyValidator propertyValidator;

	@Autowired
	private PropertyKeyRepository propertyKeyRepository;
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<PropertyResponse> getTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id)
	{
		return getValidatedResponse(Id);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= RequestMethod.POST, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody ResponseEntity<PropertyResponse> createPlayerInfo(@RequestBody @Valid PropertyRequest propertyRequest)
	{
		ValidatedProperty validatedProperty  = propertyValidator.getTeamFromRequest(propertyRequest);
		if( validatedProperty.getIsErrorPresent() )
		{
			return new ResponseEntity<PropertyResponse>(new PropertyResponse(HttpStatus.BAD_REQUEST, validatedProperty.getMessage()), HttpStatus.BAD_REQUEST);
		}
		Property team = propertyRepository.save(validatedProperty.getProperty());
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(team), HttpStatus.OK);
	}
	
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= {RequestMethod.PUT, RequestMethod.PATCH }, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<PropertyResponse> updatePlayerInfo( @PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id,  @Valid @RequestBody PropertyRequest teamRequest)
	{
		ResponseEntity<PropertyResponse> responseEntity = getValidatedResponse(Id);

		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}

		Property team = responseEntity.getBody().getProperty();
		ValidatedProperty newProp = propertyValidator.getTeamFromRequest(teamRequest);
		if(newProp.getIsErrorPresent())
		{
				return new ResponseEntity<PropertyResponse>(new PropertyResponse(HttpStatus.BAD_REQUEST,newProp.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		BeanUtils.copyProperties(newProp.getProperty(), team);
		team = propertyRepository.save(team);
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(team), HttpStatus.OK);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= { RequestMethod.DELETE }, value= RestEndpointConstants.PROPERTYS_END_POINT )
	public @ResponseBody ResponseEntity<PropertyResponse> deleteTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id)
	{
		ResponseEntity<PropertyResponse> responseEntity = getValidatedResponse(Id);
		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}
		Property team = responseEntity.getBody().getProperty();
		team.setStatus(false);
		propertyRepository.save(team);
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(team), HttpStatus.OK);
	}

	public ResponseEntity<PropertyResponse> getValidatedResponse(Long teamId)
	{
		ValidatedProperty validatedTeam = propertyValidator.getTeamFromId(teamId);
		if( validatedTeam.getIsErrorPresent() ){
			return new ResponseEntity<PropertyResponse>(new PropertyResponse(HttpStatus.BAD_REQUEST, validatedTeam.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(validatedTeam.getProperty()), HttpStatus.OK);
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody Page<Property> getPlayerInfo( Pageable pageable)
	{
		return propertyRepository.findAll(pageable); 
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_GET_ALL)
	public @ResponseBody ResponseEntity<PropertyResponse> getTeamInfo()
	{
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(propertyRepository.findAll()), HttpStatus.OK);
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_KEY_SEARCH)
	public @ResponseBody ResponseEntity<PropertyResponse> searchAllByKey(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_KEY_NOT_EMPTY) String keyName )
	{		
		PropertyKey propkey = propertyKeyRepository.findByName(keyName);
		if( null == propkey )
		{
			return new ResponseEntity<PropertyResponse>(new PropertyResponse(HttpStatus.BAD_REQUEST, ValidationMessages.PROPERTY_KEY_NOT_EXIST), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<PropertyResponse>(new PropertyResponse(propertyRepository.findAllByKey(propkey.getId())), HttpStatus.OK);
	}
}
