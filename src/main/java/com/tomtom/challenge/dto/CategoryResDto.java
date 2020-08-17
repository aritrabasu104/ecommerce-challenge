package com.tomtom.challenge.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryResDto {

	@NotNull
	private Long Id;

	private String value;
	
	private List<ProductResDto> products;
}
