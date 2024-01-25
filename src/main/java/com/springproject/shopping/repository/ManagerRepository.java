package com.springproject.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springproject.shopping.Model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
	Manager findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

}
