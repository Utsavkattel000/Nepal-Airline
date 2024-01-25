package com.springproject.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springproject.shopping.Model.Manager;
import com.springproject.shopping.repository.ManagerRepository;
import com.springproject.shopping.service.ManagerService;

public class ManagerServiceImpl implements ManagerService{
	@Autowired
	private ManagerRepository managerRepo;

	@Override
	public String managerSignup(Manager manager) {
		if(managerRepo.existsByEmail(manager.getEmail())) {
			return "Email";
		}
		if(managerRepo.existsByPhone(manager.getPhone())) {
			return "Phone";
		}
		managerRepo.save(manager);
		return null;
	}

	@Override
	public Manager findManagerByEmail(String email) {
		
		return null;
	}

	@Override
	public List<Manager> getAllManager() {
		
		return null;
	}

	@Override
	public void deleteManager(long id) {
		
		
	}

	@Override
	public boolean verifyPassword(String password, Manager manager, BCryptPasswordEncoder encoder) {
		
		return false;
	}

}
