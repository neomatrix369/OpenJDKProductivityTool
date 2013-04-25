/* 

  The GNU General Public License (GPL)
 
  Version 1, November 2012

  Copyright (C) 1989, 1991 Free Software Foundation, Inc.
  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Everyone is permitted to copy and distribute verbatim copies of this license
  document, but changing it is not allowed.
  
  Copyright (c) 2012, Mani Sarkar <sadhak001@gmail.com> All rights reserved.
  
  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 
  This code is free software; you can redistribute it and/or modify it
  under the terms of the GNU General Public License version 2 only, as
  published by the Free Software Foundation.  Oracle designates this
  particular file as subject to the "Classpath" exception as provided
  by Oracle in the LICENSE file that accompanied this code.
 
  This code is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
  version 2 for more details (a copy is included in the LICENSE file that
  accompanied this code).
 
  You should have received a copy of the GNU General Public License version
  2 along with this work; if not, write to the Free Software Foundation,
  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 
 */

package org.ljc.adoptojdk.ClassContributorRetriever;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.ljc.adoptojdk.className.FullyQualifiedClassName;
import org.ljc.adoptojdk.className.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors;
import static org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors.*;

public class ClassContributorRetrieverSteps extends Steps
{
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private Object classContributor;
 
	@Given("We have details of a list of contributors $classcontributors who have submitted patches to OpenJDK sorted by date of submission")
	public void getListOfOpenJDKContributors(List<String> classContributors)
	{
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");
	}
	
	@When("I run openJDKProdTool for class name $classname")
	public void parameterPassed(String paramClassName) throws NotAFullyQualifiedClassNameException
	{	
		String fullyQualifiedClassName = getFullyQualifiedClassName(paramClassName);
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