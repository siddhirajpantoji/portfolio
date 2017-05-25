package com.portfolio.controllers;

import java.util.List;

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
import com.portfolio.repositories.PropertyRepository;
import com.portfolio.requests.PropertyRequest;
import com.portfolio.response.TeamResponse;
import com.portfolio.util.Messages;
import com.portfolio.util.RestEndpointConstants;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedProperty;
import com.portfolio.validators.PropertyValidator;

@RestController
public class PropertyController {

	@Autowired
	private	PropertyRepository propertyRepository;
	
	@Autowired
	private PropertyValidator teamValidator;
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<TeamResponse> getTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id)
	{
		return getValidatedResponse(Id);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= RequestMethod.POST, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody ResponseEntity<TeamResponse> createPlayerInfo(@RequestBody @Valid PropertyRequest propertyRequest)
	{
		ValidatedProperty validatedProperty  = teamValidator.getTeamFromRequest(propertyRequest);
		if( validatedProperty.getIsErrorPresent() )
		{
			return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.BAD_REQUEST, validatedProperty.getMessage()), HttpStatus.BAD_REQUEST);
		}
		Property team = propertyRepository.save(validatedProperty.getProperty());
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}
	
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= {RequestMethod.PUT, RequestMethod.PATCH }, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<TeamResponse> updatePlayerInfo( @PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id,  @Valid @RequestBody PropertyRequest teamRequest)
	{
		ResponseEntity<TeamResponse> responseEntity = getValidatedResponse(Id);

		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}

		Property team = responseEntity.getBody().getProperty();
		ValidatedProperty newProp = teamValidator.getTeamFromRequest(teamRequest);
		if(newProp.getIsErrorPresent())
		{
				return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.BAD_REQUEST,newProp.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		BeanUtils.copyProperties(newProp.getProperty(), team);
		team = propertyRepository.save(team);
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= { RequestMethod.DELETE }, value= RestEndpointConstants.PROPERTYS_END_POINT )
	public @ResponseBody ResponseEntity<TeamResponse> deleteTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_ID_NOT_EMPTY) Long Id)
	{
		ResponseEntity<TeamResponse> responseEntity = getValidatedResponse(Id);
		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}
		Property team = responseEntity.getBody().getProperty();
		team.setStatus(false);
		propertyRepository.save(team);
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}

	public ResponseEntity<TeamResponse> getValidatedResponse(Long teamId)
	{
		ValidatedProperty validatedTeam = teamValidator.getTeamFromId(teamId);
		if( validatedTeam.getIsErrorPresent() ){
			return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.BAD_REQUEST, validatedTeam.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, validatedTeam.getProperty()), HttpStatus.OK);
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody Page<Property> getPlayerInfo( Pageable pageable)
	{
		return propertyRepository.findAll(pageable); 
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_GET_ALL)
	public @ResponseBody ResponseEntity<List<Property>> getTeamInfo()
	{
		return new ResponseEntity<List<Property>>(propertyRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_KEY_SEARCH)
	public @ResponseBody ResponseEntity<List<Property>> searchAllByKey(@PathVariable @NotBlank(message =ValidationMessages.PROPERTY_KEY_NOT_EMPTY) String key )
	{
		return new ResponseEntity<List<Property>>(propertyRepository.findAll(), HttpStatus.OK); 
	}
}
