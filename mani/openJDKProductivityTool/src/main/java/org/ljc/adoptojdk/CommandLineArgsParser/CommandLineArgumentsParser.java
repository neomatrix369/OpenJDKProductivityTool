package org.ljc.adoptojdk.CommandLineArgsParser;

import java.util.ArrayList;
import java.util.List;

public class CommandLineArgumentsParser {
	List<ArgumentsParsedResult> resultSet = new ArrayList<ArgumentsParsedResult>();
	boolean containsAtLeastOneError = false;
	String finalErrorMessage = "";
	
	public CommandLineArgumentsParser(String[] commandLineArgs) {
		for (String eachArg: commandLineArgs) {
			IndividualArgumentParser individualArgumentParser = new IndividualArgumentParser(eachArg);
			ArgumentsParsedResult parsedResult = individualArgumentParser.getParseResults();
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

	public ArgumentsParsedResult getFirstArgument() {
		return getArgumentByIndex(0);
	}

	public ArgumentsParsedResult getArgumentByIndex(int i) {
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