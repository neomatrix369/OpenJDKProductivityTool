package org.ljc.adoptojdk.ParseCommandLineArgs;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.ljc.adoptojdk.ParseCommandLineArgs.ParseCommandLineArgs.*;
import static org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner.*;

import org.ljc.adoptojdk.ParseCommandLineArgs.ParseCommandLineArgs;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParsedCommandLineArgsResult;

public class ParseCommandLineArgsBehaviour {
	
	@Test
	public void shouldShowUsageScreenIfNoArgsArePassedToProgram() {
		String commandLineArgs = noCommandLineArguments();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());		
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(
				ParseCommandLineArgs.getUsageText()));
	}

	@Test 
	public void shouldShowUsageScreenIfInvalidArgsArePassedToProgram() {
		String commandLineArgs = invalidCommandLineArguments();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(
				getInvalidArgsPassedMessage(commandLineArgs) +
				getUsageText()));
	}
	
	@Test 
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithoutASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithoutASpace();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertFalse(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getResultString().equals(getClassNamePassed()));
	}
	
	@Test @Ignore
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithASpace();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();

		assertFalse(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getResultString().equals(getClassNamePassed()));
	}	
	
	
	private String classNameWithClassOwnerSwitchWithoutASpace() {
		return CLASS_OWNER_SWITCH + getClassNamePassed();
	}
	
	private String classNameWithClassOwnerSwitchWithASpace() {
		return CLASS_OWNER_SWITCH + " " + getClassNamePassed();
	}

	private String getClassNamePassed() {
		return "java.awt.event.Action";
	}

	@Test
	public void shouldShowHelpOnClassOwnerArgIfIncompleteArgsPassedToProgram() {
		String commandLineArgs = getIncompleteClassOwnerArgs();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(
				getIncompleteClassOwnerArgsMessage(commandLineArgs) +
				                                 getUsageText()));
	}
	
	private String noCommandLineArguments() {
		return ""; // return nothing
	}	

	private String invalidCommandLineArguments() {
		return "jsdhfjkdshx"; // some invalid text
	}	

	private String getIncompleteClassOwnerArgs() {
		return CLASS_OWNER_SWITCH;
	}
}