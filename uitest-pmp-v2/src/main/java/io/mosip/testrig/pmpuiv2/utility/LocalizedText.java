package io.mosip.testrig.pmpuiv2.utility;

public final class LocalizedText {

	private LocalizedText() {
	}

	public static boolean matches(String actual, String... accepted) {
		if (actual == null || accepted == null) {
			return false;
		}
		String trimmed = actual.replaceAll("\\s+", " ").trim();
		for (String value : accepted) {
			if (value != null && trimmed.equals(value.replaceAll("\\s+", " ").trim())) {
				return true;
			}
		}
		return false;
	}
}
