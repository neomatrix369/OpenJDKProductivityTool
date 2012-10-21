package org.ljc.adoptojdk.ReturnClassContributor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;
//import static org.ljc.adoptojdk.Others.Fluency.*;
import static org.hamcrest.CoreMatchers.*;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.Others.FullyQualifiedClassName;
import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;

public class ReturnClassContributorSteps extends Steps
{

	private String fullyQualifiedClassName; 
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private Object classContributor;
 
	@Given("We have details of a list of contributors $contributors who have submitted patches to OpenJDK sorted by date of submission")
	public void getListOfOpenJDKContributors()
	{
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();
	}

	
	@When("A developer runs the utility program in Linux/Unix and passes a fully qualified class name $classname as a parameter")
	public void parameterPassed(String paramClassName) throws NotAFullyQualifiedClassNameException
	{	
		fullyQualifiedClassName = getFullyQualifiedClassName(paramClassName);
		classContributor = getClassContributorDetails(fullyQualifiedClassName); 
	}

	private String[] getClassContributorDetails(String fullyQualifiedClassName) {
		return dbOpenJDKContributors.findRecordBy(FULLY_QUALIFIED_CLASS_NAME, fullyQualifiedClassName);
	}

	private String getFullyQualifiedClassName(String paramClassName)
			throws NotAFullyQualifiedClassNameException {
		return new FullyQualifiedClassName(paramClassName).getFullyQualifiedClassName();
	}

	
	@Then("the utility program should look into the database containing list of contributors and return the contributor $classcontributor and their details, which will contain their email address, etc...")
	public void searchOwner()
	{
		assertThat(classContributor.toString(), is(not(equalTo(""))));
	}
}