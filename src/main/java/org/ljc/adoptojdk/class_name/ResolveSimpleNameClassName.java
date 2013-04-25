package org.ljc.adoptojdk.class_name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResolveSimpleNameClassName {
	private Collection<String> packages = null;
	
	public Collection<String> getPackages() {
	    Set<String> returnPackages = new HashSet<String>();
	    for (Package aPackage : Package.getPackages()) {
	    	returnPackages.add(aPackage.getName());
	    }
	    return returnPackages;
	}
	
	public List<String> getFullyQualifiedNames(String simpleName) {
	    if (this.packages == null) {
	        this.packages = getPackages();
	    }

	    List<String> fqns = new ArrayList<String>();
	    for (String aPackage : packages) {
	        try {
	            String fqn = aPackage + "." + simpleName;
	            Class.forName(fqn);
	            fqns.add(fqn);
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	    return fqns;
	}

}
