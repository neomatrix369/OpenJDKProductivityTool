package org.ljc.adoptojdk.ReturnClassContributor;

import java.util.Arrays;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;
//import static org.ljc.adoptojdk.Others.Fluency.*;

import org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.Others.FullyQualifiedClassName;
import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.ReturnClassContributor.ReturnClassContributor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ReturnClassContributorBehaviour {
	
	private static final String JAVA_AWT_EVENT_ACTION_CLASSNAME = "java.awt.event.Action";
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String fullyQualifiedClassName;
	private String classContributorName;
	private String[] classContributorDetails;

	@Test
	public void shouldReturnClassContributorNameWhenClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ReturnClassContributor returnClassContributor = new ReturnClassContributor(JAVA_AWT_EVENT_ACTION_CLASSNAME);
		classContributorName = returnClassContributor.getClassContributorName();
		
		// Then
		// check if result returned is not null
		assertNotNull(classContributorName);
		// check if result returned is not empty
		assertThat(classContributorName, is(not(equalTo(""))));
		// check if it returns name of the owner of the java.awt.event.Action class name 
		assertThat(classContributorName, is(equalTo("jack")));
	}
	

	@Test
	public void shouldReturnClassContributorDetailsWhenIsClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ReturnClassContributor returnClassContributor = new ReturnClassContributor(JAVA_AWT_EVENT_ACTION_CLASSNAME);
		classContributorDetails = returnClassContributor.getClassContributorDetails();
		
		// Then
		// check if result returned is not null
		assertNotNull(classContributorDetails);
		// check if result returned has 1 or more elements
		assertThat(classContributorDetails.length, is(greaterThan(0)));
		// check if result returned is not empty
		assertThat(classContributorDetails.toString(), is(not(equalTo(""))));
		// check if it returns details of the owner of the java.awt.event.Action class name
		//System.out.println(Arrays.toString(classContributorDetails));
		assertThat(classContributorDetails, is(
				new String[] {"20/09/2012 22:36:50", "jack", "xxxxyyyzz@gmail.com", 
						"java.awt.event.Action", "fallthro", "pending review", 
						"/javac/warnings/core/java/awt/event/", "Action.java.patch"}));		
	}	
}