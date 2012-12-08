Story: Utility that you could give a class name to and have it return an owner in some form, preferably one that 
is easily mapped to an email address. Not only would this be useful for what you are asking for here, it would 
also be useful for (semi-)automatically directing messages reporting test failures and so on

or

Generate an email with the email address along with other details of the class(es) that failed during testrig tests. Separate emails for separate 
classes. Email should contain errors and failure details, class names and other class related details. 
Log all activities. Reviewer feature.

Narrative:
In order to be able to inform the contributor of a build failure
As a reviewer
I want to retrieve the name and contact details of the submitter using a fully qualified class name


Background:
Given We have details of a list of contributors $classcontributors who have submitted patches to OpenJDK sorted by date of submission
and  
20/09/2012 22:36:50| jack   | xxxxyyyzz@gmail.com | java.awt.event.ActionEvent  | fallthro| pending review| /javac/warnings/core/java/awt/event/| ActionEvent.java.patch			
19/09/2012 22:36:50| ben    | ppppqqqrrr@gmail.com| java.awt.Button             | serial  | packaged      | /javac/warnings/core/java/awt/      | Button.java.patch
18/09/2012 22:36:50| martijn| mmmnnooo@gmail.com  | java.lang.Class             | static  | accepted      | /javac/warnings/core/java/lang/     | Class.java.patch
21/09/2012 22:36:50| richard| rrrssstt@gmail.com  | java.text.Annotation        | try     | reviewed      | /javac/warnings/core/java/text/     | Annotation.java.patch
23/09/2012 22:36:50| graham | gghhhii@gmail.com   | java.util.zip.Checksum      | varargs | rejected      | /javac/warnings/core/java/util/     | Checksum.java.patch

Scenario: Utility that you could give a class name to and have it return an owner
      
When I run openJDKProdTool for class name $classname
Then the utility program should look into the database containing list of contributors and return the contributor $classcontributor and their details, which will contain their email address, etc...