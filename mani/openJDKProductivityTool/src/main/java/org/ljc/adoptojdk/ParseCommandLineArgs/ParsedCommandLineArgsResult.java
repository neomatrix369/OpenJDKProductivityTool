package org.ljc.adoptojdk.ParseCommandLineArgs;

public class ParsedCommandLineArgsResult {
	private String commandLineSwitch = "";
	private String resultString = "";

	private boolean errorStatus = false;
	private String errorMessage = "";
	
	// Getter
	public boolean hasError() {
		return errorStatus;
	}
	
	public String getCommandLineSwitch() {
		return commandLineSwitch;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String getResultString() {
		return resultString;
	}
	
	// Setters
	public void setErrorMessage(String inErrorMessage) {
		resetWhenError();
		errorMessage = inErrorMessage;
	}

	public void setCommandLineSwitch(String inCommandLineSwitch) {
		commandLineSwitch = inCommandLineSwitch;
	}
	
	public void setResultString(String inResultString) {
		resetWhenNoError();		
		resultString = inResultString;
	}
	
	public void resetWhenNoError() {
		errorStatus = false;
		errorMessage = "";
		resultString = "";
	}
	
	public void resetWhenError() {
		errorStatus = true;
		resultString = "";
		commandLineSwitch = "";
	}

	public String toString() {
		
		String errorStatusWithLabel = "errorStatus: " + errorStatus;
		String errorMessageStringWithLabel = "errorMessage: " + errorMessage;
		String resultStringWithLabel = "resultString: " + resultString;
		String commandLineSwitchWithLabel = "commandLineSwitch: " + commandLineSwitch;
		
		if (errorStatus) {
			if (commandLineSwitch.equals("")) {
				return errorStatusWithLabel + ", " + errorMessageStringWithLabel;
			} else {
				return commandLineSwitchWithLabel + ", " + errorStatusWithLabel + ", " + errorMessageStringWithLabel;
			}
		} else {
			return commandLineSwitchWithLabel + ", " + resultStringWithLabel;
		}
	}
}