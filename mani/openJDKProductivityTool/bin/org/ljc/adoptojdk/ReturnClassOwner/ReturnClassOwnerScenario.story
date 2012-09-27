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

Scenario: Utility that you could give a class name to and have it return an owner

Given We have details of a list of contributors who have submitted patches to OpenJDK 
When A developer runs the utility program in Linux/Unix and passes fully qualified class name as a parameter
Then the utility program should look into the database containing list of contributors and return their details, which will contain their email address, etc...