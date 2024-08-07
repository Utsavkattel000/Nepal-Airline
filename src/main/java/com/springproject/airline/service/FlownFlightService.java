package com.springproject.airline.service;

import java.time.LocalDate;
import java.util.List;
import com.springproject.airline.Model.FlownFlight;

public interface FlownFlightService {
     FlownFlight save(FlownFlight flight);
	
	FlownFlight getById(Long id);
	
	List<FlownFlight> getAllFlownFlights();
	List<FlownFlight> getFlownFlightsByDepDate(LocalDate today);
}
