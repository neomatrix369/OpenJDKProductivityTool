package org.ljc.adoptojdk.ClassContributorRetriever;

import static org.ljc.adoptojdk.Database.DatabaseOfOpenJDKContributors.CONTRIBUTOR_NAME;
import static org.ljc.adoptojdk.Database.DatabaseOfOpenJDKContributors.FULLY_QUALIFIED_CLASS_NAME;

import org.ljc.adoptojdk.ClassName.FullyQualifiedClassName;
import org.ljc.adoptojdk.ClassName.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.Database.DatabaseOfOpenJDKContributors;

public class ClassContributorRetriever {
	public static final String CLASS_OWNER_SWITCH = "-co";
	
	private String fullyQualifiedClassName; 
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String classContributorName;
	private String[] classContributorDetails;

	public ClassContributorRetriever(String inClassName) throws NotAFullyQualifiedClassNameException {
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("dbOfContributorsTest.txt");
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