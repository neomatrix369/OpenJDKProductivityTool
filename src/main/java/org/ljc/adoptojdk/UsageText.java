package org.ljc.adoptojdk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UsageText {
	private static final String USAGE_TXT_FILENAME = "./Usage.txt";

	public static String getUsageText() {
		try {
			return readFileToString(USAGE_TXT_FILENAME);
		} catch (IOException ioe) {
			System.err.println(USAGE_TXT_FILENAME + " file could not be found.");
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