package com.springproject.airline.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
	
	Set<Passenger> findByPublicFlight(PublicFlight publicFlight);
	
	Set<Passenger> findByFlownFlight(FlownFlight flownFlight);
	
	Set<Passenger> findByFlownFlightAndUser(FlownFlight flownFlight,User user);
	
	Set<Passenger> findByPublicFlightAndUser(PublicFlight publicFlight,User user);
	
	boolean existsByPublicFlightAndPhoneAndFullName(PublicFlight publicFlight,String phone,String fullName);
}
