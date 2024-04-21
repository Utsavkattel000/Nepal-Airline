package com.springproject.airline.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springproject.airline.Model.Admin;
import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.service.FlownFlightService;
import com.springproject.airline.service.PassengerService;
import com.springproject.airline.service.PublicFlightService;
import com.springproject.airline.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private FlownFlightService flownFlightService;
	@Autowired
	private PublicFlightService publicFlightService;
	@Autowired
	private PassengerService passengerService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping("/")
	public String userHome(HttpSession session) {
		if (session.getAttribute("activeUser") != null) {
			return "userDashboard";
		}
		if (session.getAttribute("activeAdmin") != null) {
			return "redirect:/admin-dashboard";
		}
		return "home";
	}
    @GetMapping("/user-dashboard")
    public String userDashboard(HttpSession session,RedirectAttributes attribute) {
    	if(session.getAttribute("activeUser")!=null) {
    		return "userDashboard";
    	}
    	
    	attribute.addFlashAttribute("error", "Please Login");
    	return "redirect:/login";
    }
	@GetMapping("/login")
	public String userLogin(HttpSession session) {
		if (session.getAttribute("activeUser") != null) {
			return "userDashboard";
		}
		if (session.getAttribute("activeAdmin") != null) {
			return "redirect:/admin-dashboard";
		}else {
			return "userLogin";
		}
		
	}

	@PostMapping("/login")
	public String postLogin(Model model, @RequestParam String email, @RequestParam String password, HttpSession session,
			RedirectAttributes attribute) {
		User user = userService.finduserByEmail(email);
		if (user != null && userService.verifyPassword(password, user, encoder)) {
			session.removeAttribute("activeAdmin");
			session.setAttribute("activeUser", user);
			if (session.getAttribute("selectedFlight") != null) {
				attribute.addAttribute("id", session.getAttribute("selectedFlight"));
				return "redirect:/book";
			} else {
				return "redirect:/user-dashboard";
			}
		}
		model.addAttribute("error", "Invalid email or password");
		return "userLogin";
	}


	@GetMapping("/signup")
	public String userSignup(HttpSession session) {
		if (session.getAttribute("activeUser") != null) {
			session.invalidate();
		}
		return "signup";
	}

	@PostMapping("/signup")
	public String postuserSignup(Model model, @ModelAttribute User user, RedirectAttributes attribute) {
		if (user.getPassword().equals(user.getPassword2())) {
			try {
				String hashedPassword = encoder.encode(user.getPassword());
				user.setPassword(hashedPassword);
				// this sends the data to signup service while receiving if and which data
				// already exist in database
				String error = userService.userSignup(user);
				if (error == null) {
					attribute.addFlashAttribute("correct", "Account created successfully");
					return "redirect:/login";
				}
				// Add specific error messege this way cause i was not able to extract that
				// information from exception
				model.addAttribute("error", error + " already exists");
				return "signup";
			} catch (DataIntegrityViolationException e) {
				// Add error message to the model
				model.addAttribute("error", "Some info you entered already exists, try new one");
				return "redirect:/signup";
			}
		}
		model.addAttribute("error", "Passwords do not match");
		return "signup";
	}

	@GetMapping("/history")
	public String history(HttpSession session,Model model,RedirectAttributes attribute) {
		if(session.getAttribute("activeUser")!=null) {
			User user=(User) session.getAttribute("activeUser");
			Set<FlownFlight> flownFlights=user.getFlownFlight();
	        model.addAttribute("flightHistory", flownFlights);
			return "flightHistory";
		}
		if(session.getAttribute("activeAdmin")!=null) {
			Admin admin=(Admin) session.getAttribute("activeAdmin");
			Set<FlownFlight> flownFlights=admin.getFlownFlight();
			model.addAttribute("flightHistory", flownFlights);
            return "flightHistory";
		}
		attribute.addFlashAttribute("error", "Please Login");
		return "redirect:/login";
	}
    
	@GetMapping("/passengers")
	public String passengers(HttpSession session,Model model, RedirectAttributes attribute,@RequestParam Long id) {
		if(session.getAttribute("activeUser")!=null) {
			User user=(User) session.getAttribute("activeUser");
		    Set<Passenger> passengers=passengerService.getPassengersByFlownFlightAndUser(flownFlightService.getById(id),user);
	        model.addAttribute("passengers", passengers);
			return "passengers";
		}
		if(session.getAttribute("activeAdmin")!=null) {
			Admin admin=(Admin) session.getAttribute("activeAdmin");
		    Set<Passenger> passengers=passengerService.getPassengerByFlownFlightAndAdmin(flownFlightService.getById(id),admin);
	        model.addAttribute("passengers", passengers);
			return "passengers";
		}
		attribute.addFlashAttribute("error", "Please login");
		return "redirect:/login";
		
	}
	@GetMapping("/pending")
	public String pending(HttpSession session,Model model,RedirectAttributes attribute) {
		if(session.getAttribute("activeUser")!=null) {
			User user=(User) session.getAttribute("activeUser");
			Set<Long> publicFlightIds=user.getPublicFlightId();
			Set<PublicFlight> publicFlights= new HashSet<>();
			for(Long publicFlightId:publicFlightIds) {
				System.out.println("PublicFlightIds: "+publicFlightId);
				publicFlights.add(publicFlightService.getPublicFlightById(publicFlightId));
			}
	        model.addAttribute("pendingFlights", publicFlights);
			return "pendingFlights";
		}
		if(session.getAttribute("activeAdmin")!=null) {
			Admin admin=(Admin) session.getAttribute("activeAdmin");
			Set<Long> publicFlightIds=admin.getPublicFlightId();
			Set<PublicFlight> publicFlights= new HashSet<>();
			for(Long publicFlightId:publicFlightIds) {
				System.out.println("PublicFlightIds: "+publicFlightId);
				publicFlights.add(publicFlightService.getPublicFlightById(publicFlightId));
			}
	        model.addAttribute("pendingFlights", publicFlights);
			return "pendingFlights";
		}
		attribute.addFlashAttribute("error", "Please Login");
		return "redirect:/login";
	}
	@GetMapping("/pendingpassenger")
	public String pendingPassengers(@RequestParam Long id,HttpSession session, Model model, RedirectAttributes attributes) {
		if(session.getAttribute("activeUser")!=null) {
			User user=(User) session.getAttribute("activeUser");
		    Set<Passenger> passengers=passengerService.getPassengersByPublicFlightAndUser(publicFlightService.getPublicFlightById(id),user);
	        model.addAttribute("passengers", passengers);
			return "passengers";
		}
		if(session.getAttribute("activeAdmin")!=null) {
			Admin admin=(Admin) session.getAttribute("activeAdmin");
		    Set<Passenger> passengers=passengerService.getPassengerByPublicFlightAndAdmin(publicFlightService.getPublicFlightById(id),admin);
	        model.addAttribute("passengers", passengers);
			return "passengers";
		}
		attributes.addFlashAttribute("error", "Please Login");
		return "redirect:/login";
	}
	
	
}
