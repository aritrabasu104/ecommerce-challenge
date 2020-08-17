package com.tomtom.challenge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	private ProductDescriptionResDto productDescription;
}
