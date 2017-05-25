package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.entities.PropertyKey;

public interface PropertyKeyRepository extends JpaRepository<PropertyKey, Long> {

	public PropertyKey findByName(String name);
}
