package com.springproject.shopping.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springproject.shopping.Model.Manager;

public interface ManagerService {
	String managerSignup(Manager manager);

	Manager findManagerByEmail(String email);

	List<Manager> getAllManager();

	void deleteManager(long id);

	boolean verifyPassword(String password, Manager manager, BCryptPasswordEncoder encoder);

}
