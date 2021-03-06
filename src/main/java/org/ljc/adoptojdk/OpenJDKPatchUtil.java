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

package org.ljc.adoptojdk;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser;
import org.ljc.adoptojdk.PerformActionWithCommandLineArgs.PerformActionWithCommandLineArguments;
import org.ljc.adoptojdk.className.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.database.DatabaseOfOpenJDKContributors;

public final class OpenJDKPatchUtil {
	private static final String DB_OF_CONTRIBUTORS = "./dbOfContributorsTest.txt";
	private static final Logger COMMON_LOGGER = Logger.getLogger(OpenJDKPatchUtil.class.getName());
	
	private OpenJDKPatchUtil() {		
	}
	
	/**
	 * @param args
	 * @throws NotAFullyQualifiedClassNameException 
	 */
	public static void main(String[] args) throws NotAFullyQualifiedClassNameException {
		DatabaseOfOpenJDKContributors databaseOfOpenJDKContributors = new DatabaseOfOpenJDKContributors(DB_OF_CONTRIBUTORS); 
		
		// parses all the command line arguments passed in
		CommandLineArgumentsParser parseAllArgs = new CommandLineArgumentsParser(args);
		
		// executes the command using the parsed arguments
		PerformActionWithCommandLineArguments executeArguments = new PerformActionWithCommandLineArguments(databaseOfOpenJDKContributors, parseAllArgs);
		
		// returns result to console
		COMMON_LOGGER.log(Level.INFO, executeArguments.toString());		
	}
}
