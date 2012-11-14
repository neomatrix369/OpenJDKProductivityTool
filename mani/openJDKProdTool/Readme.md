OpenJDK Productivity Tool
=========================

This github repo contains source and documentation files for the OpenJDK Productivity Tool - a handy program for OpenJDK hackers whilst contributing patches to the Adopt OpenJDK initiative.

Navigation
----------
    src/main - contains java classes performing the logic for the various OpenJDK Productivity Tool.
	src/test - contains JUnit & JBehave tests most of the java classes. Units capturing behaviours of the various classes are 
	also captured here.
	
	example-scripts - folder containing .sh or .cmd files that help illustrate how to use the program for different purposes.
	
	feedback - folder containing general feedback, review feedback in the form of text files.
	
	stories to-do - folder containing stories and scenario files.
		OpenJDKProductivityTool-original-requirements.txt - one such file containing the requirements behind this program.
		
    ./dbOfContributors.txt - text file containing comma separate data, list of contributors.
    ./dbOfContributorsTest.txt - text file containing comma separate data, list of contributors. 
    ./pom.xml - ant build script to build the project, contains the necessary dependencies and distribution details.
    ./Readme.md - this file.
    ./to-do-list - list of items that are ongoing tasks as the program is developed.
    ./Usage.txt - text file containing the standard usage texts on available options.

Jbehave
-------
Source and document files used to create the Jbehave aspects can be found in the following folders:

	src/main/java/org/ljc/adoptojdk/ClassContributorRetriever
	stories to-do/*.*
	
Look for files with suffices 'Step', 'Scenario', '.story', etc.

Eclipse environment (Indigo or Juno)
------------------------------------
The below plug-ins / frameworks are installed into the Eclipse framework:

     * EclEmma (optional)
     * Infinitest (optional)
     * EGit
     * JBehave Code Generator (optional)
     * Maven for Eclipse (m2e)
	
Note: most of the above can be installed via the Help > Install New Software menu option in Eclipse, others will need to be manually installed dropping the .jar files into the plugins folder.

JDK/JRE required (Eclipse environment): minimum 1.6
Project type: Maven project

Libraries & frameworks
----------------------
The below libraries / frameworks are included in the project and some of them are needed for successful compilation. The pom.xml file helps to resolve these dependencies.

     * JUnit 4 (required)
     * JBehave (required)
     * Mokito
     * Hamcrest
     * Log4J
     * SLF4J

Note: some of the above can be installed via the Help > Install New Software menu option in Eclipse or by dropping the .jar files into the plugins folder. 
JBehave is required to wire the BDD stories with Java classes & methods will need to be manually installed. 

Resources
---------
- http://www.github.com
- http://www.eclipse.org
- http://www.jbehave.org
- http://docs.oracle.com/javase/6/docs/api/