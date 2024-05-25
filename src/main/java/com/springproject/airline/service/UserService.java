package com.springproject.airline.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.User;

public interface UserService {
	String userSignup(User user);

	User finduserByEmail(String email);

	boolean verifyPassword(String password, User user, BCryptPasswordEncoder encoder);

	void saveUser(User user);
	
	User getUserById(Long id);
	
	List<User> getUserByFlownFlight(FlownFlight flownFlight);

	Set<User> getUserByPublicFlightId(Long publicFlightId);
	
}
