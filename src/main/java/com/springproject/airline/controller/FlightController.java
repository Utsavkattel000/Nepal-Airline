package com.springproject.airline.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springproject.airline.Model.Flight;
import com.springproject.airline.service.FlightService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FlightController {
	@Autowired
	FlightService flightService;

	@GetMapping("/add-flight")
	public String addFlights(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") != null) {
			return "addFlight";
		}
		attribute.addFlashAttribute("error", "Don't be sneaky! Please login");
		return "redirect:/login";
	}

	@PostMapping("/add-flight")
	public String postAdd(RedirectAttributes attribute, Model model, Flight flight, HttpSession session) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}
		boolean error = flightService.dupeCheck(flight);
		if (error == false) {
			attribute.addFlashAttribute("correct", "Operation successful.");
			return "redirect:/admin-dashboard";
		}
		model.addAttribute("error", "flight already exists. Please recheck all the information.");
		return "redirect:/admin-dashboard";
	}

	@GetMapping("/view-flights")
	public String viewFlights(Model model, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}
		model.addAttribute("flightList", flightService.getAllFlights());
		return "viewFlights";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam Long id, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		} else {
			flightService.deleteById(id);
			return "redirect:/view-flights";
		}
	}

	@GetMapping("/edit")
	public String edit(@RequestParam Long id, Model model, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		} else {
			Flight flight = flightService.getFlightById(id);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			String formattedDepTime = flight.getDepTime().format(timeFormatter);
			String formattedArrivalTime = flight.getArrivalTime().format(timeFormatter);
			model.addAttribute("formattedDepTime", formattedDepTime);
			model.addAttribute("formattedArrivalTime", formattedArrivalTime);
			model.addAttribute("flight", flight);
			return "editFlight";
		}
	}

	@GetMapping("/update")
	public String update(@ModelAttribute Flight flight, RedirectAttributes attribute, HttpSession session) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}
		flightService.updateFlight(flight);
		return "redirect:/view-flights";
	}

}
