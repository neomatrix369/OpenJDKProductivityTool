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

package org.ljc.adoptojdk.PerformActionWithCommandLineArgs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser;
import org.ljc.adoptojdk.PerformActionWithCommandLineArgs.PerformActionWithCommandLineArguments;
import org.ljc.adoptojdk.class_name.NotAFullyQualifiedClassNameException;

public class PerformActionWithCommandLineArgsBehaviour {
	@Test
	public void shouldShowExecuteClassContributorSwitchWhenValidClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		String commandLineArgsString = getSampleCommandLineWithNoErrorsArg();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		CommandLineArgumentsParser parseAllCommandLineArguments = new CommandLineArgumentsParser(commandLineArgs);		
		PerformActionWithCommandLineArguments executeArguments = new PerformActionWithCommandLineArguments(parseAllCommandLineArguments);
		
		assertTrue(executeArguments.getResult().equals(
			"[19/09/2012 22:36:50,  ben,  ppppqqqrrr@gmail.com,  java.awt.Button,  serial,  packaged,  /javac/warnings/core/java/awt/,  Button.java.patch]"));
	}

	private String getSampleCommandLineWithNoErrorsArg() {
		return "-cojava.awt.Button";
	}
}