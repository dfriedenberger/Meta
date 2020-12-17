package de.frittenburger.meta.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class MetaModel {

	@JsonInclude(Include.NON_NULL)
	public String name;
	
	public String type;
	
	@JsonInclude(Include.NON_NULL)
	public List<MetaModel> properties;
	
	@JsonInclude(Include.NON_NULL)
	public MetaModel items;
	
}
