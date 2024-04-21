package com.springproject.airline.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.airline.Model.Admin;
import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
	
	Set<Passenger> findByPublicFlight(PublicFlight publicFlight);
	
	Set<Passenger> findByFlownFlightAndUser(FlownFlight flownFlight,User user);

	Set<Passenger> findByFlownFlightAndAdmin(FlownFlight flownFlight,Admin admin);
	
	Set<Passenger> findByPublicFlightAndUser(PublicFlight publicFlight,User user);
	
	Set<Passenger> findByPublicFlightAndAdmin(PublicFlight publicFlight,Admin admin);
	
	boolean existsByPublicFlightAndPhoneAndFullName(PublicFlight publicFlight,String phone,String fullName);
}
