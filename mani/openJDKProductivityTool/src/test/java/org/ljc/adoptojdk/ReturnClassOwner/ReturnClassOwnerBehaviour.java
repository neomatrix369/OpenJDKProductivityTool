package org.ljc.adoptojdk.ReturnClassOwner;

import java.util.Arrays;

import org.junit.Test;

import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;
import static org.ljc.adoptojdk.Others.Fluency.*;

import org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.Others.FullyQualifiedClassName;
import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ReturnClassOwnerBehaviour {
	
	private static final String JAVA_AWT_EVENT_ACTION_CLASSNAME = "java.awt.event.Action";
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String fullyQualifiedClassName;
	private String classOwnerName;
	private String[] classOwnerDetails;

	@Test
	public void shouldReturnClassOwnerNameWhenClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ReturnClassOwner returnClassOwner = new ReturnClassOwner(JAVA_AWT_EVENT_ACTION_CLASSNAME);
		classOwnerName = returnClassOwner.getClassOwnerName();
		
		// Then
		// check if result returned is not null
		assertFalse(ifThe(classOwnerName == null));
		// check if result returned is not empty
		assertFalse(ifThe(classOwnerName.equals("")));
		// check if it returns name of the owner of the java.awt.event.Action class name 
		assertTrue(ifThe(classOwnerName.equals("jack")));
	}
	
	@Test
	public void shouldReturnClassOwnerDetailsWhenIsClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ReturnClassOwner returnClassOwner = new ReturnClassOwner(JAVA_AWT_EVENT_ACTION_CLASSNAME);
		classOwnerDetails = returnClassOwner.getClassOwnerDetails();
		
		// Then
		// check if result returned is not null
		assertFalse(ifThe(classOwnerDetails == null));
		// check if result returned has 1 or more elements
		assertTrue(ifThe(classOwnerDetails.length > 0));
		// check if result returned is not empty
		assertFalse(ifThe(classOwnerDetails.equals("")));
		// check if it returns details of the owner of the java.awt.event.Action class name
		//System.out.println(Arrays.toString(classOwnerDetails));
		assertThat(classOwnerDetails, is(
				new String[] {"20/09/2012 22:36:50", "jack", "xxxxyyyzz@gmail.com", 
						"java.awt.event.Action", "fallthro", "pending review", 
						"/javac/warnings/core/java/awt/event/", "Action.java.patch"}));		
	}	
}