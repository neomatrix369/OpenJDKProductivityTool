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

package org.ljc.adoptojdk.ClassContributorRetriever;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.ljc.adoptojdk.className.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors;

public class ContributionRetrieverBehaviour {
	
	private static final String DB_OF_CONTRIBUTORS = "./dbOfContributorsTest.txt";
	private static final String EXAMPLE_RESULT = "jack";
	private static final String EXAMPLE_CLASS_NAME = "java.awt.event.ActionEvent";
	private static final String EXAMPLE_SIMPLE_NAME = "ActionEvent";
	private String contributorName;
	private String[] contributorDetails;
	DatabaseOfOpenJDKContributors databaseOfOpenJDKContributors;
	
	@Before
	public void setupDatabase() {
		databaseOfOpenJDKContributors = new DatabaseOfOpenJDKContributors(DB_OF_CONTRIBUTORS);
	}

	@Test(expected=NullPointerException.class)
	public void shouldNeverProvideNullAsTheContributorName() throws NotAFullyQualifiedClassNameException {
		new ContributorRetriever(databaseOfOpenJDKContributors, null);
	}

	@Test
	public void shouldReturnContributorNameWhenClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// When
		// user passes class name (fully qualified or unqualified)
		ContributorRetriever returnContributor = new ContributorRetriever(databaseOfOpenJDKContributors, EXAMPLE_CLASS_NAME);
		contributorName = returnContributor.getContributorName();
		
		// Then
		// check if it returns name of the owner of the java.awt.event.ActionEvent class name 
		assertThat(contributorName.trim(), is(EXAMPLE_RESULT));
	}
	
	@Test
	public void shouldReturnContributorNameWhenASimpleNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// When
		// user passes class name (fully qualified or unqualified)
		ContributorRetriever returnContributor = new ContributorRetriever(databaseOfOpenJDKContributors, EXAMPLE_SIMPLE_NAME);
		contributorName = returnContributor.getContributorName();
		
		// Then
		// check if it returns name of the owner of the java.awt.event.ActionEvent class name 
		assertThat(contributorName.trim(), is(equalTo(EXAMPLE_RESULT)));
	}

	@Test
	public void shouldReturnContributorDetailsWhenIsClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// When
		// user passes class name (fully qualified or unqualified)
		ContributorRetriever returnContributor = new ContributorRetriever(databaseOfOpenJDKContributors, EXAMPLE_CLASS_NAME);
		contributorDetails = returnContributor.getContributorDetails();
		
		// Then
		// check if result returned is not null
		assertNotNull(contributorDetails);
		// check if result returned has 1 or more elements
		assertThat(contributorDetails.length, is(greaterThan(0)));
		// check if result returned is not empty
		assertThat(contributorDetails.toString(), is(not(equalTo(""))));
		// check if it returns details of the owner of the java.awt.event.ActionEvent class name
		//System.out.println(Arrays.toString(contributorDetails));
		assertThat(contributorDetails, is(
				new String[] {"20/09/2012 22:36:50", " jack", " xxxxyyyzz@gmail.com", 
						" java.awt.event.ActionEvent", " fallthro", " pending review", 
						" /javac/warnings/core/java/awt/event/", " ActionEvent.java.patch\t\t\t"}));		
	}
}