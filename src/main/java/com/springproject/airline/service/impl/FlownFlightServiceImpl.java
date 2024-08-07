package com.springproject.airline.service.impl;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.repository.FlownFlightRepository;
import com.springproject.airline.service.FlownFlightService;

@Service
public class FlownFlightServiceImpl implements FlownFlightService {
	@Autowired
	FlownFlightRepository flownFlightRepo;

	@Override
	public FlownFlight save(FlownFlight flight) {
    FlownFlight flownFlight= flownFlightRepo.save(flight);
    return flownFlight;
	}

	@Override
	public FlownFlight getById(Long id) {
		
		return flownFlightRepo.getReferenceById(id);
	}

	@Override
	public List<FlownFlight> getAllFlownFlights() {
		
		return flownFlightRepo.findAll();
	}
	@Override
	public List<FlownFlight> getFlownFlightsByDepDate(LocalDate today) {
		
		return flownFlightRepo.getFlownFlightsByDepDate(today);
	}

}
