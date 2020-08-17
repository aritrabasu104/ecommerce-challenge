package com.tomtom.challenge.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserResDto {
	
	@NotNull
	private Long Id;
	
	private String name;
	
	private CartResDto cart;
}
