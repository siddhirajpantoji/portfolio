package com.portfolio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.portfolio.entities.Property;
@RepositoryRestResource

public interface PropertyRepository extends JpaRepository<Property, Long> {

	@Query( nativeQuery = true , value = "Select * from property where key_id  = :key ")
	List<Property> findAllByKey(@Param("key") Long key);
}
