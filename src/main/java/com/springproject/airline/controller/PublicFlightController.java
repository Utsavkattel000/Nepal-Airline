package com.springproject.airline.controller;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.springproject.airline.Model.Passenger;
import com.springproject.airline.Model.PublicFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.service.PassengerService;
import com.springproject.airline.service.PublicFlightService;
import com.springproject.airline.service.UserService;
import com.springproject.airline.utils.MailUtils;
import com.springproject.airline.utils.PdfGenerator;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class PublicFlightController {
	@Autowired
	PublicFlightService publicFlightService;

	@Autowired
	MailUtils mailUtils;

	@Autowired
	UserService userService;

	@Autowired
	PassengerService passengerService;

	@Autowired
	PdfGenerator pdfGenerator;

	@GetMapping("/flightresult")
	public String searchFlight(@ModelAttribute PublicFlight publicFlights, Model model) {
		List<PublicFlight> availableFlights = new ArrayList<>();
		for (PublicFlight flight : publicFlightService.searchFlight(publicFlights.getDepDate(),
				publicFlights.getDepAirport(), publicFlights.getDesAirport())) {
			if (flight.getCapacity() >= 1) {
				availableFlights.add(flight);
			}
		}
		model.addAttribute("availableFlights", availableFlights);
		return "flightResult";
	}

	@GetMapping("/admin-flight-result")
	public String adminFlightResult(@ModelAttribute PublicFlight publicFlights, Model model, HttpSession session,
			RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Login please");
			return "redirect:/login";
		}
		model.addAttribute("availableFlights", publicFlightService.searchFlight(publicFlights.getDepDate(),
				publicFlights.getDepAirport(), publicFlights.getDesAirport()));
		return "adminFlightResult";
	}

	@GetMapping("/add-public-flight")
	public String addFlights(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") != null) {
			return "addPublicFlight";
		}
		attribute.addFlashAttribute("error", "Don't be sneaky! Please login");
		return "redirect:/login";
	}

	@PostMapping("/add-public-flight")
	public String postAdd(RedirectAttributes attribute, Model model, PublicFlight publicFlight, HttpSession session) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}
		boolean error = publicFlightService.dupeCheck(publicFlight);
		if (error == false) {
			attribute.addFlashAttribute("correct", "Operation successful.");
			return "redirect:/admin-dashboard";
		}
		model.addAttribute("error", "flight already exists. Please recheck all the information.");
		return "redirect:/admin-dashboard";
	}

	@GetMapping("/book")
	public String book(@RequestParam long id, HttpSession session, RedirectAttributes attribute) {
		session.setAttribute("selectedFlight", id);
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}

		return "passengerDetails";
	}

	@PostMapping("/book")
	public String postBook(@ModelAttribute Passenger passenger, HttpSession session,
			@RequestParam String payment_method, @RequestParam int numberOfPassenger, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		if (numberOfPassenger > 1 && numberOfPassenger < 5) {
			String attributeName = "passenger" + numberOfPassenger;
			session.setAttribute(attributeName, passenger);
			session.setAttribute("totalPassenger", numberOfPassenger);
			int newNumberOfPassenger = numberOfPassenger - 1;
			session.setAttribute("numberOfPassenger", newNumberOfPassenger);
			session.setAttribute("payment_method", payment_method);
			return "redirect:/booking";
		} else if (numberOfPassenger == 1) {
			if (passengerService.existsByPublicFlightAndPhoneAndFullName(
					(publicFlightService.getPublicFlightById((Long) session.getAttribute("selectedFlight"))),
					passenger.getPhone(), passenger.getFullName())) {
				attribute.addFlashAttribute("error", "This passenger Already exists for this flight");
				return "redirect:/";
			}
			session.setAttribute("totalPassenger", numberOfPassenger);
			session.setAttribute("passenger1", passenger);
			session.setAttribute("payment_method", payment_method);
			return "redirect:/payment";
		} else {
			attribute.addFlashAttribute("error", "Too many passengers select 1 to 4");
			if (session.getAttribute("activeUser") != null) {

				return "redirect:/user-dashboard";
			} else if (session.getAttribute("activeAdmin") != null) {
				return "redirect:/admin-dashboard";
			} else {
				attribute.addFlashAttribute("error", "Please login");
				return "redirect:/login";
			}
		}
	}

	@GetMapping("/booking")
	public String booking(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		return "passengerDetail";
	}

	@PostMapping("/booking")
	public String postBooking(@ModelAttribute Passenger passenger, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		int numberOfPassenger = (int) session.getAttribute("numberOfPassenger");
		if (numberOfPassenger > 1 && numberOfPassenger < 4) {
			if (passengerService.existsByPublicFlightAndPhoneAndFullName(
					(publicFlightService.getPublicFlightById((Long) session.getAttribute("selectedFlight"))),
					passenger.getPhone(), passenger.getFullName())) {
				attribute.addFlashAttribute("error", "This passenger Already exists for this flight");
				return "redirect:/book";
			}
			String attributeName = "passenger" + numberOfPassenger;
			session.setAttribute(attributeName, passenger);
			int newNumberOfPassenger = numberOfPassenger - 1;
			session.setAttribute("numberOfPassenger", newNumberOfPassenger);

			return "redirect:/booking";
		} else if (numberOfPassenger == 1) {
			if (passengerService.existsByPublicFlightAndPhoneAndFullName(
					publicFlightService.getPublicFlightById((Long) session.getAttribute("selectedFlight")),
					passenger.getPhone(), passenger.getFullName())) {
				attribute.addFlashAttribute("error", "This passenger Already exists for this flight");
				if (session.getAttribute("activeUser") != null) {

					return "redirect:/user-dashboard";
				} else if (session.getAttribute("activeAdmin") != null) {
					return "redirect:/admin-dashboard";
				} else {
					attribute.addFlashAttribute("error", "Please login");
					return "redirect:/login";
				}
			}
			session.setAttribute("passenger1", passenger);
			return "redirect:/payment";
		} else {
			attribute.addFlashAttribute("error", "Invalid number of passengers");
			if (session.getAttribute("activeUser") != null) {

				return "redirect:/user-dashboard";
			} else if (session.getAttribute("activeAdmin") != null) {
				return "redirect:/admin-dashboard";
			} else {
				attribute.addFlashAttribute("error", "Please login");
				return "redirect:/login";
			}
		}
	}

	@GetMapping("/payment")
	public String payment(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		String payment_method = session.getAttribute("payment_method").toString();
		if (payment_method.equals("esewa")) {
			return "esewa";
		} else if (payment_method.equals("imepay")) {

			return "imePay";
		}
		return "redirect:/login";

	}

	@PostMapping("/payment")
	public String postPayment(HttpSession session, @RequestParam String id, @RequestParam String name, Model model,
			RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		int numberOfPassenger = (int) session.getAttribute("totalPassenger");

		Long flightId = (Long) session.getAttribute("selectedFlight");

		PublicFlight flight = publicFlightService.getPublicFlightById(flightId);
		float price = numberOfPassenger * flight.getPrice();
		session.setAttribute("price", price);

		Random random = new Random();
		int randomNumber = random.nextInt(900000) + 100000;
		String otp = Integer.toString(randomNumber);
		session.setAttribute("otp", otp);
		mailUtils.sendEmail(id, "Your OTP for payment of Rs." + price + " is: " + otp, "OTP Do not share:");
		return "redirect:/verifyotp";

	}

	@GetMapping("/verifyotp")
	public String verifyOtp(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		return "otp";
	}

	@PostMapping("/verifyotp")
	public String postVerifyOtp(@RequestParam String otp, Model model, HttpSession session,
			RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		String sessionOtp = (String) session.getAttribute("otp");
		if (sessionOtp.equals(otp)) {
			Set<Passenger> passengers = new HashSet<>();

			Passenger passenger1 = (Passenger) session.getAttribute("passenger1");
			Passenger passenger2 = (Passenger) session.getAttribute("passenger2");
			Passenger passenger3 = (Passenger) session.getAttribute("passenger3");
			Passenger passenger4 = (Passenger) session.getAttribute("passenger4");

			if (passenger1 != null) {
				passengers.add(passenger1);
			}
			if (passenger2 != null) {
				passengers.add(passenger2);
			}
			if (passenger3 != null) {
				passengers.add(passenger3);
			}
			if (passenger4 != null) {
				passengers.add(passenger4);
			}
			session.setAttribute("passengers", passengers);

			return "redirect:/confirm";
		}
		model.addAttribute("error", "Invalid OTP");
		return "otp";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/confirm")
	public String confirm(HttpSession session, Model model, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		Object selectedFlight = session.getAttribute("selectedFlight");
		Object price = session.getAttribute("price");
		Object passengersObject = session.getAttribute("passengers");

		if (selectedFlight != null && price != null && passengersObject != null) {
			Long selectedFlightId = (Long) selectedFlight;
			Float flightPrice = (Float) price;
			Set<Passenger> passengers = (Set<Passenger>) passengersObject;
			session.removeAttribute("price");
			model.addAttribute("flight", publicFlightService.getPublicFlightById(selectedFlightId));
			model.addAttribute("price", flightPrice);
			model.addAttribute("passengers", passengers);
			return "confirm";
		} else {
			attribute.addFlashAttribute("error", "Something went wrong please try again");

			if (session.getAttribute("activeUser") != null) {

				return "redirect:/user-dashboard";
			} else if (session.getAttribute("activeAdmin") != null) {
				return "redirect:/admin-dashboard";
			} else {
				attribute.addFlashAttribute("error", "Please login");
				return "redirect:/login";
			}
		}
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@GetMapping("/final")
	public String finalBook(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			return "redirect:/login";
		}
		User admin = (User) session.getAttribute("activeAdmin");
		User user = (User) session.getAttribute("activeUser");
		Long selectedFlightId = (Long) session.getAttribute("selectedFlight");

		Set<Passenger> passengers = (Set<Passenger>) session.getAttribute("passengers");
		PublicFlight publicFlight = (PublicFlight) publicFlightService.getPublicFlightById(selectedFlightId);
		if (publicFlight != null) {
			if (publicFlight.getCapacity() <= 0 || publicFlight.getCapacity() < passengers.size()) {
				attribute.addFlashAttribute("error", "Not enough seats available");
				return "redirect:/flight-result";
			} else {
				int size = publicFlight.getCapacity();
				int pass = passengers.size();
				int after = size - pass;
				publicFlight.setCapacity(after);
				publicFlightService.saveFlight(publicFlight);
				for (Passenger passenger : passengers) {
					if (passengerService.existsByPublicFlightAndPhoneAndFullName(
							(publicFlightService.getPublicFlightById((Long) session.getAttribute("selectedFlight"))),
							passenger.getPhone(), passenger.getFullName())) {
						attribute.addFlashAttribute("error", "This passenger Already exists for this flight");
						if (session.getAttribute("activeUser") != null) {

							return "redirect:/user-dashboard";
						} else if (session.getAttribute("activeAdmin") != null) {
							return "redirect:/admin-dashboard";
						} else {
							attribute.addFlashAttribute("error", "Please login");
							return "redirect:/login";
						}
					}
					passenger.setPublicFlight(publicFlight);
					if (session.getAttribute("activeAdmin") != null) {
						passenger.setUser(admin);
						admin.addPublicFlightId(selectedFlightId);
						userService.saveUser(admin);
					} else if (session.getAttribute("activeUser") != null) {
						passenger.setUser(user);
						user.addPublicFlightId(selectedFlightId);
						userService.saveUser(user);
					}

					passengerService.savePassenger(passenger);
					System.out.println("successfully booked ticket for passenger id: " + passenger.getId());
				}

				return "redirect:/successfull";

			}

		}
		attribute.addFlashAttribute("error", "Something went wrong");
		return "redirect:/";
	}

	@GetMapping("/successfull")
	public String success(HttpSession session, Model model, RedirectAttributes attribute) {
		User user = null;
		User admin = null;

		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please login");
			return "redirect:/login";
		}

		PublicFlight flight = publicFlightService.getPublicFlightById((Long) session.getAttribute("selectedFlight"));
		Set<Passenger> passengers = new HashSet<>();
		if (session.getAttribute("activeAdmin") != null) {
			admin = (User) session.getAttribute("activeAdmin");
			passengers = passengerService.getPassengersByPublicFlightAndUser(flight, admin);
		} else if (session.getAttribute("activeUser") != null) {
			user = (User) session.getAttribute("activeUser");
			passengers = passengerService.getPassengersByPublicFlightAndUser(flight, user);
		}
		attribute.addAttribute("flight", flight);
		attribute.addAttribute("passengers", passengers);
		String htmlContent = generateHTMLContent(model, flight, passengers);
		String filename = "ticket" + LocalTime.now().getNano();
		pdfGenerator.generateTicket(htmlContent, filename);
		String email = null;
		if (user != null) {
			email = user.getEmail();
		}
		if (admin != null) {
			email = admin.getEmail();
		}
		String filepath = "C:/Users/utsav/Documents/workspace-spring-tool-suite-4-4.18.0.RELEASE/NepalAirline/src/main/resources/static/tickets/"
				+ filename + ".pdf";
		File ticket = new File(filepath);
		mailUtils.sendEmailWithAttachment(email, "Please bring this ticket with you when departing!",
				"Your flight ticket", ticket);
		return "redirect:/ticket";
	}

	@GetMapping("/ticket")
	public String ticket(@RequestParam("flight") PublicFlight flight,
			@RequestParam("passengers") Set<Passenger> passengers, RedirectAttributes attribute, HttpSession session,
			Model model) {
		if (session.getAttribute("activeUser") == null && session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login before booking the flight");
			attribute.addFlashAttribute("error", "Please login");
			return "redirect:/login";
		}
		model.addAttribute("flight", flight);
		model.addAttribute("passengers", passengers);

		return "ticket";
	}

	public String generateHTMLContent(Model model, PublicFlight flight, Set<Passenger> passengers) {
		Context context = new Context();
		context.setVariable("flight", flight);
		context.setVariable("passengers", passengers);
		model.asMap().forEach(context::setVariable);
		TemplateEngine templateEngine = new SpringTemplateEngine();
		String html = "<!DOCTYPE html>\r\n" + "<html lang=\"en\" xmlns:th=\"https://www.thymeleaf.org\">\r\n" + "\r\n"
				+ "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "<title>Airline Ticket</title>\r\n" + "<style>\r\n" + "body {\r\n"
				+ "    font-family: Arial, sans-serif;\r\n" + "    margin: 0;\r\n" + "    padding: 0;\r\n"
				+ "    background-color: #f2f2f2;\r\n" + "  }\r\n" + "  \r\n" + "  .ticket {\r\n"
				+ "    max-width: 600px;\r\n" + "    margin: 50px auto;\r\n" + "    padding: 20px;\r\n"
				+ "    border-radius: 10px;\r\n" + "    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\r\n"
				+ "    background-color: #fff;\r\n" + "  }\r\n" + "  \r\n" + "  .ticket-header {\r\n"
				+ "    display: flex;\r\n" + "    align-items: center;\r\n" + "    justify-content: space-between;\r\n"
				+ "    margin-bottom: 20px;\r\n" + "    border-bottom: 2px solid #e0e0e0;\r\n"
				+ "    padding-bottom: 10px;\r\n" + "  }\r\n" + "  \r\n" + "  .logo {\r\n" + "    max-width: 80px;\r\n"
				+ "  }\r\n" + "  \r\n" + "  .company-name {\r\n" + "    font-size: 24px;\r\n" + "    color: #333;\r\n"
				+ "  }\r\n" + "  \r\n" + "  .ticket-info {\r\n" + "    display: flex;\r\n"
				+ "    justify-content: space-between;\r\n" + "  }\r\n" + "  \r\n" + "  .passenger-info {\r\n"
				+ "    flex-grow: 1;\r\n" + "  }\r\n" + "  \r\n" + "  .passenger-name {\r\n"
				+ "    font-weight: bold;\r\n" + "    font-size: 20px;\r\n" + "    margin-bottom: 10px;\r\n" + "  }\r\n"
				+ "  \r\n" + "  .passenger-id {\r\n" + "    font-size: 14px;\r\n" + "    color: #666;\r\n" + "  }\r\n"
				+ "\r\n" + "  .carry-text {\r\n" + "    font-size: 14px;\r\n" + "    max-width: 250px;\r\n"
				+ "    color: #666;\r\n" + "    margin-top: 5px; /* Adjust margin-top as needed */\r\n" + "  }\r\n"
				+ "  \r\n" + "  \r\n" + "  .flight-details {\r\n" + "    flex-grow: 2;\r\n"
				+ "    text-align: right;\r\n" + "  }\r\n" + "  \r\n" + "  .flight-name {\r\n"
				+ "    font-weight: bold;\r\n" + "    font-size: 18px;\r\n" + "    margin-bottom: 10px;\r\n" + "  }\r\n"
				+ "  \r\n" + "  .departure-info,\r\n" + "  .destination-info {\r\n" + "    font-size: 16px;\r\n"
				+ "    margin-bottom: 5px;\r\n" + "    color: #555;\r\n" + "  }\r\n" + "</style>\r\n" + "</head>\r\n"
				+ "\r\n" + "<body>\r\n" + "    <div th:each=\"passenger : ${passengers}\">\r\n"
				+ "        <div class=\"ticket\">\r\n" + "            <div class=\"ticket-header\">\r\n"
				+ "                <!-- Embedding image as base64 -->\r\n"
				+ "                <img src=\"data:image/png;base64,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\" \r\n"
				+ "                alt=\"Airline Logo\" class=\"logo\">\r\n"
				+ "                <h2 class=\"company-name\">SUNDAR Travels</h2>\r\n" + "            </div>\r\n"
				+ "            <div class=\"ticket-info\">\r\n" + "               <div class=\"passenger-info\">\r\n"
				+ "                  <p>Name: <span class=\"passenger-name\" th:text=\"${passenger.fullName}\"></span></p>\r\n"
				+ "                  <p>ID: <span class=\"passenger-id\" th:text=\"${passenger.id}\"></span></p>\r\n"
				+ "                  <p>Email: <span class=\"passenger-id\" th:text=\"${passenger.email}\"></span></p>\r\n"
				+ "                  <p>Phone: <span class=\"passenger-id\" th:text=\"${passenger.phone}\"></span></p>\r\n"
				+ "                  <p class=\"carry-text\">Please make sure that you have this when coming to the airport. You can have either printed or soft copy.</p>\r\n"
				+ "           </div>\r\n" + "<div class=\"flight-details\">\r\n"
				+ "    <p>Airline: <span class=\"flight-name\" th:text=\"${flight.airline}\"></span></p>\r\n"
				+ "    <p>Departure Date: <span class=\"departure-info\" th:text=\"${flight.depDate}\"></span></p>\r\n"
				+ "    <p>Departure Time: <span class=\"departure-info\" th:text=\"${flight.depTime}\"></span></p>\r\n"
				+ "    <p>Departure Airport: <span class=\"departure-info\" th:text=\"${flight.depAirport}\"></span></p>\r\n"
				+ "    <p>Destination Airport: <span class=\"departure-info\" th:text=\"${flight.desAirport}\"></span></p>\r\n"
				+ "</div>\r\n" + "\r\n" + "            </div>\r\n" + "        </div>\r\n" + "    </div>\r\n"
				+ "</body>\r\n" + "\r\n" + "</html>\r\n" + "";

		String content = templateEngine.process(html, context);
		return content;
	}

	@GetMapping("/editpublic")
	public String edit(@RequestParam Long id, Model model, HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		} else {
			PublicFlight publicFlight = publicFlightService.getPublicFlightById(id);
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = publicFlight.getDepDate().format(dateFormatter);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			String formattedDepTime = publicFlight.getDepTime().format(timeFormatter);
			String formattedArrivalTime = publicFlight.getArrivalTime().format(timeFormatter);
			model.addAttribute("formattedDate", formattedDate);
			model.addAttribute("formattedDepTime", formattedDepTime);
			model.addAttribute("formattedArrivalTime", formattedArrivalTime);
			model.addAttribute("publicFlight", publicFlight);
			return "editPublicFlight";
		}
	}

	@GetMapping("/updatepublic")
	public String update(@ModelAttribute PublicFlight publicFlight, RedirectAttributes attribute, HttpSession session) {
		if (session.getAttribute("activeAdmin") == null) {
			attribute.addFlashAttribute("error", "Please Login");
			return "redirect:/login";
		}
		publicFlightService.saveFlight(publicFlight);
		return "redirect:/admin-dashboard";
	}
	@GetMapping("/cancel")
	public String cancel(HttpSession session) {
		if(session.getAttribute("activeAdmin")!=null) {
			return "redirect:/admin-dashboard";
		} else if(session.getAttribute("activeUser")!=null) {
			return "redirect:/user-dashboard";
		}
	 return "redirect:/login";	
	}
	
	
	
	
	
	
	
	
	
}
