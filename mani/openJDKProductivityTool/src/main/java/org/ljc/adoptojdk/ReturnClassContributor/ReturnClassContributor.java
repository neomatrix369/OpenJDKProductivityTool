package org.ljc.adoptojdk.ReturnClassContributor;

import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.CONTRIBUTOR_NAME;
import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.FULLY_QUALIFIED_CLASS_NAME;

import org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors;
import org.ljc.adoptojdk.Others.FullyQualifiedClassName;
import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;

public class ReturnClassContributor {
	public static final String CLASS_OWNER_SWITCH = "-co";
	
	private String fullyQualifiedClassName; 
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String classContributorName;
	private String[] classContributorDetails;

	public ReturnClassContributor(String inClassName) throws NotAFullyQualifiedClassNameException {
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors();
		fullyQualifiedClassName = getFullyQualifiedClassName(inClassName);
		classContributorDetails = getClassContributorDetails();
		classContributorName = getClassContributorName();
	}

	public String[] getClassContributorDetails() {
		String[] dbRecord = {};
		dbRecord = dbOpenJDKContributors.findRecordBy(FULLY_QUALIFIED_CLASS_NAME, fullyQualifiedClassName);
		return dbRecord;
	}
	
	public String getClassContributorName() {
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