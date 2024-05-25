package com.springproject.airline.service;

import java.util.List;

import com.springproject.airline.Model.Flight;

public interface FlightService {

	boolean dupeCheck(Flight flight);

	List<Flight> getAllFlights();

	void deleteById(Long id);

	Flight getFlightById(Long id);

	void updateFlight(Flight flight);
	
}
