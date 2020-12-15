package de.frittenburger.meta.interfaces;

import java.util.List;

import de.frittenburger.meta.model.Code;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaCodeBlockProcessor {

	void process(MetaRuntime runtime, List<Code> block);

}
