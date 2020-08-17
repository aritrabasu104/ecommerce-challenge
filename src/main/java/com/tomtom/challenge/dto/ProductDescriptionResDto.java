package com.tomtom.challenge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ProductDescriptionResDto {
	
	@NotNull
	@Min(1)
	private Long id;

	private String title;
	
	private Double price;
	
	private String description;
	
	private String imageUrl;
	
	private Integer rating;
}
