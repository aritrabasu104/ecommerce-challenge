package com.tomtom.challenge.dto.user;

import javax.validation.constraints.NotNull;

import com.tomtom.challenge.dto.cart.CartResDto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserResDto {
	
	@NotNull
	private Long Id;
	
	private String name;
	
	private CartResDto cart;
}
