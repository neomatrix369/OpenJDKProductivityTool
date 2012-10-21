package org.ljc.adoptojdk.Others;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class DatabaseOfOpenJDKContributorsBehaviour {

	@Test
	public void shouldGiveAccessToADatabaseWhenFileNameIsPassed() {
		DatabaseOfOpenJDKContributors dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");
		assertThat(dbOpenJDKContributors.isOpen(), is(true));
		assertThat(dbOpenJDKContributors.recordCount(), is(5));
		
		String[] recordFound = dbOpenJDKContributors.findRecordBy(CONTRIBUTOR_NAME, "jack");
		assertNotNull(recordFound);
		assertThat(recordFound[CONTRIBUTOR_NAME].trim(), is(equalTo("jack")));
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
		assertThat(record[CONTRIBUTOR_NAME].trim(), is(equalTo("graham")));
		
		record = dbOpenJDKContributors.getRecord(1);
		assertThat(record[CONTRIBUTOR_NAME].trim(), is(equalTo("richard")));
		
		record = dbOpenJDKContributors.getRecord(2);
		assertThat(record[CONTRIBUTOR_NAME].trim(), is(equalTo("jack")));
		
		record = dbOpenJDKContributors.getRecord(3);
		assertThat(record[CONTRIBUTOR_NAME].trim(), is(equalTo("ben")));
		
		record = dbOpenJDKContributors.getRecord(4);
		assertThat(record[CONTRIBUTOR_NAME].trim(), is(equalTo("martijn")));
	}
}