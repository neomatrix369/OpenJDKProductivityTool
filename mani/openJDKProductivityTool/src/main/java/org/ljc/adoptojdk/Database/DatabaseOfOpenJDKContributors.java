package org.ljc.adoptojdk.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.ljc.adoptojdk.Naratives.Fluency.*;

public class DatabaseOfOpenJDKContributors {
	private String[][] databaseOfContributors; // data stored in an array object 
	//TODO: convert to ArrayList<String> instead 
	
	public static int DATE_TIME_STAMP_SUBMISSION = 0;
	public static int CONTRIBUTOR_NAME = 1;
	public static int CONTRIBUTOR_EMAIL = 2;
	public static int FULLY_QUALIFIED_CLASS_NAME = 3;
	public static int WARNING_TYPE = 4;
	public static int SUBMISSION_STATUS = 5;
	public static int PATCH_LOCATION = 6;
	public static int PATCH_FILENAME = 7;
	
	public DatabaseOfOpenJDKContributors(String dbFilename) {
		databaseOfContributors = accessDatabaseOfContributors(dbFilename);
	}

	private String[][] createDatabaseOfContributors() {
		// <Date & Time stamp>, <Contributor Name>, <Contributor Email>, <Full qualified Class Name>, 
		// <Warning Type>, <Status>, <Patch Location>, <Patch filename>
		String[][] database = { {"", "", "", "", "", "", "", ""} };
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
		// guarded if statements - Sandro suggests using guarded if-s!
		if (dbFilename.trim().isEmpty()) return null;

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
			System.out.println("Error reading the database: " +  dbFilename);
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