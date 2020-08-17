package com.tomtom.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne
	@NotNull
	private Category category;
	
	@NotNull
	@Min(0)
	private Integer quantity;
	
	@ManyToOne
	@NotNull
	private Seller seller;
	
	@NotBlank
	private String title;
	
	@NotNull
	@Min(0)
	private Double price;
	
	private String description;
	
	@NotBlank
	private String imageUrl;
	
	private Integer rating;
}
