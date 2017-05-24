package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.portfolio.entities.Property;

@RepositoryRestResource
public interface PropertyRepository extends JpaRepository<Property, Long> {

}
