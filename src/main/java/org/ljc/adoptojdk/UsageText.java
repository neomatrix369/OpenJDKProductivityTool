package org.ljc.adoptojdk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class UsageText {
	private static final String USAGE_TXT_FILENAME = "./Usage.txt";static final 
	Logger COMMON_LOGGER = Logger.getLogger(UsageText.class.getName());
	
	private UsageText() {
		//  Hide Utility Class Constructor	false	UsageText.java	Hide Utility Class Constructor : Utility classes should not have a public or default constructor
	}
	
	public static String getUsageText() {
		try {
			return readFileToString(USAGE_TXT_FILENAME);
		} catch (IOException ioe) {
			COMMON_LOGGER.log(Level.SEVERE, USAGE_TXT_FILENAME + " file could not be found.");
			return "";
		}
	}

	private static String readFileToString( String filename ) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader( filename ) );
		StringBuilder  stringBuilder = new StringBuilder();
		try {
			String         line   = null;			                      
			String         ls     = System.getProperty( "line.separator" );
			
			while ( ( line = reader.readLine() ) != null ) {
				stringBuilder.append( line );
				stringBuilder.append( ls );
			}
		}
		finally {
			reader.close();
		}
		return stringBuilder.toString();
	}
}