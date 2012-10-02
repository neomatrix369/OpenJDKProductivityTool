package org.ljc.adoptojdk.PerformActionWithCommandLineArgs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.PerformActionWithCommandLineArgs.PerformActionWithCommandLineArguments;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParseAllCommandLineArguments;

public class PerformActionWithCommandLineArgsBehaviour {
	@Test
	public void shouldShowExecuteClassOwnerSwitchWhenValidClassNameIsPassedIn() throws NotAFullyQualifiedClassNameException {
		String commandLineArgsString = getSampleCommandLineWithNoErrorsArg();
		String[] commandLineArgs = commandLineArgsString.split(" ");
		ParseAllCommandLineArguments parseAllCommandLineArguments = new ParseAllCommandLineArguments(commandLineArgs);		
		PerformActionWithCommandLineArguments executeArguments = new PerformActionWithCommandLineArguments(parseAllCommandLineArguments);
		
		assertTrue(executeArguments.getResult().equals(
			"[19/09/2012 22:36:50, ben, ppppqqqrrr@gmail.com, java.awt.Button, serial, packaged, /javac/warnings/core/java/awt/, Button.java.patch]"));
	}

	private String getSampleCommandLineWithNoErrorsArg() {
		return "-cojava.awt.Button";
	}
}