package com.tomtom.challenge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductDescriptionReqDto {

	@NotBlank
	private String title;
	
	@NotNull
	@Min(1)
	private Double price;
	
	private String description;
	
	@NotBlank
	@URL
	private String imageUrl;
	
	private Integer rating;
}
