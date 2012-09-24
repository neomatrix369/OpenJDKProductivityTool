package org.ljc.adoptojdk.ReturnClassOwner;

import org.junit.Test;
import org.ljc.adoptojdk.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.FullyQualifiedClassName;
import static org.ljc.adoptojdk.Fluency.*;
import org.ljc.adoptojdk.NotAFullyQualifiedClassNameException;

import static org.junit.Assert.assertFalse;

import static org.ljc.adoptojdk.DatabaseOfOpenJDKContributors.*;

public class ReturnClassOwnerBehaviour {
	
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String fullyQualifiedClassName;
	private String classOwner;

	@Test
	public void shouldReturnClassOwnerWhenClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();

		// When
		// user passes class name (fully qualified or unqualified)
		fullyQualifiedClassName = getFullyQualifiedClassName("java.awt.event.Action");
		classOwner = getOwnerNameByClassName(fullyQualifiedClassName);
		
		// Then
		// check if result returned is not null
		assertFalse(ifThe(classOwner == null));
		// check if result returned is not empty
		assertFalse(ifThe(classOwner.equals("")));
	}

	private String getOwnerNameByClassName(String fullyQualifiedClassName) {
		String[] dbRecord = {};
		String ownerName = "";
		dbRecord = dbOpenJDKContributors.findRecordBy(FULLY_QUALIFIED_CLASS_NAME, fullyQualifiedClassName);
		if ((dbRecord != null) && (dbRecord.length > 0)) { 
			ownerName = dbRecord[CONTRIBUTOR_NAME];
		}
		return ownerName;
	}

	private String getFullyQualifiedClassName(String paramClassName) throws NotAFullyQualifiedClassNameException {
		return new FullyQualifiedClassName(paramClassName).getFullyQualifiedClassName();
	}
}