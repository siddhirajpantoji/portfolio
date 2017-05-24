package com.portfolio.requests;


import javax.validation.constraints.NotNull;

import com.portfolio.util.ValidationMessages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false )
@NoArgsConstructor
public class TeamRequest extends BaseRequest {
	
	@NotNull ( message = ValidationMessages.TEAM_NAME_NOT_EMPTY )
	//@NotBlank (message = ValidationMessages.TEAM_NAME_NOT_EMPTY)
	private String name;
	
	private Boolean status;
	
}
