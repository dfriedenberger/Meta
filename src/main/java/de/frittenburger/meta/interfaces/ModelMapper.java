package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.model.MetaModel;
import de.frittenburger.meta.model.MetaRuntime;

public interface ModelMapper {

	MetaModel getModel(MetaRuntime runtime, String type);

}
