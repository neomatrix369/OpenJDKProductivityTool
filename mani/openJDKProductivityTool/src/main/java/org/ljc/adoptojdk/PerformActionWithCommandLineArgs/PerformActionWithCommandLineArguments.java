package org.ljc.adoptojdk.PerformActionWithCommandLineArgs;

import java.util.Arrays;

import javax.rmi.CORBA.ClassDesc;

import org.ljc.adoptojdk.Others.NotAFullyQualifiedClassNameException;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParseAllCommandLineArguments;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParseEachCommandLineArgument;
import org.ljc.adoptojdk.ParseCommandLineArgs.ParsedCommandLineArgsResult;
import org.ljc.adoptojdk.ReturnClassContributor.ReturnClassContributor;

import static org.ljc.adoptojdk.ReturnClassContributor.ReturnClassContributor.*;

public class PerformActionWithCommandLineArguments {

	private String executionResult = "";

	public PerformActionWithCommandLineArguments(ParseAllCommandLineArguments commandLineArguments) 
			throws NotAFullyQualifiedClassNameException {
		if (commandLineArguments.hasErrors()) {
			executionResult = "";
			for(int i=0; i < commandLineArguments.getArgsCount();i++) {
				ParsedCommandLineArgsResult parsedArg = commandLineArguments.getArgumentByIndex(i);
				executionResult = executionResult + parsedArg.getErrorMessage();
			}
			executionResult = executionResult + commandLineArguments.getFinalErrorMessage();
		}
		else {
			ParsedCommandLineArgsResult commandLineArgsResult = commandLineArguments.getArgumentByIndex(0);
			
			if (commandLineArgsResult.getCommandLineSwitch().equals(CLASS_OWNER_SWITCH)) {
	            ReturnClassContributor returnClassContributor = new ReturnClassContributor(commandLineArgsResult.getResultString());
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
