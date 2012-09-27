package org.ljc.adoptojdk;

public class DatabaseOfOpenJDKContributors {
	private String[][] databaseOfContributors;
	public static int DATE_TIME_STAMP = 0;
	public static int CONTRIBUTOR_NAME = 1;
	public static int CONTRIBUTOR_EMAIL = 2;
	public static int FULLY_QUALIFIED_CLASS_NAME = 3;
	public static int WARNING_TYPE = 4;
	public static int SUBMISSION_STATUS = 5;
	public static int PATCH_LOCATION = 6;
	public static int PATCH_FILENAME = 7;
	
	public DatabaseOfOpenJDKContributors() {
		databaseOfContributors = accessDatabaseOfContributors();
	}

	private String[][] createDatabaseOfContributors() {
		// <Date & Time stamp>, <Contributor Name>, <Contributor Email>, <Full qualified Class Name>, 
		// <Warning Type>, <Status>, <Patch Location>, <Patch filename>
		String[][] database = {
				{"20/09/2012 22:36:50", "mani", "sadhak001@gmail.com", "java.awt.event.Action", "fallthro", "pending review", "/javac/warnings/core/java/awt/event/", "Action.java.patch"},
		};
		return database ;
	}

	private String[][] accessDatabaseOfContributors() {
		String[][] database = null;
		
		if (database == null) { 
			database = createDatabaseOfContributors();
		}

		return database;
	}

	public String[] findRecordBy(int columnIndex, String fullyQualifiedClassName) {
		String [] result = {};
		
		for (int row=0; row<databaseOfContributors.length; row++) {
			for (int col=0; col<databaseOfContributors[row].length; col++) {
				if (databaseOfContributors[row].length > columnIndex) {
					if (databaseOfContributors[row][columnIndex].equals(fullyQualifiedClassName)) {
						result = databaseOfContributors[row];
						break;
					}
				} else {
					break;
				}
			}
		}
		
		return result;
	}
}