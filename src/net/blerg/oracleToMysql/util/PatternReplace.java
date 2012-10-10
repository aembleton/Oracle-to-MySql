package net.blerg.oracleToMysql.util;

import java.util.LinkedList;
import java.util.List;

public class PatternReplace {

	public static String replace(String string, String pattern, String replacement) {
		return replace(string, pattern, replacement, '*', '\\', true);
	}

	public static String replace(String string, String pattern, String replacement, char wildcard, char escapeChar, boolean ignoreCase) {

		// tokenise against the pattern
		List<String> tokenisedPattern = tokenise(pattern, wildcard, escapeChar, true);
		List<String> tokenisedReplacement = tokenise(replacement, wildcard, escapeChar, true);

		List<String> replacements = replacements(string, tokenisedPattern, wildcard, ignoreCase);

		return replaceTokens(tokenisedReplacement, replacements, wildcard);
	}

	private static String replaceTokens(List<String> tokenisedReplacement, List<String> replacements, char wildcard) {
		StringBuffer result = new StringBuffer();
		int i = 0;

		for (String replacementPattern : tokenisedReplacement) {
			if (replacementPattern.equals(String.valueOf(wildcard)) && replacements.size() > i) {
				result.append(replacements.get(i));
				i++;
			} else {
				result.append(replacementPattern);
			}
		}

		return result.toString();
	}

	private static List<String> replacements(String string, List<String> pattern, char wildcard, boolean ignoreCase) {
		return replacements(string, pattern, wildcard, new LinkedList<String>(), ignoreCase);
	}

	private static List<String> replacements(String string, List<String> pattern, char wildcard, List<String> replacements, boolean ignoreCase) {

		if (pattern.size() > 0 && string.length() > 0) {
			String patternElement = pattern.get(0);
			if (patternElement.equals(String.valueOf(wildcard))) {
				if (pattern.size() > 1) {
					int index = string.indexOf(pattern.get(1));
					replacements.add(string.substring(0, index));
					string = string.substring(index);
				} else {
					replacements.add(string);
					string = "";
				}
			} else if ((ignoreCase && patternElement.equalsIgnoreCase(string.substring(0, patternElement.length()))) || patternElement.equals(string.substring(0, patternElement.length()))) {
				string = string.substring(patternElement.length());
			} else {
				throw new IllegalArgumentException();
			}

			pattern = pattern.subList(1, pattern.size());

			replacements = replacements(string, pattern, wildcard, replacements, ignoreCase);
		}

		return replacements;
	}

	private static List<String> tokenise(String string, char splitOn, char escapeChar, boolean includeSplitCharAsToken) {
		List<String> tokenised = new LinkedList<>();
		StringBuffer sb = new StringBuffer();
		boolean escape = false;

		for (char c : string.toCharArray()) {
			if (c == splitOn && !escape) {
				// split here
				if (sb.length() > 0) {
					tokenised.add(sb.toString());
					sb = new StringBuffer();
				}
				if (includeSplitCharAsToken) {
					tokenised.add(String.valueOf(splitOn));
				}
				escape = false;
			} else if (c == escapeChar) {
				if (escape) {
					// we've escaped the escape char so this should be added to the string buffer
					sb.append(c);
					escape = false;
				} else {
					escape = true;
				}
			} else {
				sb.append(c);
				escape = false;
			}
		}

		if (sb.length() > 0) {
			tokenised.add(sb.toString());
		}

		return tokenised;
	}

}
