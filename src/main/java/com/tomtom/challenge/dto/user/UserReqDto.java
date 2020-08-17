package com.tomtom.challenge.dto.user;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserReqDto {
	
	@NotBlank
	private String name;
	
}
