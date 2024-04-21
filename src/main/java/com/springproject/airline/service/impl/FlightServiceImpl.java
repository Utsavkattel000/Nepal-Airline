package com.springproject.airline.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.Flight;
import com.springproject.airline.repository.FlightRepository;
import com.springproject.airline.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	FlightRepository flightRepo;

	@Override
	public boolean dupeCheck(Flight flight) {
		if (flightRepo.existsByDepAirportAndDesAirportAndDepTimeAndArrivalTimeAndAirline(flight.getDepAirport(),
				flight.getDesAirport(), flight.getDepTime(), flight.getArrivalTime(), flight.getAirline()) == true) {
			return true;
		}
		flightRepo.save(flight);
		return false;
	}

	@Override
	public List<Flight> getAllFlights() {

		return flightRepo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		flightRepo.deleteById(id);

	}

	@Override
	public Flight getFlightById(Long id) {

		return flightRepo.getFlightsById(id);
	}

	@Override
	public void updateFlight(Flight flight) {
		flightRepo.save(flight);
	}

}
