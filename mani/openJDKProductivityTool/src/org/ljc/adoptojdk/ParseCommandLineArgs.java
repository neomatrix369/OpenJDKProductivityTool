package org.ljc.adoptojdk;

public class ParseCommandLineArgs {
	
	static final String CLASS_OWNER_SWITCH = "-co";
	private String parseResult = "";
	private String commandLineArgs = "";
	
	public ParseCommandLineArgs(String commandLineArgs) {
		this.commandLineArgs = commandLineArgs.trim();		
		doParseCommandLineArgs(this.commandLineArgs);
	}

	private void doParseCommandLineArgs(String commandLineArgs) {
		if (commandLineArgs.equals("")) {
			parseResult = performDisplayUsageScreen(getUsageText());
		}
		else {
			// process arguments and display message
			if (argumentsAreValid(commandLineArgs)) {
				if (commandLineArgs.toLowerCase().startsWith(CLASS_OWNER_SWITCH)) {
					String classNames;
					try	{
						int startIndex = CLASS_OWNER_SWITCH.length();
						int endIndex = commandLineArgs.length();
						classNames = commandLineArgs.substring(startIndex, endIndex);
						classNames = classNames.trim();
					}
					catch (Exception e) {
						classNames = "";
					}
					if ((classNames == null) || (classNames.equals(""))) {
						parseResult = getIncompleteClassOwnerArgsMessage(commandLineArgs) + 
				              getUsageText();
					} else {
						parseResult = classNames;
					}
				} else {
					// do nothing yet
				}
				
			} else {
				parseResult = getInvalidArgsPassedMessage(commandLineArgs) + 
			                  getUsageText();
			}
		}
	}
		
	private boolean argumentsAreValid(String commandLineArgs) {
		//TODO: more implementation will follow
		//commandLineArgs = commandLineArgs.trim();
		return commandLineArgs.toLowerCase().contains(CLASS_OWNER_SWITCH);
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
	
	public String getParseResults() {
		return parseResult;
	}

	static String getInvalidArgsPassedMessage(String inCommandLineArgs) {
		return "Invalid arguments passed: [" + inCommandLineArgs + "]. Please see usage text.\n\n";
	}
	
	static String getIncompleteClassOwnerArgsMessage(String inCommandLineArgs) {
		return "Incomplete Class Owner's arguments were passed: [" + inCommandLineArgs + "]. Class name(s) missing. Please see usage text.\n\n";
	}
}
