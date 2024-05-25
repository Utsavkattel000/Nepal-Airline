package com.springproject.airline.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springproject.airline.Model.PublicFlight;

public interface PublicFlightRepository extends JpaRepository<PublicFlight, Long> {
	List<PublicFlight> findByDepDateAndDepAirportAndDesAirport(LocalDate departureDate, String departureAirport, String destinationAirport);

	boolean existsByDepDateAndDepAirportAndDesAirportAndDepTimeAndArrivalTimeAndAirlineAndPriceAndCapacity(
			LocalDate departureDate, String departureAirport, String destinationAirport, LocalTime depTime,
			LocalTime arrivalTime, String airline, float price, int capacity);
	
    boolean existsByOriginalDepDateAndOriginalDepTimeAndOrginalDepAirportAndOrginalDesAirport(LocalDate originalDepDate,LocalTime originalDepTime,String depAirport,String desAirport);
	

	PublicFlight getFlightsById(Long id);

	@Query("SELECT pf FROM PublicFlight pf WHERE pf.depDate <= :today")
    List<PublicFlight> getFlightsOnOrBeforeDate(@Param("today") LocalDate today);
	
	PublicFlight getPublicFlightByDepTimeAndDepDate(LocalTime closestTime,LocalDate today);
	
    List<PublicFlight> getFlightsByDepDate(LocalDate today);
	 
}
