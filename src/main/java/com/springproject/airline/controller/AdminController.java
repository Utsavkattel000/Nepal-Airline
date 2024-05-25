package com.springproject.airline.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.service.FlownFlightService;
import com.springproject.airline.service.PassengerService;
import com.springproject.airline.service.PublicFlightService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private PassengerService passengerService;

	@Autowired
	private PublicFlightService publicFlightService;

	@Autowired
	private FlownFlightService flownFlightService;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping("/about")
	public String about() {

		return "about";
	}

	@GetMapping("/admin-dashboard")
	public String adminDashboard(HttpSession session, RedirectAttributes attribute,Model model) {
		if (session.getAttribute("activeAdmin") != null) {
			User user=(User) session.getAttribute("activeAdmin");
			model.addAttribute("fullname",user.getFullName());
			return "adminDashboard";
		}
		attribute.addFlashAttribute("error", "Trying to be sneaky? Login now");
		return "redirect:/login";
	}

	@GetMapping("/all-passengers")
	public String allPassenger(@RequestParam long id, HttpSession session, Model model, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please login");
			return "redirect:/login";
		}
		Set<Passenger> passengers = passengerService
				.getPassengersByPublicFlight(publicFlightService.getPublicFlightById(id));
		model.addAttribute("passengers", passengers);
		return "passengers";
	}

	@GetMapping("/flown-flights")
	public String flownFlights(Model model, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please login");
			return "redirect:/login";
		}
		model.addAttribute("flownFlights", flownFlightService.getAllFlownFlights());
		return "flownFlights";
	}

	@GetMapping("/flown-passengers")
	public String flownPassengers(Model model, @RequestParam Long id, HttpSession session,
			RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please login");
			return "redirect:/login";
		}
		model.addAttribute("passengers", passengerService.getPassengersByFlownFlight(flownFlightService.getById(id)));
		return "passengers";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes attribute) {
		session.invalidate();
		attribute.addFlashAttribute("correct", "Logout Successful");
		return "redirect:/login";
	}

	@GetMapping("/booked-flights")
	public String bookedFlights(HttpSession session, RedirectAttributes attribute, Model model) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}

		List<PublicFlight> publicFlights = publicFlightService.getPublicFlightsByDepDate(LocalDate.now());
		System.out.println(LocalDate.now());
		List<PublicFlight> completlyBooked = new ArrayList<>();
		List<PublicFlight> partiallyBooked = new ArrayList<>();
		List<PublicFlight> untouched = new ArrayList<>();
		for(PublicFlight publicFlight:publicFlights) {
			if(publicFlight.getCapacity()==0) {
				completlyBooked.add(publicFlight);
			}
			else if(publicFlight.getPassengers().size()==0) {
				untouched.add(publicFlight);
			}else {
				partiallyBooked.add(publicFlight);
			}
		}
		
        System.out.println(completlyBooked.toString());
		
		System.out.println(partiallyBooked.toString());
		System.out.println(untouched.toString());
		List<FlownFlight> flownFlights = flownFlightService.getFlownFlightsByDepDate(LocalDate.now());
		System.out.println(flownFlights.toString());
        model.addAttribute("completelyBooked", completlyBooked);
        model.addAttribute("partiallyBooked", partiallyBooked);
        model.addAttribute("untouched", untouched);
        model.addAttribute("flownFlights", flownFlights);
		return "bookedFlights";
	}

}
