package org.ljc.adoptojdk.className;

public class InheritedClassLoader extends ClassLoader {
	// do nothing, just inherit, to avail protected methods
	public InheritedClassLoader() {
		super(InheritedClassLoader.class.getClassLoader());
	}
	
	@SuppressWarnings("rawtypes")
	public Class resolveLoadClass(String className) throws ClassNotFoundException {
		return loadClass(className, true);
	}
}
