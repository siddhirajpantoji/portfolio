package com.portfolio.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.portfolio.util.Constants;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@MappedSuperclass
public class AuditParams {

	@JsonFormat (timezone = Constants.TIME_ZONE, shape = Shape.STRING, pattern= Constants.DATE_TIME_FORMAT )
	protected Date createdTs;
	

	@JsonFormat (timezone = Constants.TIME_ZONE, shape = Shape.STRING, pattern= Constants.DATE_TIME_FORMAT )
	protected Date modifiedTs;
	
	
	/*
	 * If the property is Active or not 
	 */
	private Boolean status;
	
	@PrePersist
	void onCreate()
	{
		this.setCreatedTs(new Date());
	}
	
	@PreUpdate
	void onUpdate()
	{
		this.setModifiedTs(new Date());
	}
}
