package com.tomtom.challenge.dto.seller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SellerIdDto {

	@NotNull
	@Min(1)
	private Long Id;
	
}
