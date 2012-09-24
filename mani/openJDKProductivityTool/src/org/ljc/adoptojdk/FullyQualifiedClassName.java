package org.ljc.adoptojdk;

public class FullyQualifiedClassName {
	private String className;

	public FullyQualifiedClassName(String className) throws NotAFullyQualifiedClassNameException {
		Boolean isClassFullyQualified = checkIfClassIsFullyQualified(className);
		if (isClassFullyQualified) {
			this.className = className;
		} else {
			this.className = attemptToGetAFullyQualifiedName(className);
			if (fullyQualifiedNameNotFound(className, this.className)) {
				throw new NotAFullyQualifiedClassNameException("The parameter passed ("+ className 
						+ ") was not a fully qualified class name. A distinct class name could not be found.");
			}
		}
	}
	
	private boolean fullyQualifiedNameNotFound(String originalClassName, String parsedClassName) {
		return !originalClassName.equals(parsedClassName);
	}

	private String attemptToGetAFullyQualifiedName(String className) {
		// search for similar className (unqualified) from a list of fully qualified classes 
		return className; // search through a list of class names using the parameter
	}

	private Boolean checkIfClassIsFullyQualified(String className) {
		// check if the classname has dots(.) in it
		boolean foundAtLeastOneDotSeparator = className.indexOf(".") > 0;
		// and is a valid one from the list
		//TODO: to be implemented
		return foundAtLeastOneDotSeparator; 
	}

	public String getFullyQualifiedClassName() {
		return className;
	}	
}