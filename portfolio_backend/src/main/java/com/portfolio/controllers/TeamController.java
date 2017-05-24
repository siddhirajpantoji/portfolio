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
import com.portfolio.requests.TeamRequest;
import com.portfolio.response.TeamResponse;
import com.portfolio.util.Messages;
import com.portfolio.util.RestEndpointConstants;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedTeam;
import com.portfolio.validators.TeamValidator;

@RestController
public class TeamController {

	@Autowired
	private	PropertyRepository teamRepository;
	
	@Autowired
	private TeamValidator teamValidator;
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<TeamResponse> getTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PLAYER_ID_NOT_EMPTY) Long Id)
	{
		return getValidatedResponse(Id);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= RequestMethod.POST, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody ResponseEntity<TeamResponse> createPlayerInfo(@RequestBody @Valid TeamRequest TeamResponse)
	{
		Property team = teamRepository.save(teamValidator.getTeamFromRequest(TeamResponse));
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}
	
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= {RequestMethod.PUT, RequestMethod.PATCH }, value= RestEndpointConstants.PROPERTYS_END_POINT)
	public @ResponseBody ResponseEntity<TeamResponse> updatePlayerInfo( @PathVariable @NotBlank(message =ValidationMessages.TEAM_ID_NOT_EMPTY) Long Id,  @Valid @RequestBody TeamRequest teamRequest)
	{
		ResponseEntity<TeamResponse> responseEntity = getValidatedResponse(Id);

		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}

		Property team = responseEntity.getBody().getProperty();
		BeanUtils.copyProperties(teamRequest, team);
		team = teamRepository.save(team);
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}
	
	@Transactional(  rollbackOn= Exception.class )
	@RequestMapping ( method= { RequestMethod.DELETE }, value= RestEndpointConstants.PROPERTYS_END_POINT )
	public @ResponseBody ResponseEntity<TeamResponse> deleteTeamInfo(@PathVariable @NotBlank(message =ValidationMessages.PLAYER_ID_NOT_EMPTY) Long Id)
	{
		ResponseEntity<TeamResponse> responseEntity = getValidatedResponse(Id);
		if(! HttpStatus.OK.equals(responseEntity.getStatusCode()))
		{
			return responseEntity;
		}
		Property team = responseEntity.getBody().getProperty();
		teamRepository.delete(team);
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, team), HttpStatus.OK);
	}

	public ResponseEntity<TeamResponse> getValidatedResponse(Long teamId)
	{
		ValidatedTeam validatedTeam = teamValidator.getTeamFromId(teamId);
		if( validatedTeam.getIsErrorPresent() ){
			return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.BAD_REQUEST, validatedTeam.getMessage()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<TeamResponse>(new TeamResponse(HttpStatus.OK, Messages.EVERYTHING_OK, validatedTeam.getProperty()), HttpStatus.OK);
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_BASE_VAR)
	public @ResponseBody Page<Property> getPlayerInfo( Pageable pageable)
	{
		return teamRepository.findAll(pageable); 
	}
	
	@RequestMapping ( method= RequestMethod.GET, value= RestEndpointConstants.PROPERTY_GET_ALL)
	public @ResponseBody List<Property> getTeamInfo()
	{
		return teamRepository.findAll();
	}	
}
