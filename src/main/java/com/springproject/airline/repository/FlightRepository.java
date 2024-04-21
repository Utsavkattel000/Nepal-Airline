package com.springproject.airline.repository;

import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springproject.airline.Model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

	boolean existsByDepAirportAndDesAirportAndDepTimeAndArrivalTimeAndAirline(String departureAirport,
			String destinationAirport, LocalTime depTime, LocalTime arrivalTime, String airline);

	Flight getFlightsById(Long id);

}
