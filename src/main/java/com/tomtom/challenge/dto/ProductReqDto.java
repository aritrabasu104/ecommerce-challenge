package com.tomtom.challenge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductReqDto {
	
	@NotNull
	private CategoryResDto category;
	
	@NotNull
	@Min(1)
	private Integer quantity;
	
	@NotNull
	private SellerResDto seller;
	
	private ProductDescriptionResDto productDescription;
}
