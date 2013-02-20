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

package org.ljc.adoptojdk.CommandLineArgsParser;

import static org.ljc.adoptojdk.ClassContributorRetriever.ContributorRetriever.*;

public class IndividualArgumentParser {
	
	private ArgumentsParsedResult parsedResult = new ArgumentsParsedResult();
	private String commandLineArgs = "";
	
	public IndividualArgumentParser(String commandLineArgs) {
		this.commandLineArgs = commandLineArgs.trim();		
		doParseCommandLineArgs(this.commandLineArgs);
	}

	private void doParseCommandLineArgs(String commandLineArgs) {
		if (commandLineArgs.equals("")) {
			parsedResult.setErrorMessage(getNoArgumentsPassedMessage());
		}
		else {
			// process arguments and display message
			if (argumentsAreValid(commandLineArgs)) {
				
				// CLASS OWNER SWITCH: -co
				if (commandLineArgs.toLowerCase().startsWith(CLASS_OWNER_SWITCH)) {
					String classNames;
					parsedResult.setCommandLineSwitch(CLASS_OWNER_SWITCH);
					try	{
						int startIndex = CLASS_OWNER_SWITCH.length();
						int endIndex = commandLineArgs.length();
						classNames = commandLineArgs.substring(startIndex, endIndex);
						classNames = classNames.trim();
					}
					catch (Exception e) {
						classNames = "";
						parsedResult.setErrorMessage("Error message: " + e.getMessage());
						System.err.println(parsedResult);
					}
					if ((classNames == null) || (classNames.equals(""))) {
						parsedResult.setErrorMessage(getIncompleteClassContributorArgsMessage(commandLineArgs));
					} else {
						parsedResult.setResultString(classNames);
					}
				} else {
					// do nothing yet
				}
			} else {
				parsedResult.setErrorMessage(getInvalidArgsPassedMessage(commandLineArgs));
			}
		}
	}
		
	public static String getNoArgumentsPassedMessage() {
		return "No arguments passed.";
	}

	private boolean argumentsAreValid(String commandLineArgs) {
		commandLineArgs = commandLineArgs.trim();
		return commandLineArgs.toLowerCase().startsWith(CLASS_OWNER_SWITCH);
	}

	public String getCommandLineArguments() {
		return commandLineArgs;
	}
	
	public ArgumentsParsedResult getParseResults() {
		return parsedResult;
	}

	public static String getInvalidArgsPassedMessage(String inCommandLineArgs) {
		return "Invalid arguments passed: [" + inCommandLineArgs + "]. Please see usage text.\n\n";
	}
	
	public static String getIncompleteClassContributorArgsMessage(String inCommandLineArgs) {
		return "Incomplete Class Owner's arguments were passed: [" + inCommandLineArgs + "]. Class name(s) missing. Please see usage text.\n\n";
	}
}
