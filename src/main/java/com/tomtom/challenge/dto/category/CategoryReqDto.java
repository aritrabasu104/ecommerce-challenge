package com.tomtom.challenge.dto.category;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryReqDto {

	@NotBlank
	private String value;
	
}
