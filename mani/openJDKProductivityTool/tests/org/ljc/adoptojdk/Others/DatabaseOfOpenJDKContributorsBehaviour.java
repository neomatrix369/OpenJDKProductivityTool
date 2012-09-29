package org.ljc.adoptojdk.Others;

import static org.junit.Assert.assertTrue;
import static org.ljc.adoptojdk.Others.DatabaseOfOpenJDKContributors.*;

import java.util.Arrays;

import org.junit.Test;

public class DatabaseOfOpenJDKContributorsBehaviour {

	@Test
	public void shouldGiveAccessToADatabaseWhenFileNameIsPassed() {
		DatabaseOfOpenJDKContributors dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributors.txt");
		assertTrue(dbOpenJDKContributors.isOpen());
		assertTrue(dbOpenJDKContributors.recordCount() == 5);
		
		String[] recordFound = dbOpenJDKContributors.findRecordBy(CONTRIBUTOR_NAME, "jack");
		assertTrue(recordFound != null);
		assertTrue(recordFound[CONTRIBUTOR_NAME].equals("jack"));
		System.out.println(Arrays.toString(recordFound));
	}
}