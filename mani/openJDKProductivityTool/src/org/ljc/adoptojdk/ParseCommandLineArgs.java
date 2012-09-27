package org.ljc.adoptojdk;

import static org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner.*;

public class ParseCommandLineArgs {
	
	private ParsedCommandLineArgsResult parsedResult = new ParsedCommandLineArgsResult();
	private String commandLineArgs = "";
	
	public ParseCommandLineArgs(String commandLineArgs) {
		this.commandLineArgs = commandLineArgs.trim();		
		doParseCommandLineArgs(this.commandLineArgs);
	}

	private void doParseCommandLineArgs(String commandLineArgs) {
		if (commandLineArgs.equals("")) {
			parsedResult.setErrorMessage(performDisplayUsageScreen(getUsageText()));
		}
		else {
			// process arguments and display message
			if (argumentsAreValid(commandLineArgs)) {
				
				// CLASS OWNER SWITCH: -co
				if (commandLineArgs.toLowerCase().startsWith(CLASS_OWNER_SWITCH)) {
					String classNames;
					parsedResult.setCommandLineSwitch(CLASS_OWNER_SWITCH);
					try	{
						int startIndex = CLASS_OWNER_SWITCH.length();
						int endIndex = commandLineArgs.length();
						classNames = commandLineArgs.substring(startIndex, endIndex);
						classNames = classNames.trim();
					}
					catch (Exception e) {
						classNames = "";
						parsedResult.setErrorMessage("Error message: " + e.getMessage());
					}
					if ((classNames == null) || (classNames.equals(""))) {
						parsedResult.setErrorMessage(getIncompleteClassOwnerArgsMessage(commandLineArgs) + getUsageText());
					} else {
						parsedResult.setResultString(classNames);
					}
				} else {
					// do nothing yet
				}
			} else {
				parsedResult.setErrorMessage(getInvalidArgsPassedMessage(commandLineArgs) + getUsageText());
			}
		}
	}
		
	private boolean argumentsAreValid(String commandLineArgs) {
		commandLineArgs = commandLineArgs.trim();
		return commandLineArgs.toLowerCase().startsWith(CLASS_OWNER_SWITCH);
	}

	private String performDisplayUsageScreen(String inUsageText) {
		System.out.println(inUsageText);
		return inUsageText;
	}

	public String getCommandLineArguments() {
		return commandLineArgs;
	}

	static String getUsageText() {
		return "Usage text";
	}
	
	public ParsedCommandLineArgsResult getParseResults() {
		return parsedResult;
	}

	static String getInvalidArgsPassedMessage(String inCommandLineArgs) {
		return "Invalid arguments passed: [" + inCommandLineArgs + "]. Please see usage text.\n\n";
	}
	
	static String getIncompleteClassOwnerArgsMessage(String inCommandLineArgs) {
		return "Incomplete Class Owner's arguments were passed: [" + inCommandLineArgs + "]. Class name(s) missing. Please see usage text.\n\n";
	}
}
