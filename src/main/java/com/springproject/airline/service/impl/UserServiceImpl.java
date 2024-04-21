package com.springproject.airline.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springproject.airline.Model.FlownFlight;
import com.springproject.airline.Model.User;
import com.springproject.airline.repository.UserRepository;
import com.springproject.airline.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public String userSignup(User user) {
		if (userRepo.existsByEmail(user.getEmail())) {
			return "Email";
		}
		if (userRepo.existsByPhone(user.getPhone())) {
			return "Phone";
		}
		userRepo.save(user);
		return null;
	}

	@Override
	public User finduserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean verifyPassword(String password, User user, BCryptPasswordEncoder encoder) {
		if (user.getPassword() != null && encoder.matches(password, user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public void saveUser(User user) {
		userRepo.save(user);
		
	}

	@Override
	public Set<User> getUserByPublicFlightId(Long publicFlightId) {
		
		return userRepo.getByPublicFlightId(publicFlightId);
	}

	@Override
	public List<User> getUserByFlownFlight(FlownFlight flownFlight) {
		
		return userRepo.getByFlownFlight(flownFlight);
	}

	@Override
	public User getUserById(Long id) {
		
		return userRepo.getReferenceById(id);
	}
    
	
	
}
