package com.portfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.entities.PropertyKey;

public interface PropertyKeyRepository extends JpaRepository<PropertyKey, Long> {

	@Query( nativeQuery = true, value = "Select * From property_key where status = 1 and name=:name")
	PropertyKey findByName(@Param("name") String name);
	
	@Query( nativeQuery = true, value = "Select * From property_key where status = 1 and id=:id")
	PropertyKey findOne(@Param("id") Long id);
	
	@Override
	@Query( nativeQuery = true, value = "Select * From property_key where status = 1")
	List<PropertyKey> findAll();
}
