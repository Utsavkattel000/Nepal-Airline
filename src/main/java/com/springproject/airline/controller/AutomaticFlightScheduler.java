package com.springproject.airline.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.Flight;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.service.FlownFlightService;
import com.springproject.airline.service.PassengerService;
import com.springproject.airline.service.FlightService;
import com.springproject.airline.service.PublicFlightService;
import com.springproject.airline.service.UserService;
import jakarta.transaction.Transactional;

@Component
public class AutomaticFlightScheduler {
	@Autowired
	FlightService flightService;
	@Autowired
	PublicFlightService publicFlightService;
	@Autowired
	FlownFlightService flownFlightService;
	@Autowired
	PassengerService passengerService;
	@Autowired
	UserService userService;

	@Scheduled(cron = "0/30 27 * * * ?")
	public void autoUpdateFlight() {
		List<Flight> flights = flightService.getAllFlights();
		LocalDate today = LocalDate.now();
		for (int i = 1; i <= 12; i++) {
			for (Flight flight : flights) {
				PublicFlight publicFlight = new PublicFlight();
				publicFlight.setDepAirport(flight.getDepAirport());
				publicFlight.setDesAirport(flight.getDesAirport());
				publicFlight.setDepTime(flight.getDepTime());
				publicFlight.setArrivalTime(flight.getArrivalTime());
				publicFlight.setAirline(flight.getAirline());
				publicFlight.setCapacity(flight.getCapacity());
				publicFlight.setPrice(flight.getPrice());
				publicFlight.setDepDate(today.plusDays(i));
				publicFlight.setOriginalDepTime(flight.getDepTime());
				publicFlight.setOriginalDepDate(today.plusDays(i));
				publicFlight.setOrginalDepAirport(flight.getDepAirport());
				publicFlight.setOrginalDesAirport(flight.getDesAirport());
				boolean updateStatus=publicFlightService.autoUpdate(publicFlight);
				System.out.println("update status"+updateStatus);
			}
		}

	}
    @Transactional
	@Scheduled(cron = "0 0/1 * * * ?")
	public void autoRemoveFlight() {
		LocalTime nowTime = LocalTime.now().withNano(0);
		LocalDate nowDate = LocalDate.now();
		System.out.println("Autometically removing flight from publicFlight where Departure date is: "+nowDate+" and departure Time is: "+nowTime);
		List<PublicFlight> todaysFlight = publicFlightService.getFlightsByDate(nowDate);
		if (todaysFlight != null) {
			for (PublicFlight publicFlight : todaysFlight) {
				LocalTime depTime = publicFlight.getDepTime();
				LocalDate depDate= publicFlight.getDepDate();
				if (depTime.equals(nowTime) || depTime.isBefore(nowTime) || depDate.isBefore(nowDate) ) {
					if (publicFlight != null) {
						Set<User> users = new HashSet<>();
						users.addAll(userService.getUserByPublicFlightId(publicFlight.getId()));
						Set<Passenger> passengers = passengerService.getPassengersByPublicFlight(publicFlight);
						FlownFlight flownFlight = new FlownFlight();
						flownFlight.setDepDate(publicFlight.getDepDate());
						flownFlight.setDepAirport(publicFlight.getDepAirport());
						flownFlight.setDesAirport(publicFlight.getDesAirport());
						flownFlight.setDepTime(publicFlight.getDepTime());
						flownFlight.setArrivalTime(publicFlight.getArrivalTime());
						flownFlight.setAirline(publicFlight.getAirline());
						flownFlight.setPrice(publicFlight.getPrice());
						flownFlight.setCapacity(publicFlight.getCapacity());
						flownFlight.setPassengers(publicFlight.getPassengers());
						flownFlight = flownFlightService.save(flownFlight);
						for (Passenger passenger : passengers) {
							passenger.setFlownFlight(flownFlight);
							passenger.setPublicFlight(null);
							passengerService.savePassenger(passenger);
						}

						for (User user : users) {
							Set<FlownFlight> flight= user.getFlownFlight();
							flight.add(flownFlight);
							System.out.println("FlownFLight id check "+flownFlight.getId());
							user.setFlownFlight(flight);
							Set<Long> ids=user.getPublicFlightId();
							System.out.println("length of ids before "+ids.size());
							System.out.println("Id of to be removed publicFlightId "+publicFlight.getId());
							boolean worked= ids.remove(publicFlight.getId());
							System.out.println("after "+ids.size());
							System.out.println("remove status "+worked);
							user.setPublicFlightId(ids);
							userService.saveUser(user);
						}

						publicFlightService.deleteById(publicFlight.getId());
					}
				}
			}
		}
		System.out.println("There were no flights to remove for today.");

	}

}

























//
//class AutoRemoveFlight implements Runnable {
//
//	@Autowired
//	FlightService flightService;
//
//	@Autowired
//	PublicFlightService publicFlightService;
//
//	@Getter
//	@Setter
//	private LocalTime closestTime = null;
//
//	@Getter
//	@Setter
//	private String scheduleCron;
//
//	public void getTime() {
//		LocalTime currentTime = LocalTime.now();
//		List<PublicFlight> todaysFlight = publicFlightService.getFlightsByDate(LocalDate.now());
//		
//		for (PublicFlight publicFlight : todaysFlight) {
//			LocalTime depTime = publicFlight.getDepTime();
//			if (depTime.isAfter(currentTime) && (closestTime == null || depTime.isBefore(closestTime))) {
//				closestTime = depTime;
//				scheduleCron = String.format("0 %d %d * * *", depTime.getMinute(), depTime.getHour());
//			}
//		}
//
//	}
////
//	public void autoRemoveFlight() {
//		PublicFlight publicFlight = publicFlightService.getFlightsByDepTimeAndDepDate(closestTime, LocalDate.now());
//		flownFlight flownFlight = new flownFlight();
//		flownFlight.setDepDate(publicFlight.getDepDate());
//		flownFlight.setDepAirport(publicFlight.getDepAirport());
//		flownFlight.setDesAirport(publicFlight.getDesAirport());
//		flownFlight.setDepTime(publicFlight.getDepTime());
//		flownFlight.setArrivalTime(publicFlight.getArrivalTime());
//		flownFlight.setAirline(publicFlight.getAirline());
//		flownFlight.setPrice(publicFlight.getPrice());
//		flownFlight.setCapacity(publicFlight.getCapacity());
//		flownFlight.setUser(publicFlight.getUser());
//		flownFlight.setAdmin(publicFlight.getAdmin());
//		flownFlight.setPassenger(publicFlight.getPassenger());
//        
//	}
//
//	@Override
//	public void run() {
//		autoRemoveFlight();
//	}
//}
//
//class ScheduleRemoval implements SchedulingConfigurer {
//	@Autowired
//	AutoRemoveFlight remove;
//
//	@Override
//	public void configureTasks(ScheduledTaskRegistrar addTask) {
//		addTask.addCronTask(remove, remove.getScheduleCron());
//
//	}
//}
