package com.tomtom.challenge.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsReqDto {
	
	@NotNull
	private OrderCartDto cart;
	
	@NotNull
	private String transaction;
	
	@NotNull
	private UserCartDto user;
	
	
}
