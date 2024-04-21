package com.springproject.airline.controller;

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
import com.springproject.airline.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@GetMapping("/admin-login")
	public String adminLogin(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") != null) {
			attribute.addFlashAttribute("correct", "You are already logged in");
			return "redirect:/admin-dashboard";
		}

		return "adminLogin";
	}

	@PostMapping("/admin-login")
	public String postLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model,
			RedirectAttributes attribute) {
		Admin admin = adminService.findAdminByEmail(email);
		if (admin != null && adminService.verifyPassword(encoder, password, admin)) {
			if(session.getAttribute("selectedFlight")!=null) {
				session.setAttribute("activeAdmin", admin);
				attribute.addAttribute("id", session.getAttribute("selectedFlight"));
				return "redirect:/book";
			}
			session.removeAttribute("activeUser");
			session.setAttribute("activeAdmin", admin);
			return "redirect:/admin-dashboard";
		}
		attribute.addFlashAttribute("error", "Invalid Email or Password");
		return "redirect:/admin-login";
	}

	@GetMapping("/admin-dashboard")
	public String adminDashboard(HttpSession session, RedirectAttributes attribute) {
		if (session.getAttribute("activeAdmin") != null) {
			return "adminDashboard";
		}
		attribute.addFlashAttribute("error", "Trying to be sneaky? Login now");
		return "redirect:/admin-login";
	}

	@GetMapping("/addAdmin")
	public String addAdmin() {
		return "addAdmin";
	}

	@PostMapping("/addAdmin")
	public String postAdd(Model model, @ModelAttribute Admin admin, RedirectAttributes attribute) {
		if (admin.getPassword().equals(admin.getPassword2())) {
			try {
				String hashedPassword = encoder.encode(admin.getPassword());
				admin.setPassword(hashedPassword);
				// this sends the data to signup service while receiving if and which data
				// already exist in database
				String error = adminService.adminSignup(admin);
				if (error == null) {
					attribute.addFlashAttribute("correct", "account created succesfully");
					return "redirect:/admin-login";
				}
				// Add specific error message this way cause I was not able to extract that
				// information from exception
				attribute.addFlashAttribute("error", error + " already exists");
				return "redirect:/addAdmin";
			} catch (DataIntegrityViolationException e) {
				// Add error message to the model
				attribute.addFlashAttribute("error", "Some info you entered already exists, try new one");
				return "redirect:/addAdmin";
			}
		}
		attribute.addFlashAttribute("error", "Passwords do not match");
		return "redirect:/addAdmin";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes attribute) {
		session.invalidate();
		attribute.addFlashAttribute("correct", "Logout Successful");
		return "redirect:/login";
	}

}
