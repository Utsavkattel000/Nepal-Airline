package com.springproject.airline.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "passenger")
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String phone;
	private String email;
	private int age;
	private String gender;
	private String nationality;
	@ManyToOne
	@JoinColumn(name = "admin_id")
	private Admin admin;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "public_flight_id")
	private PublicFlight publicFlight;
	@ManyToOne
	@JoinColumn(name = "flown_flight_id")
	private FlownFlight flownFlight;

}
