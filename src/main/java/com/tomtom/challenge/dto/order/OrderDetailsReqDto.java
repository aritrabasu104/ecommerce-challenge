package com.tomtom.challenge.dto.order;

import javax.validation.constraints.NotNull;

import com.tomtom.challenge.dto.user.UserCartDto;

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
