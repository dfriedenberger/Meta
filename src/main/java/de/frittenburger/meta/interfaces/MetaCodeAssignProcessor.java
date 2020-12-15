package de.frittenburger.meta.interfaces;

import de.frittenburger.meta.model.AssignStatement;
import de.frittenburger.meta.model.MetaRuntime;

public interface MetaCodeAssignProcessor {

	void process(MetaRuntime runtime, AssignStatement assignStatement);

}
