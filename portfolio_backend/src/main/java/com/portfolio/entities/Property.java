package com.portfolio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import com.portfolio.util.PropertyType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString( callSuper = false)
@EqualsAndHashCode( callSuper = false)
@NoArgsConstructor
@Entity
public class Property extends AuditParams  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	@Column( unique = true)
	private String name;
	
	private String value;
	
	private Boolean status;
	
	@NotEmpty
	private PropertyType type;
	
	@NotEmpty
	private String key;
	public Property( String name, Boolean status) {
		this.name = name;
		this.status = status;
	}
}
