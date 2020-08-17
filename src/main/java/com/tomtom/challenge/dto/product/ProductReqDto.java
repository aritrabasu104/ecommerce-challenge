package com.tomtom.challenge.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import com.tomtom.challenge.dto.category.CategoryIdDto;
import com.tomtom.challenge.dto.seller.SellerIdDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductReqDto {
	
	@NotNull
	private CategoryIdDto category;
	
	@NotNull
	@Min(1)
	private Integer quantity;
	
	@NotNull
	private SellerIdDto seller;
	
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
