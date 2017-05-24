package com.portfolio.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portfolio.entities.Property;
import com.portfolio.repositories.PropertyRepository;
import com.portfolio.requests.TeamRequest;
import com.portfolio.util.ValidationMessages;
import com.portfolio.validatedObjects.ValidatedTeam;

@Component
public class TeamValidator {

	@Autowired
	PropertyRepository teamRepository;
	
	/**
	 * To Parse Request to Object 
	 * @param teamRequest
	 * @param playerId
	 * @return
	 */
	public Property getTeamFromRequest( TeamRequest teamRequest)
	{
			return new Property(teamRequest.getName(), teamRequest.getStatus());
	}
	/**
	 * To Validate the Player Information 
	 * @param teamId
	 * @return
	 */
	public ValidatedTeam getTeamFromId ( Long teamId)
	{
		if( null == teamId )
			return new ValidatedTeam(true, ValidationMessages.TEAM_ID_NOT_EMPTY);
		Property team = teamRepository.findOne(teamId);
		if( null == team )
		{
			return new ValidatedTeam(true, ValidationMessages.TEAM_DOES_NOT_EXIST);
		}
		return new ValidatedTeam(team);
	}
}
