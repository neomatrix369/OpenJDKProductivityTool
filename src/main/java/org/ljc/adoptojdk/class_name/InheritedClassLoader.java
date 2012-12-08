package org.ljc.adoptojdk.class_name;

public class InheritedClassLoader extends ClassLoader {
	// do nothing, just inherit, to avail protected methods
	public InheritedClassLoader() {
		super(InheritedClassLoader.class.getClassLoader());
	}
	
	public Class resolveLoadClass(String className) throws ClassNotFoundException {
		return loadClass(className, true);
	}
}
