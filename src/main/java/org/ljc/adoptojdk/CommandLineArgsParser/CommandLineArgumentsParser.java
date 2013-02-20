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

import java.util.ArrayList;
import java.util.List;

import org.ljc.adoptojdk.UsageText;

public class CommandLineArgumentsParser {
	List<ArgumentsParsedResult> resultSet = new ArrayList<ArgumentsParsedResult>();
	boolean containsAtLeastOneError = false;
	String finalErrorMessage = "";
	
	public CommandLineArgumentsParser(String[] commandLineArgs) {
		
		if (commandLineArgs.length == 0) { // no arguments passed	
			ParseArguments(""); 		
		} else {
			for (String eachArg: commandLineArgs) {
				ParseArguments(eachArg);
			}
		}

		if (containsAtLeastOneError) {
			finalErrorMessage = getUsageText();
		}
	}
	
	private void ParseArguments(String eachArg) {
		IndividualArgumentParser individualArgumentParser = new IndividualArgumentParser(eachArg);
		ArgumentsParsedResult parsedResult = individualArgumentParser.getParseResults();
		resultSet.add(parsedResult);
		containsAtLeastOneError = containsAtLeastOneError || parsedResult.hasError();
	}
	

	public int getArgsCount() {
		return resultSet.size();
	}

	public ArgumentsParsedResult getFirstArgument() {
		return getArgumentByIndex(0);
	}

	public ArgumentsParsedResult getArgumentByIndex(int i) {
		if (resultSet.size() > i) {
			return resultSet.get(i);
		} else {
			return null;
		}
	}

	public boolean hasErrors() {
		return containsAtLeastOneError;
	}
	
	public String getFinalErrorMessage() {
		return finalErrorMessage;
	}
	
	public static String getUsageText() {
		return UsageText.getUsageText();
	}	
}