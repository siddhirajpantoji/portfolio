package com.portfolio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

	@NotEmpty
	private PropertyType type;

	@OneToOne( optional = false)
	private PropertyKey key;

	public Property( String name, String value, PropertyType type, PropertyKey key) {
		this.name = name;
		this.value = value;
		this.type = type;
		this.key = key;
	}
	
}
