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

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.ljc.adoptojdk.ClassContributorRetriever.ContributorRetriever.*;
import static org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser.*;
import static org.ljc.adoptojdk.CommandLineArgsParser.IndividualArgumentParser.*;

import org.ljc.adoptojdk.CommandLineArgsParser.ArgumentsParsedResult;
import org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser;
import org.ljc.adoptojdk.CommandLineArgsParser.IndividualArgumentParser;

public class CommandLineArgsParserBehaviour {
	
	@Test
	public void shouldShowNoArgsErrorMsgIfNoArgsArePassedToProgram() {
		String commandLineArgs = noCommandLineArguments();
		IndividualArgumentParser parseCommandLineArgs = new IndividualArgumentParser(commandLineArgs);
		ArgumentsParsedResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertThat(outputAfterParsingArgs.hasError(), is(true));		
		assertThat(outputAfterParsingArgs.getErrorMessage(), is(equalTo(getNoArgumentsPassedMessage())));
	}

	@Test 
	public void shouldShowInvalidArgsErrorMsgIfInvalidArgsArePassedToProgram() {
		String commandLineArgs = invalidCommandLineArguments();
		IndividualArgumentParser parseCommandLineArgs = new IndividualArgumentParser(commandLineArgs);
		ArgumentsParsedResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertThat(outputAfterParsingArgs.hasError(), is(true));
		assertThat(outputAfterParsingArgs.getErrorMessage(), is(equalTo(
				getInvalidArgsPassedMessage(commandLineArgs))));
	}
	
	@Test 
	public void shouldReturnClassNamePassedWithClassContributorSwitchWithoutASpace() {
		String commandLineArgs = classNameWithClassContributorSwitchWithoutASpace();
		IndividualArgumentParser parseCommandLineArgs = new IndividualArgumentParser(commandLineArgs);
		ArgumentsParsedResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertThat(outputAfterParsingArgs.hasError(), is(false));
		assertThat(outputAfterParsingArgs.getResultString(), is(equalTo(getClassNamePassed().trim())));
	}
	
	@Test @Ignore
	public void shouldReturnClassNamePassedWithClassContributorSwitchWithASpace() {
		String commandLineArgs = classNameWithClassContributorSwitchWithASpace();
		IndividualArgumentParser parseCommandLineArgs = new IndividualArgumentParser(commandLineArgs);
		ArgumentsParsedResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();

		assertThat(outputAfterParsingArgs.hasError(), is(true));
		assertThat(outputAfterParsingArgs.getResultString(), is(equalTo(getClassNamePassed())));
	}	
	
	
	private String classNameWithClassContributorSwitchWithoutASpace() {
		return CLASS_OWNER_SWITCH + getClassNamePassed();
	}
	
	private String classNameWithClassContributorSwitchWithASpace() {
		return CLASS_OWNER_SWITCH + " " + getClassNamePassed();
	}

	private String getClassNamePassed() {
		return "java.awt.event.ActionEvent";
	}

	@Test
	public void shouldShowIncompleteClassContributorErrorMsgIfIncompleteArgsPassedToProgram() {
		String commandLineArgs = getIncompleteClassContributorArgs();
		IndividualArgumentParser parseCommandLineArgs = new IndividualArgumentParser(commandLineArgs);
		ArgumentsParsedResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertThat(outputAfterParsingArgs.hasError(), is(true));
		assertThat(outputAfterParsingArgs.getErrorMessage(), is(equalTo(
				getIncompleteClassContributorArgsMessage(commandLineArgs))));
	}

	
	@Test
	public void shouldParseAllCommandArgumentsWhenFiveArgsArePassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithFiveArgs();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		CommandLineArgumentsParser parseAllCommandLineArguments = new CommandLineArgumentsParser(commandLineArgs);
		
		assertThat(parseAllCommandLineArguments.getArgsCount(), is(equalTo(5)));
		ArgumentsParsedResult firstParsedArg = parseAllCommandLineArguments.getFirstArgument();
		assertThat(firstParsedArg.hasError(), is(false));
		assertThat(firstParsedArg.getResultString(), is(equalTo("java.awt.event.ActionEvent")));
		assertThat(firstParsedArg.getCommandLineSwitch(), is(equalTo(CLASS_OWNER_SWITCH)));
		
