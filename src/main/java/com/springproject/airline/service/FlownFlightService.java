package com.springproject.airline.service;

import com.springproject.airline.Model.FlownFlight;

public interface FlownFlightService {
     FlownFlight save(FlownFlight flight);
	
	FlownFlight getById(Long id);
}
