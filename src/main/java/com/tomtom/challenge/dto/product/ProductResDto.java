package com.tomtom.challenge.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomtom.challenge.dto.category.CategoryResDto;
import com.tomtom.challenge.dto.seller.SellerResDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductResDto {
	
	@NotNull
	@Min(1)
	private Long id;

	@JsonIgnore
	private CategoryResDto category;
	
	private Integer quantity;
	
	@JsonIgnore
	private SellerResDto seller;
	
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
