package com.springproject.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManagerController {
	@GetMapping("/")
	public String userHome() {
		
		return "userHome";
	}
	@GetMapping("/login")
	public String managerLogin(HttpSession session) {
		
		return "managerLogin";
	}
	@PostMapping("/login")
	public String postLogin(Model model,@RequestParam String email, @RequestParam String password) {
		
		return "dashboard";
	}
	@GetMapping("/signup")
	public String managerSignup() {
		
		return "managerSignup";
	}

}
