package com.springproject.airline.service;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springproject.airline.Model.Admin;

public interface AdminService {
	String adminSignup(Admin admin);

	Admin findAdminByEmail(String email);

	boolean verifyPassword(BCryptPasswordEncoder verify, String password, Admin admin);

	void saveAdmin(Admin admin);

	Admin getAdminById(Long id);
	
	Set<Admin> getAdminByPublicFlightId(Long publicFlightId);
	
}
