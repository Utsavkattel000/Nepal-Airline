package com.springproject.airline.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "publicFlights")
public class PublicFlight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private LocalDate depDate;
	private String depAirport;
	private String desAirport;
	private LocalTime depTime;
	private LocalTime arrivalTime;
	private String airline;
	private float price;
	private int capacity;
	@OneToMany(mappedBy = "publicFlight")
    private Set<Passenger> passengers;
	private LocalTime originalDepTime;
	private LocalDate originalDepDate;
	private String orginalDepAirport;
	private String orginalDesAirport;
}
