package com.tomtom.challenge.dto.cart;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartReqDto {

	@NotNull
	private Long Id;

	
}
