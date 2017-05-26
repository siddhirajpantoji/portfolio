package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.entities.PropertyKey;

public interface PropertyKeyRepository extends JpaRepository<PropertyKey, Long> {

	
	@Query( nativeQuery = true, value = "Select * From property_key where status = 1 and name=:name")
	PropertyKey findByName(@Param("name") String name);
}
