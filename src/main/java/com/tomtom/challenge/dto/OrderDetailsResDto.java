package com.tomtom.challenge.dto;

import javax.validation.constraints.NotNull;

import com.tomtom.challenge.model.OrderDetails.STATUS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsResDto {
	@NotNull
	private Long Id;

	private String transactionId;
	
	private CartResDto cart;
	
	private UserResDto user;
	
	
	private STATUS status;
	
}
