package com.tomtom.challenge.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserCartDto {
	
	@NotNull
	private Long Id;
	
}
