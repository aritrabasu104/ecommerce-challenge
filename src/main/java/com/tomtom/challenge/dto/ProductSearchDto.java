package com.tomtom.challenge.dto;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.tomtom.challenge.model.Category;
import com.tomtom.challenge.model.Seller;

public class ProductSearchDto {

	private Category category;
	
	private Integer quantity;
	
	
}