		assertThat(parseAllCommandLineArguments.hasErrors(), is(true));
		assertThat(parseAllCommandLineArguments.finalErrorMessage, is(equalTo(getUsageText())));
	}
	
	@Test
	public void shouldParseAllCommandArgumentsWhenFourArgsArePassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithFourArgs();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		CommandLineArgumentsParser parseAllCommandLineArguments = new CommandLineArgumentsParser(commandLineArgs);
		
		assertThat(parseAllCommandLineArguments.getArgsCount(), is(equalTo(4)));
		ArgumentsParsedResult secondParsedArg = parseAllCommandLineArguments.getArgumentByIndex(1);
		assertThat(secondParsedArg.hasError(), is(true));
		assertThat(secondParsedArg.getCommandLineSwitch(), is(equalTo("")));
		assertThat(secondParsedArg.getResultString(), is(equalTo("")));
		
		// contains atleast one error
		assertThat(parseAllCommandLineArguments.hasErrors(), is(equalTo(true)));
		assertThat(parseAllCommandLineArguments.finalErrorMessage, is(equalTo(getUsageText())));
	}

	@Test
	public void shouldParseAllCommandArgumentsWhenNoErrorArgIsPassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithNoErrorsArg();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		CommandLineArgumentsParser parseAllCommandLineArguments = new CommandLineArgumentsParser(commandLineArgs);
		
		assertThat(parseAllCommandLineArguments.getArgsCount(), is(equalTo(1)));
		ArgumentsParsedResult firstParsedArg = parseAllCommandLineArguments.getFirstArgument();
		assertThat(firstParsedArg.hasError(), is(false));
		assertThat(firstParsedArg.getResultString(), is(equalTo("java.awt.event.ActionEvent")));
		assertThat(firstParsedArg.getCommandLineSwitch(), is(equalTo(CLASS_OWNER_SWITCH)));
		
		assertThat(parseAllCommandLineArguments.hasErrors(), is(false));
		assertThat(parseAllCommandLineArguments.finalErrorMessage, is(not(equalTo(getUsageText()))));
	}

	@Test
	public void shouldParseAllCommandArgumentsWhenNoErrorSimpleNameArgIsPassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithNoErrorsSimpleNameArg();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		CommandLineArgumentsParser parseAllCommandLineArguments = new CommandLineArgumentsParser(commandLineArgs);
		
		assertThat(parseAllCommandLineArguments.getArgsCount(), is(equalTo(1)));
		ArgumentsParsedResult firstParsedArg = parseAllCommandLineArguments.getFirstArgument();
		assertThat(firstParsedArg.hasError(), is(false));
		assertThat(firstParsedArg.getResultString(), is(equalTo("ActionEvent")));
		assertThat(firstParsedArg.getCommandLineSwitch(), is(equalTo(CLASS_OWNER_SWITCH)));
		
		assertThat(parseAllCommandLineArguments.hasErrors(), is(false));
		assertThat(parseAllCommandLineArguments.finalErrorMessage, is(not(equalTo(getUsageText()))));
	}
	
	private String getSampleCommandLineWithNoErrorsArg() {
		return "-cojava.awt.event.ActionEvent";
	}
	
	private String getSampleCommandLineWithNoErrorsSimpleNameArg() {
		return "-coActionEvent";
	}
	
	private String getSampleCommandLineWithFourArgs() {
		return "-cojava.awt.event.ActionEvent -arg2 -arg3 -arg4";
	}

	private String getSampleCommandLineWithFiveArgs() {
		return "-cojava.awt.event.ActionEvent -arg2 -arg3 -arg4 -arg5";
	}

	private String noCommandLineArguments() {
		return ""; // return nothing
	}	

	private String invalidCommandLineArguments() {
		return "jsdhfjkdshx"; // some invalid text
	}	

	private String getIncompleteClassContributorArgs() {
		return CLASS_OWNER_SWITCH;
	}
}