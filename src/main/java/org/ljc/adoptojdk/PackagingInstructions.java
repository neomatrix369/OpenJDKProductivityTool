package org.ljc.adoptojdk;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

public class PackagingInstructions {

	private static String PACKAGING_PROPERTY = "patchfile.";
	private static final String PACKAGING_PROPERTIES_FILE = "packaging.props";

	public static ImmutableMap<String, String> PACKAGE_REGEX_TO_PATCH_NAME;

	static {

		Properties properties = new Properties();
		try {
			properties.load(PackagingInstructions.class
					.getResourceAsStream(PACKAGING_PROPERTIES_FILE));

		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> result = new HashMap<>();

		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (key.startsWith(PACKAGING_PROPERTY)) {
				String fileName = key.substring(PACKAGING_PROPERTY.length());
				String[] regexes = value.split(",");

				for (String packageRegex : regexes) {
					result.put(packageRegex, fileName);
				}
			}
		}

		PACKAGE_REGEX_TO_PATCH_NAME = ImmutableMap.copyOf(result);

	}

	public static Optional<String> getPatchName(final String className) {

		Collection<String> packageNames = Collections2.filter(
				PACKAGE_REGEX_TO_PATCH_NAME.keySet(), new Predicate<String>() {
					@Override
					public boolean apply(@Nullable String arg0) {
						return className.matches(arg0);
					}
				});

		String result = null;
		if (!packageNames.isEmpty()) {

			result = PACKAGE_REGEX_TO_PATCH_NAME.get(Iterables.get(packageNames, 0));

			if (packageNames.size() > 1) {
				System.err.println("WARNING, found multiple packages for class "+ className + " selecting: " + result);
			}
		}

		return Optional.fromNullable(result);

	}
}
