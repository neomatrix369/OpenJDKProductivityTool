package org.ljc.adoptojdk.ReturnClassOwner;

import java.util.List;
import org.jbehave.core.model.Scenario;


public class ReturnClassOwner extends Scenario {

	@SuppressWarnings("unchecked")
	public ReturnClassOwner()  {
		super((List<String>) new ReturnClassOwnerSteps());
	}
}
