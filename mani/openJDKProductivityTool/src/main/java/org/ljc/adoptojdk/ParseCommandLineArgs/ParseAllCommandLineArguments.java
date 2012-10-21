package org.ljc.adoptojdk.ParseCommandLineArgs;

import java.util.ArrayList;
import java.util.List;

public class ParseAllCommandLineArguments {
	List<ParsedCommandLineArgsResult> resultSet = new ArrayList<ParsedCommandLineArgsResult>();
	boolean containsAtLeastOneError = false;
	String finalErrorMessage = "";
	
	public ParseAllCommandLineArguments(String[] commandLineArgs) {
		for (String eachArg: commandLineArgs) {
			ParseEachCommandLineArgument parseCommandLineArgs = new ParseEachCommandLineArgument(eachArg);
			ParsedCommandLineArgsResult parsedResult = parseCommandLineArgs.getParseResults();
			resultSet.add(parsedResult);
			containsAtLeastOneError = containsAtLeastOneError || parsedResult.hasError();
		}
		
		if (containsAtLeastOneError) {
			finalErrorMessage = getUsageText();
		}
	}

	public int getArgsCount() {
		return resultSet.size();
	}

	public ParsedCommandLineArgsResult getFirstArgument() {
		return getArgumentByIndex(0);
	}

	public ParsedCommandLineArgsResult getArgumentByIndex(int i) {
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
		return "Usage text";
	}	
}