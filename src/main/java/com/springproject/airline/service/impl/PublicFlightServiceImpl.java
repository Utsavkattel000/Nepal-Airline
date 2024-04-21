package com.springproject.airline.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.repository.PublicFlightRepository;
import com.springproject.airline.service.PublicFlightService;

@Service
public class PublicFlightServiceImpl implements PublicFlightService {
	@Autowired
	PublicFlightRepository publicFlightRepo;

	@Override
	public List<PublicFlight> searchFlight(LocalDate departureDate, String departureAirport,
			String destinationAirport) {
		publicFlightRepo.findByDepDateAndDepAirportAndDesAirport(departureDate, departureAirport, destinationAirport);
		return publicFlightRepo.findByDepDateAndDepAirportAndDesAirport(departureDate, departureAirport,
				destinationAirport);
	}

	@Override
	public boolean dupeCheck(PublicFlight publicFlight) {
		if (publicFlightRepo
				.existsByDepDateAndDepAirportAndDesAirportAndDepTimeAndArrivalTimeAndAirlineAndPriceAndCapacity(
						publicFlight.getDepDate(), publicFlight.getDepAirport(), publicFlight.getDesAirport(),
						publicFlight.getDepTime(), publicFlight.getArrivalTime(), publicFlight.getAirline(),
						publicFlight.getPrice(), publicFlight.getCapacity()) == true) {
			return true;
		}
		publicFlightRepo.save(publicFlight);
		return false;
	}

	@Override
	public void deleteById(Long id) {
		publicFlightRepo.deleteById(id);

	}

	@Override
	public PublicFlight getPublicFlightById(Long id) {

		return publicFlightRepo.getReferenceById(id);
	}

	

	@Override
	public boolean autoUpdate(PublicFlight publicFlight) {
		if (publicFlightRepo.existsByOriginalDepDateAndOriginalDepTimeAndOrginalDepAirportAndOrginalDesAirport(publicFlight.getOriginalDepDate(), publicFlight.getOriginalDepTime(),publicFlight.getOrginalDepAirport(), publicFlight.getOrginalDesAirport()) == true) {
			return false;
		}
		publicFlightRepo.save(publicFlight);
		return true;
	}

	@Override
	public List<PublicFlight> getFlightsByDate(LocalDate today) {

		return publicFlightRepo.getFlightsOnOrBeforeDate(today);
	}


	

	@Override
	public void saveFlight(PublicFlight publicFlight) {
		publicFlightRepo.save(publicFlight);
		
	}

	

	

	

}
