package com.springproject.airline.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.Admin;
import com.springproject.airline.repository.AdminRepository;
import com.springproject.airline.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public Admin findAdminByEmail(String email) {

		return adminRepo.findByEmail(email);
	}

	@Override
	public boolean verifyPassword(BCryptPasswordEncoder verify, String password, Admin admin) {
		if (admin.getPassword() != null && verify.matches(password, admin.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public String adminSignup(Admin admin) {
		if (adminRepo.existsByEmail(admin.getEmail())) {
			return "Email";
		}
		if (adminRepo.existsByPhone(admin.getPhone())) {
			return "Phone";
		}
		adminRepo.save(admin);
		return null;
	}

	@Override
	public void saveAdmin(Admin admin) {
		adminRepo.save(admin);

	}

	@Override
	public Admin getAdminById(Long id) {

		return adminRepo.getReferenceById(id);
	}

	@Override
	public Set<Admin> getAdminByPublicFlightId(Long publicFlightId) {

		return adminRepo.getByPublicFlightId(publicFlightId);
	}

}
