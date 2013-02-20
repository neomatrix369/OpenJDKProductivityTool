/* 

  The GNU General Public License (GPL)
 
  Version 1, November 2012

  Copyright (C) 1989, 1991 Free Software Foundation, Inc.
  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Everyone is permitted to copy and distribute verbatim copies of this license
  document, but changing it is not allowed.
  
  Copyright (c) 2012, Mani Sarkar <sadhak001@gmail.com> All rights reserved.
  
  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 
  This code is free software; you can redistribute it and/or modify it
  under the terms of the GNU General Public License version 2 only, as
  published by the Free Software Foundation.  Oracle designates this
  particular file as subject to the "Classpath" exception as provided
  by Oracle in the LICENSE file that accompanied this code.
 
  This code is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
  version 2 for more details (a copy is included in the LICENSE file that
  accompanied this code).
 
  You should have received a copy of the GNU General Public License version
  2 along with this work; if not, write to the Free Software Foundation,
  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 
 */

package org.ljc.adoptojdk.database;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors.*;
import java.util.Arrays;
import org.junit.Test;
import org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors;

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