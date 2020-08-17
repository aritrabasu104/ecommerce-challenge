package com.tomtom.challenge.dto.product;

import com.tomtom.challenge.dto.category.CategoryIdDto;
import com.tomtom.challenge.dto.page.PageDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchDto {

	private CategoryIdDto category;

	private String title = "";

	private Double minPrice = 0d;

	private Double maxPrice= Double.MAX_VALUE;

	private String description = "";

	private Integer minRating = 0;

	private Integer maxRating = Integer.MAX_VALUE;
	
	private SortColumn sortColumn;
	
	private SortOrder sortOrder;
	
	private PageDto pageDto;
	
	public static enum SortColumn{
		price,title,rating,description;
	}
	public static enum SortOrder{
		ASC,DESC;
	}
	
}
