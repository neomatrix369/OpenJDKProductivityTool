package org.ljc.adoptojdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ljc.adoptojdk.ParsedCommandLineArgsResult;
import org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner;
import static org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwner.*;
import org.ljc.adoptojdk.ReturnClassOwner.ReturnClassOwnerScenario;

public class MainExeClass {

	/**
	 * @param args
	 * @throws NotAFullyQualifiedClassNameException 
	 */
	public static void main(String[] args) throws NotAFullyQualifiedClassNameException {
		List<ParsedCommandLineArgsResult> listOfParsedCommandLineArgsResult = new ArrayList<ParsedCommandLineArgsResult>();
		
		for(String eachArg: args) {
			System.out.println(eachArg);
			ParseCommandLineArgs parseCommandLineArgs = new ParseCommandLineArgs(eachArg);
			ParsedCommandLineArgsResult argsResult = parseCommandLineArgs.getParseResults(); 
			listOfParsedCommandLineArgsResult.add(argsResult);
			System.out.println(argsResult);
			
			if (argsResult.getCommandLineSwitch().equals(CLASS_OWNER_SWITCH)) {
				ReturnClassOwner returnClassOwner = new ReturnClassOwner(argsResult.getResultString());
				System.out.println(returnClassOwner.getClassOwnerName());
				System.out.println(Arrays.toString(returnClassOwner.getClassOwnerDetails()));
			}
		}
	}
}
