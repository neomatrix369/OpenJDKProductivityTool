package org.ljc.adoptojdk.ReturnClassOwner;

import static org.junit.Assert.assertTrue;
import static org.ljc.adoptojdk.Fluency.*;
import static org.ljc.adoptojdk.DatabaseOfOpenJDKContributors.*;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.ljc.adoptojdk.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.FullyQualifiedClassName;
import org.ljc.adoptojdk.NotAFullyQualifiedClassNameException;

public class ReturnClassOwnerSteps extends Steps
{

	private String fullyQualifiedClassName; 
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private Object classContributor;


	@Given("We have details of a list of contributors who have submitted patches to OpenJDK")
	public void getListOfOpenJDKContributors()
	{
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();
	}

	
	@When("A developer runs the utility program in Linux/Unix and passes fully qualified class name as a parameter")
	public void parameterPassed(String paramClassName) throws NotAFullyQualifiedClassNameException
	{	
		fullyQualifiedClassName = getFullyQualifiedClassName(paramClassName);
		classContributor = getClassOwnerDetails(fullyQualifiedClassName); 
	}

	private String[] getClassOwnerDetails(String fullyQualifiedClassName) {
		return dbOpenJDKContributors.findRecordBy(FULLY_QUALIFIED_CLASS_NAME, fullyQualifiedClassName);
	}

	private String getFullyQualifiedClassName(String paramClassName)
			throws NotAFullyQualifiedClassNameException {
		return new FullyQualifiedClassName(paramClassName).getFullyQualifiedClassName();
	}

	
	@Then("the utility program should look into the database containing list of contributors and return their details, which will contain their email address, etc...")
	public void searchOwner()
	{
		assertTrue(ifItIs(not(classContributor.equals(""))));
	}
}