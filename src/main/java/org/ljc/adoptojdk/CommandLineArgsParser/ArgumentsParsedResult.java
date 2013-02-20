/* 

  The GNU General Public License (GPL)
 
  Version 1, November 2012

  Copyright (C) 1989, 1991 Free Software Foundation, Inc.
  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Everyone is permitted to copy and distribute verbatim copies of this license
  document, but changing it is not allowed.
  
  Copyright (c) 2012, Mani Sarkar <sadhak001@gmail.com> All rights reserved.
  
  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 
  This code is free software; you can redistribute it and/or modify it
  under the terms of the GNU General Public License version 2 only, as
  published by the Free Software Foundation.  Oracle designates this
  particular file as subject to the "Classpath" exception as provided
  by Oracle in the LICENSE file that accompanied this code.
 
  This code is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
  version 2 for more details (a copy is included in the LICENSE file that
  accompanied this code).
 
  You should have received a copy of the GNU General Public License version
  2 along with this work; if not, write to the Free Software Foundation,
  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 
 */

package org.ljc.adoptojdk.CommandLineArgsParser;

public class ArgumentsParsedResult {
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