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

import java.util.Arrays;

import org.hamcrest.Matcher;
import org.junit.Test;

import static org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors.*;
//import static org.ljc.adoptojdk.Others.Fluency.*;

import org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetriever;
import org.ljc.adoptojdk.class_name.FullyQualifiedClassName;
import org.ljc.adoptojdk.class_name.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClassContributorRetrieverBehaviour {
	
	private static final String JAVA_AWT_EVENT_ACTIONEVENT_CLASSNAME = "java.awt.event.ActionEvent";
	private static final String SIMPLE_NAME_ACTIONEVENT_CLASSNAME = "ActionEvent";
	private DatabaseOfOpenJDKContributors dbOpenJDKContributors;
	private String fullyQualifiedClassName;
	private String classContributorName;
	private String[] classContributorDetails;

	@Test
	public void shouldReturnClassContributorNameWhenClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ClassContributorRetriever returnClassContributor = new ClassContributorRetriever(JAVA_AWT_EVENT_ACTIONEVENT_CLASSNAME);
		classContributorName = returnClassContributor.getClassContributorName();
		
		// Then
		// check if result returned is not null
		assertNotNull(classContributorName);
		// check if result returned is not empty
		assertThat(classContributorName.trim(), is(not(equalTo(""))));
		// check if it returns name of the owner of the java.awt.event.ActionEvent class name 
		assertThat(classContributorName.trim(), is(equalTo("jack")));
	}
	
	@Test
	public void shouldReturnClassContributorNameWhenSimpleNameClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ClassContributorRetriever returnClassContributor = new ClassContributorRetriever(SIMPLE_NAME_ACTIONEVENT_CLASSNAME);
		classContributorName = returnClassContributor.getClassContributorName();
		
		// Then
		// check if result returned is not null
		assertNotNull(classContributorName);
		// check if result returned is not empty
		assertThat(classContributorName.trim(), is(not(equalTo(""))));
		// check if it returns name of the owner of the java.awt.event.ActionEvent class name 
		assertThat(classContributorName.trim(), is(equalTo("jack")));
	}

	@Test
	public void shouldReturnClassContributorDetailsWhenIsClassNamePassedIn() throws NotAFullyQualifiedClassNameException {
		// Given
		// we have access to a list of contributors
		dbOpenJDKContributors = new DatabaseOfOpenJDKContributors("./dbOfContributorsTest.txt");

		// When
		// user passes class name (fully qualified or unqualified)
		ClassContributorRetriever returnClassContributor = new ClassContributorRetriever(JAVA_AWT_EVENT_ACTIONEVENT_CLASSNAME);
		classContributorDetails = returnClassContributor.getClassContributorDetails();
		
		// Then
		// check if result returned is not null
		assertNotNull(classContributorDetails);
		// check if result returned has 1 or more elements
		assertThat(classContributorDetails.length, is(greaterThan(0)));
		// check if result returned is not empty
		assertThat(classContributorDetails.toString(), is(not(equalTo(""))));
		// check if it returns details of the owner of the java.awt.event.ActionEvent class name
		//System.out.println(Arrays.toString(classContributorDetails));
		assertThat(classContributorDetails, is(
				new String[] {"20/09/2012 22:36:50", " jack", " xxxxyyyzz@gmail.com", 
						" java.awt.event.ActionEvent", " fallthro", " pending review", 
						" /javac/warnings/core/java/awt/event/", " ActionEvent.java.patch\t\t\t"}));		
	}
	
	
}