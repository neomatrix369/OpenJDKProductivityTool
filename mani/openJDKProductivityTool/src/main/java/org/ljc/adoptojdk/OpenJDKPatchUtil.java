package org.ljc.adoptojdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParseAllCommandLineArguments;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParseEachCommandLineArgument;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParsedCommandLineArgsResult;
import org.ljc.adoptojdk.PerformActionWithCommandLineArgs.PerformActionWithCommandLineArguments;
import org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner;
import static org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner.*;
import org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwnerScenario;

public class OpenJDKPatchUtil {

	/**
	 * @param args
	 * @throws NotAFullyQualifiedClassNameException 
	 */
	public static void main(String[] args) throws NotAFullyQualifiedClassNameException {
		// parses all the command line arguments passed in
		ParseAllCommandLineArguments parseAllArgs = new ParseAllCommandLineArguments(args);
		
		// executes the command using the parsed arguments
		PerformActionWithCommandLineArguments executeArguments = new PerformActionWithCommandLineArguments(parseAllArgs);
		
		// returns result to console
		System.out.println(executeArguments.toString());
	}
}
