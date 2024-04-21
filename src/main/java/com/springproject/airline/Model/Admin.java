package com.springproject.airline.Model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String address;
	@Column(unique = true)
	private String email;
	private String password;
	@Column(unique = true)
	private String phone;
	@Transient
	private String password2;
	@OneToMany(mappedBy = "admin")
    private Set<Passenger> passengers;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable
	private Set<Long> publicFlightId= new HashSet<Long>();
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<FlownFlight> flownFlight;
	
	public void addPublicFlightid(Long publicFlight) {
		publicFlightId.add(publicFlight);
	}
	
	
	
	
	
	
	
	
	
	
}
