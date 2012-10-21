package org.ljc.adoptojdk.ReturnClassContributor;

import java.util.List;
import org.jbehave.core.model.Scenario;


public class ReturnClassContributorScenario extends Scenario {

	@SuppressWarnings("unchecked")
	public ReturnClassContributorScenario()  {
		super((List<String>) new ReturnClassContributorSteps());
	}
}
