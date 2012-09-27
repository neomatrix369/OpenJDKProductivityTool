package org.ljc.adoptojdk.ReturnClassOwner;

import org.junit.Test;
import org.ljc.adoptojdk.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.FullyQualifiedClassName;
import static org.ljc.adoptojdk.Fluency.*;
import org.ljc.adoptojdk.NotAFullyQualifiedClassNameException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.ljc.adoptojdk.DatabaseOfOpenJDKContributors.*;

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
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();

		// When
		// user passes class name (fully qualified or unqualified)
		ReturnClassOwner returnClassOwner = new ReturnClassOwner(JAVA_AWT_EVENT_ACTION_CLASSNAME);
		classOwnerName = returnClassOwner.getClassOwnerName();
		
		// Then
		// check if result returned is not null
		assertFalse(ifThe(classOwnerName == null));
		// check if result returned is not empty
		assertFalse(ifThe(classOwnerName.equals("")));
	}
	
	@Test
	public void shouldReturnClassOwnerDetailsWhenIsClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();

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
	}	
}