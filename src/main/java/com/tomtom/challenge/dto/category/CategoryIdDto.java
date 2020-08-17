package com.tomtom.challenge.dto.category;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryIdDto {

	@NotNull
	private Long Id;

}
