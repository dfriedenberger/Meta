package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.model.LoopStatement;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaCodeLoopProcessor {

	void process(MetaRuntime runtime, LoopStatement loopStatement);

}
