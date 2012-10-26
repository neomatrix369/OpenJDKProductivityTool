package org.ljc.adoptojdk.PerformActionWithCommandLineArgs;

import java.util.Arrays;

import javax.rmi.CORBA.ClassDesc;

import org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetriever;
import org.ljc.adoptojdk.ClassName.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.CommandLineArgsParser.ArgumentsParsedResult;
import org.ljc.adoptojdk.CommandLineArgsParser.CommandLineArgumentsParser;
import org.ljc.adoptojdk.CommandLineArgsParser.IndividualArgumentParser;

import static org.ljc.adoptojdk.ClassContributorRetriever.ClassContributorRetriever.*;

public class PerformActionWithCommandLineArguments {

	private String executionResult = "";

	public PerformActionWithCommandLineArguments(CommandLineArgumentsParser commandLineArguments) 
			throws NotAFullyQualifiedClassNameException {
		if (commandLineArguments.hasErrors()) {
			executionResult = "";
			for(int i=0; i < commandLineArguments.getArgsCount();i++) {
				ArgumentsParsedResult argsParsedResult = commandLineArguments.getArgumentByIndex(i);
				executionResult = executionResult + argsParsedResult.getErrorMessage();
			}
			executionResult = executionResult + commandLineArguments.getFinalErrorMessage();
		}
		else {
			ArgumentsParsedResult argsParsedResult = commandLineArguments.getArgumentByIndex(0);
			
			if (argsParsedResult.getCommandLineSwitch().equals(CLASS_OWNER_SWITCH)) {
	            ClassContributorRetriever returnClassContributor = new ClassContributorRetriever(argsParsedResult.getResultString());
	            executionResult = Arrays.toString(returnClassContributor.getClassContributorDetails());
			}
		}
	}

	public String getResult() {
		return executionResult;
	}
	
	public String toString() {
		return getResult();
	}
}
