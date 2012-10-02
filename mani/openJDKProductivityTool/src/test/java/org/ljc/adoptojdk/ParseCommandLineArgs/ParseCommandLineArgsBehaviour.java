package org.ljc.adoptojdk.ParseCommandLineArgs;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.ljc.adoptojdk.ParseCommandLineArgs.ParseEachCommandLineArgument.*;
import static org.ljc.adoptojdk.ParseCommandLineArgs.ParseAllCommandLineArguments.*;
import static org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner.*;

import org.ljc.adoptojdk.ParseCommandLineArgs.ParseEachCommandLineArgument;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParsedCommandLineArgsResult;

public class ParseCommandLineArgsBehaviour {
	
	@Test
	public void shouldShowNoArgsErrorMsgIfNoArgsArePassedToProgram() {
		String commandLineArgs = noCommandLineArguments();
		ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());		
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(getNoArgumentsPassedMessage()));
	}

	@Test 
	public void shouldShowInvalidArgsErrorMsgIfInvalidArgsArePassedToProgram() {
		String commandLineArgs = invalidCommandLineArguments();
		ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(
				getInvalidArgsPassedMessage(commandLineArgs)));
	}
	
	@Test 
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithoutASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithoutASpace();
		ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertFalse(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getResultString().equals(getClassNamePassed()));
	}
	
	@Test @Ignore
	public void shouldReturnClassNamePassedWithClassOwnerSwitchWithASpace() {
		String commandLineArgs = classNameWithClassOwnerSwitchWithASpace();
		ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(commandLineArgs);
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
	public void shouldShowIncompleteClassOwnerErrorMsgIfIncompleteArgsPassedToProgram() {
		String commandLineArgs = getIncompleteClassOwnerArgs();
		ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(commandLineArgs);
		ParsedCommandLineArgsResult outputAfterParsingArgs = parseCommandLineArgs.getParseResults();
		
		assertTrue(outputAfterParsingArgs.getErrorStatus());
		assertTrue(outputAfterParsingArgs.getErrorMessage().equals(
				getIncompleteClassOwnerArgsMessage(commandLineArgs)));
	}

	
	@Test
	public void shouldParseAllCommandArgumentsWhenFiveArgsArePassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithFiveArgs();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		ParseAllCommandLineArguments parseAllCommandLineArguments = new ParseAllCommandLineArguments(commandLineArgs);
		
		assertTrue(parseAllCommandLineArguments.getArgsCount() == 5);
		ParsedCommandLineArgsResult firstParsedArg = parseAllCommandLineArguments.getFirstArgument();
		assertFalse(firstParsedArg.getErrorStatus());
		assertTrue(firstParsedArg.getResultString().equals("java.awt.event.Action"));
		assertTrue(firstParsedArg.getCommandLineSwitch().equals(CLASS_OWNER_SWITCH));
		
		assertTrue(parseAllCommandLineArguments.hasErrors());
		assertTrue(parseAllCommandLineArguments.finalErrorMessage.equals(getUsageText()));
	}
	
	@Test
	public void shouldParseAllCommandArgumentsWhenFourArgsArePassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithFourArgs();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		ParseAllCommandLineArguments parseAllCommandLineArguments = new ParseAllCommandLineArguments(commandLineArgs);
		
		assertTrue(parseAllCommandLineArguments.getArgsCount() == 4);
		ParsedCommandLineArgsResult secondParsedArg = parseAllCommandLineArguments.getArgumentByIndex(1);
		assertTrue(secondParsedArg.getErrorStatus());
		assertTrue(secondParsedArg.getCommandLineSwitch().equals(""));
		assertTrue(secondParsedArg.getResultString().equals(""));
		
		// contains atleast one error
		assertTrue(parseAllCommandLineArguments.hasErrors());
		assertTrue(parseAllCommandLineArguments.finalErrorMessage.equals(getUsageText()));
	}

	@Test
	public void shouldParseAllCommandArgumentsWhenOneNoErrorArgIsPassedToIt() {
		String commandLineArgsString = getSampleCommandLineWithNoErrorsArg();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		ParseAllCommandLineArguments parseAllCommandLineArguments = new ParseAllCommandLineArguments(commandLineArgs);
		
		assertTrue(parseAllCommandLineArguments.getArgsCount() == 1);
		ParsedCommandLineArgsResult firstParsedArg = parseAllCommandLineArguments.getFirstArgument();
		assertFalse(firstParsedArg.getErrorStatus());
		assertTrue(firstParsedArg.getResultString().equals("java.awt.event.Action"));
		assertTrue(firstParsedArg.getCommandLineSwitch().equals(CLASS_OWNER_SWITCH));
		
		assertFalse(parseAllCommandLineArguments.hasErrors());
		assertFalse(parseAllCommandLineArguments.finalErrorMessage.equals(getUsageText()));
	}

	private String getSampleCommandLineWithNoErrorsArg() {
		return "-cojava.awt.event.Action";
	}
	
	private String getSampleCommandLineWithFourArgs() {
		return "-cojava.awt.event.Action -arg2 -arg3 -arg4";
	}

	private String getSampleCommandLineWithFiveArgs() {
		return "-cojava.awt.event.Action -arg2 -arg3 -arg4 -arg5";
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