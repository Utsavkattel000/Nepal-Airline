package com.springproject.airline.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.repository.PassengerRepository;
import com.springproject.airline.service.PassengerService;


@Service
public class PassengerServiceImpl implements PassengerService{
@Autowired
	PassengerRepository passengerRepo;
	
	
	@Override
	public void savePassenger(Passenger passengers) {
		passengerRepo.save(passengers);
		
	}


	@Override
	public Set<Passenger> getPassengersByPublicFlight(PublicFlight publicFlight) {
		
		return passengerRepo.findByPublicFlight(publicFlight);
	}


	@Override
	public boolean existsByPublicFlightAndPhoneAndFullName(PublicFlight publicFlight, String phone, String fullName) {
		
		return passengerRepo.existsByPublicFlightAndPhoneAndFullName(publicFlight, phone, fullName);
	}


	@Override
	public Set<Passenger> getPassengersByFlownFlightAndUser(FlownFlight flownFlight,User user) {
		
		return passengerRepo.findByFlownFlightAndUser(flownFlight,user);
	}


	


	@Override
	public Set<Passenger> getPassengersByPublicFlightAndUser(PublicFlight publicFlight, User user) {
		
		return passengerRepo.findByPublicFlightAndUser(publicFlight, user);
	}

	@Override
	public Set<Passenger> getPassengersByFlownFlight(FlownFlight flownFlight) {
		
		return passengerRepo.findByFlownFlight(flownFlight);
	}
	
	
	
	
	
	
	
     
	
	
	
}
