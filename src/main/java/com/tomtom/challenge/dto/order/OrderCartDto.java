package com.tomtom.challenge.dto.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCartDto {
	@NotNull
	@Min(1)
	private Long id;
	
}
