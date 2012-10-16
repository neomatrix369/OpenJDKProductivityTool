package org.ljc.adoptojdk.Others;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DatabaseOfOpenJDKContributors {
	private String[][] databaseOfContributors;
	public static int DATE_TIME_STAMP_SUBMISSION = 0;
	public static int CONTRIBUTOR_NAME = 1;
	public static int CONTRIBUTOR_EMAIL = 2;
	public static int FULLY_QUALIFIED_CLASS_NAME = 3;
	public static int WARNING_TYPE = 4;
	public static int SUBMISSION_STATUS = 5;
	public static int PATCH_LOCATION = 6;
	public static int PATCH_FILENAME = 7;
	
	public DatabaseOfOpenJDKContributors() {
		databaseOfContributors = accessDatabaseOfContributors("");
	}

	public DatabaseOfOpenJDKContributors(String dbFilename) {
		databaseOfContributors = accessDatabaseOfContributors(dbFilename);
	}

	private String[][] createDatabaseOfContributors() {
		// <Date & Time stamp>, <Contributor Name>, <Contributor Email>, <Full qualified Class Name>, 
		// <Warning Type>, <Status>, <Patch Location>, <Patch filename>
		String[][] database = {
				{"20/09/2012 22:36:50", "jack", "xxxxyyyzz@gmail.com", "java.awt.event.Action", "fallthro", "pending review", "/javac/warnings/core/java/awt/event/", "Action.java.patch"},				
				{"19/09/2012 22:36:50", "ben", "ppppqqqrrr@gmail.com", "java.awt.Button", "serial", "packaged", "/javac/warnings/core/java/awt/", "Button.java.patch"},
				{"18/09/2012 22:36:50", "martyn", "mmmnnooo@gmail.com", "java.lang.Class", "static", "accepted", "/javac/warnings/core/java/lang/", "Class.java.patch"},
				{"21/09/2012 22:36:50", "richard", "rrrssstt@gmail.com", "java.text.Annotation", "try", "reviewed", "/javac/warnings/core/java/text/", "Annotation.java.patch"},
				{"23/09/2012 22:36:50", "graham", "gghhhii@gmail.com", "java.util.zip.Checksum", "varargs", "rejected", "/javac/warnings/core/java/util/", "Checksum.java.patch"}
		};
		return database ;
	}

	private String[][] accessDatabaseOfContributors(String dbFilename) {
		String[][] database = null;
		
		database = loadDatabaseOfContributors(dbFilename);		
		if (database == null) { 
			database = createDatabaseOfContributors();
		}

		return database;
	}

	private String[][] loadDatabaseOfContributors(String dbFilename) {
		List<String> linesFromFile = new ArrayList<String>();
		String readLine = "";
		FileReader dbFR = null;
		BufferedReader dbBR = null;

		try {
			dbFR = new FileReader(dbFilename);			
			dbBR = new BufferedReader(dbFR);
			readLine = dbBR.readLine();
			
			while (readLine != null) {
				linesFromFile.add(readLine);
				readLine = dbBR.readLine();
			}

			return parseFileContent(linesFromFile);
		} catch (IOException ex) {
			return null;
		}
	}

	private String[][] parseFileContent(List<String> linesFromFile) {
		if (linesFromFile.size() > 0) {
			String[][] resultSet = new String[linesFromFile.size()][];
			int lineNumber = 0;
			for(String eachLine: linesFromFile) {
				resultSet[lineNumber] = eachLine.split(",");
				lineNumber++;
			}
			return resultSet;
		}
		return null;
	}

	public String[] findRecordBy(int columnIndex, String searchText) {
		String [] result = {};
		
		for (int row=0; row<databaseOfContributors.length; row++) {
			for (int col=0; col<databaseOfContributors[row].length; col++) {
				if (databaseOfContributors[row].length > columnIndex) {
					if (databaseOfContributors[row][columnIndex].trim().equals(searchText)) {
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

	public boolean isOpen() {
		return true;
	}

	public int recordCount() {
		return databaseOfContributors.length;
	}

	public String[] getRecord(int row) {
		String[] result = {};
		if (databaseOfContributors.length > row) {
			return databaseOfContributors[row];
		}
		return result;
	}

	public String[][] getRawDatabase() {
		return databaseOfContributors;
	}

	public void sortDatabaseBy(int inColumnIndex, boolean inAscendingOrder) {	
		Arrays.sort(databaseOfContributors, new ArrayComparator(inColumnIndex, inAscendingOrder));
	}

	class ArrayComparator implements Comparator<Comparable[]> {
	    private final int columnToSort;
	    private final boolean ascendingOrder;
	
	    public ArrayComparator(int columnToSort, boolean ascendingOrder) {
	        this.columnToSort = columnToSort;
	        this.ascendingOrder = ascendingOrder;
	    }
	
	    public int compare(Comparable[] entity1, Comparable[] entity2) {
	        int comparisonResult = entity1[columnToSort].compareTo(entity2[columnToSort]);
	        return ascendingOrder ? comparisonResult : -comparisonResult;
	    }
	}
}