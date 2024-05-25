package com.springproject.airline.service;

import java.time.LocalDate;
import java.util.List;

import com.springproject.airline.Model.PublicFlight;

public interface PublicFlightService {
	List<PublicFlight> searchFlight(LocalDate depDate, String departureAirport, String destinationAirport);

	boolean dupeCheck(PublicFlight publicFlight);

	void deleteById(Long id);

	PublicFlight getPublicFlightById(Long id);

	List<PublicFlight> getFlightsByDate(LocalDate today);


	void saveFlight(PublicFlight flight);

	boolean autoUpdate(PublicFlight publicFlight);
	
	List<PublicFlight> getPublicFlightsByDepDate(LocalDate today);
}
