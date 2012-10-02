package org.ljc.adoptojdk.ReturnClassOwner;

import java.util.List;
import org.jbehave.core.model.Scenario;


public class ReturnClassOwnerScenario extends Scenario {

	@SuppressWarnings("unchecked")
	public ReturnClassOwnerScenario()  {
		super((List<String>) new ReturnClassOwnerSteps());
	}
}
