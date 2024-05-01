package com.springproject.airline.service;

import java.util.Set;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;

public interface PassengerService {
	void savePassenger(Passenger passengers);

	Set<Passenger> getPassengersByPublicFlight(PublicFlight publicFlight);
	
	Set<Passenger> getPassengersByFlownFlight(FlownFlight flownFlight);

	Set<Passenger> getPassengersByFlownFlightAndUser(FlownFlight flownFlight, User user);

	Set<Passenger> getPassengersByPublicFlightAndUser(PublicFlight publicFlight, User user);

	boolean existsByPublicFlightAndPhoneAndFullName(PublicFlight publicFlight, String phone, String fullName);

}
