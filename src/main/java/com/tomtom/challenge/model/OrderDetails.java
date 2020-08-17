package com.tomtom.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long Id;

	private String transaction;
	
	@OneToOne
	@NotNull
	private Cart cart;
	
	@ManyToOne
	@NotNull
	private User user;
	
	
	private STATUS status;
	
	public enum STATUS{
		CONFIRMED,CANCELLED;
	}
}
