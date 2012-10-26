package org.ljc.adoptojdk.ClassContributorRetriever;

import java.util.List;
import org.jbehave.core.model.Scenario;


public class ClassContributorRetrieverScenario extends Scenario {

	@SuppressWarnings("unchecked")
	public ClassContributorRetrieverScenario()  {
		super((List<String>) new ClassContributorRetrieverSteps());
	}
}
