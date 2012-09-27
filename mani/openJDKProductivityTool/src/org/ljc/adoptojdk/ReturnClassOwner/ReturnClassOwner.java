package org.ljc.adoptojdk.ReturnClassOwner;

import static org.ljc.adoptojdk.DatabaseOfOpenJDKContributors.CONTRIBUTOR_NAME;
import static org.ljc.adoptojdk.DatabaseOfOpenJDKContributors.FULLY_QUALIFIED_CLASS_NAME;

import org.ljc.adoptojdk.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.FullyQualifiedClassName;
import org.ljc.adoptojdk.NotAFullyQualifiedClassNameException;

public class ReturnClassOwner {
	public static final String CLASS_OWNER_SWITCH = "-co";
	
	private String fullyQualifiedClassName; 
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String classContributorName;
	private String[] classContributorDetails;

	public ReturnClassOwner(String inClassName) throws NotAFullyQualifiedClassNameException {
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();
		fullyQualifiedClassName = getFullyQualifiedClassName(inClassName);
		classContributorDetails = getClassOwnerDetails();
		classContributorName = getClassOwnerName();
	}

	public String[] getClassOwnerDetails() {
		String[] dbRecord = {};
		dbRecord = dbOpenJDKContributors.findRecordBy(FULLY_QUALIFIED_CLASS_NAME, fullyQualifiedClassName);
		return dbRecord;
	}
	
	public String getClassOwnerName() {
		String ownerName = "";
		if ((classContributorDetails != null) && (classContributorDetails.length > 0)) { 
			ownerName = classContributorDetails[CONTRIBUTOR_NAME];
		}
		return ownerName;
	}
	
	public String getFullyQualifiedClassName(String paramClassName) throws NotAFullyQualifiedClassNameException {
		return new FullyQualifiedClassName(paramClassName).getFullyQualifiedClassName();
	}
}