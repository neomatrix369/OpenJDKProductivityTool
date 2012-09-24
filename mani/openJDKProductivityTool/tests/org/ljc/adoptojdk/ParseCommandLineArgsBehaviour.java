package org.ljc.adoptojdk;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.ljc.adoptojdk.ParseCommandLineArgs.*;
import org.ljc.adoptojdk.ParseCommandLineArgs;

public class ParseCommandLineArgsBehaviour {
	
	@Test
	public void shouldShowUsageScreenIfNoArgsArePassedToProgram() {
		String commandLineArgs = noCommandLineArguments();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		String outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.equals(ParseCommandLineArgs.getUsageText()));
	}

	@Test 
	public void shouldShowUsageScreenIfInvalidArgsArePassedToProgram() {
		String commandLineArgs = invalidCommandLineArguments();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		String outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.equals(
				getInvalidArgsPassedMessage(commandLineArgs) +
				getUsageText()));
	}
	
	@Test 
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithoutASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithoutASpace();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		String outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.equals(getClassNamePassed()));
	}
	
	@Test 
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithASpace();
		ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(commandLineArgs);
		String outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.equals(getClassNamePassed()));
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
		String outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.equals(getIncompleteClassOwnerArgsMessage(commandLineArgs) +
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