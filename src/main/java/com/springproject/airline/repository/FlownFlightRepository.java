package com.springproject.airline.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.airline.Model.FlownFlight;

public interface FlownFlightRepository extends JpaRepository<FlownFlight, Long>{
	List<FlownFlight> getFlownFlightsByDepDate(LocalDate today);
	
	

}
