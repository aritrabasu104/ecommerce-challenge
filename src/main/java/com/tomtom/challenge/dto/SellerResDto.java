package com.tomtom.challenge.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SellerResDto {

	@NotNull
	@Min(1)
	private Long Id;
	
	private String name;
	
	private String detail;
	
	private List<ProductResDto> products;
}
