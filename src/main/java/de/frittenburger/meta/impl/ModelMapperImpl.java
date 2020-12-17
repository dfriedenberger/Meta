package de.frittenburger.meta.impl;

import java.util.List;

import de.frittenburger.meta.interfaces.ModelMapper;
import de.frittenburger.meta.model.MetaModel;
import de.frittenburger.meta.model.MetaRuntime;

public class ModelMapperImpl implements ModelMapper {

	@Override
	public MetaModel getModel(MetaRuntime runtime,String type) {
		
		
		switch(type)
		{
		case "string":
		case "number":
			MetaModel model = new MetaModel();
			model.type = type;
			return model;
		}
		
		List<MetaModel> models = runtime.getAlgorithm().modul.models;
		if(models != null)
			for(MetaModel model : models)
			{
				if(model.name.equals(type))
					return model;
			}
		
		
		throw new RuntimeException("not implemented "+type);
	}

}
