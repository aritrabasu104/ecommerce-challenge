package com.tomtom.challenge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedEntityGraph(
		  name = "product-entity-graph-with-min-details",
		  attributeNodes = {
		    @NamedAttributeNode("seller"),
		    @NamedAttributeNode(value = "productDescription", subgraph = "productDescription-subgraph"),
		  },
		  subgraphs = {
		    @NamedSubgraph(
		      name = "productDescription-subgraph",
		      attributeNodes = {
		        @NamedAttributeNode("title"),@NamedAttributeNode("imageUrl"),@NamedAttributeNode("price")
		      }
		    )
		  }
		)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	@ManyToOne
	@NotNull
	private Category category;
	
	@NotNull
	@Min(0)
	private Integer quantity;
	
	@ManyToOne
	@NotNull
	private Seller seller;
	
	@OneToOne
	@NotNull
	private ProductDescription productDescription;
}
