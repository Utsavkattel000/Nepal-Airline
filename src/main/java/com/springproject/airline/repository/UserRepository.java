package com.springproject.airline.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);
	
	List<User> getByFlownFlight(FlownFlight flownFlight);

	Set<User> getByPublicFlightId(Long publicFlightId);
}
