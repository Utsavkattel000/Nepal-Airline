package com.springproject.airline.service;

import java.util.Set;

import com.springproject.airline.Model.Admin;
import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;

public interface PassengerService {
	void savePassenger(Passenger passengers);

	Set<Passenger> getPassengersByPublicFlight(PublicFlight publicFlight);

	Set<Passenger> getPassengersByFlownFlightAndUser(FlownFlight flownFlight, User user);

	Set<Passenger> getPassengerByFlownFlightAndAdmin(FlownFlight flownFlight, Admin admin);

	Set<Passenger> getPassengersByPublicFlightAndUser(PublicFlight publicFlight, User user);

	Set<Passenger> getPassengerByPublicFlightAndAdmin(PublicFlight publicFlight, Admin admin);

	boolean existsByPublicFlightAndPhoneAndFullName(PublicFlight publicFlight, String phone, String fullName);

}
