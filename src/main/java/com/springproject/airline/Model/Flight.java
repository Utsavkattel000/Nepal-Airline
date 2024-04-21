package com.springproject.airline.Model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "flights")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String depAirport;
	private String desAirport;
	private LocalTime depTime;
	private LocalTime arrivalTime;
	private String airline;
	private int capacity;
	private float price;

}
