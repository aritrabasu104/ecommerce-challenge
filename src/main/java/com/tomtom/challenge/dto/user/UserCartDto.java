package com.tomtom.challenge.dto.user;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserCartDto {
	
	@NotNull
	private Long Id;
	
}
