package org.ljc.adoptojdk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDatabaseOfOpenJDKContributorsTest.class,
		TestFluencyTest.class, TestFullyQualifiedClassNameTest.class,
		TestNotAFullyQualifiedClassNameExceptionTest.class })
public class AllTests {

}
