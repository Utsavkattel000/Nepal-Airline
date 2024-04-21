package com.springproject.airline.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.airline.Model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

    Set<Admin> getByPublicFlightId(Long id);
	
}
