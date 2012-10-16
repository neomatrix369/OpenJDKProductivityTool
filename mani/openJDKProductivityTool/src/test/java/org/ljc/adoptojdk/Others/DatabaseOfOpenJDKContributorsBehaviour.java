package org.ljc.adoptojdk.Others;

import static org.junit.Assert.assertTrue;
import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class DatabaseOfOpenJDKContributorsBehaviour {

	@Test
	public void shouldGiveAccessToADatabaseWhenFileNameIsPassed() {
		DatabaseOfOpenJDKContributors dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");
		assertTrue(dbOpenJDKContributors.isOpen());
		assertTrue(dbOpenJDKContributors.recordCount() == 5);
		
		String[] recordFound = dbOpenJDKContributors.findRecordBy(CONTRIBUTOR_NAME, "jack");
		assertTrue(recordFound != null);
		assertTrue(recordFound[CONTRIBUTOR_NAME].trim().equals("jack"));
		System.out.println(Arrays.toString(recordFound));
	}
	
	@Test
	public void shouldSortByAscendingOrderWhenDataRecordsArePassed() {
		DatabaseOfOpenJDKContributors dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");

		// Sort by descending order of date of submission
		boolean ascendingOrder = false;
		dbOpenJDKContributors.sortDatabaseBy(DATE_TIME_STAMP_SUBMISSION, ascendingOrder);
		
		String[] record = null; 
		record = dbOpenJDKContributors.getRecord(0);
		assertTrue(record[CONTRIBUTOR_NAME].trim().equals("graham"));
		
		record = dbOpenJDKContributors.getRecord(1);
		assertTrue(record[CONTRIBUTOR_NAME].trim().equals("richard"));
		
		record = dbOpenJDKContributors.getRecord(2);
		assertTrue(record[CONTRIBUTOR_NAME].trim().equals("jack"));
		
		record = dbOpenJDKContributors.getRecord(3);
		assertTrue(record[CONTRIBUTOR_NAME].trim().equals("ben"));
		
		record = dbOpenJDKContributors.getRecord(4);
		assertTrue(record[CONTRIBUTOR_NAME].trim().equals("martijn"));
	}
}