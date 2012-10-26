package org.ljc.adoptojdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetriever;
import org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetrieverScenario;
import org.ljc.adoptojdk.ClassName.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.CommandLineArgsParser.ArgumentsParsedResult;
import org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser;
import org.ljc.adoptojdk.CommandLineArgsParser.IndividualArgumentParser;
import org.ljc.adoptojdk.PerformActionWithCommandLineArgs.PerformActionWithCommandLineArguments;

import static org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetriever.*;

public class OpenJDKPatchUtil {

	/**
	 * @param args
	 * @throws NotAFullyQualifiedClassNameException 
	 */
	public static void main(String[] args) throws NotAFullyQualifiedClassNameException {
		// parses all the command line arguments passed in
		CommandLineArgumentsParser parseAllArgs = new CommandLineArgumentsParser(args);
		
		// executes the command using the parsed arguments
		PerformActionWithCommandLineArguments executeArguments = new PerformActionWithCommandLineArguments(parseAllArgs);
		
		// returns result to console
		System.out.println(executeArguments.toString());
	}
}
