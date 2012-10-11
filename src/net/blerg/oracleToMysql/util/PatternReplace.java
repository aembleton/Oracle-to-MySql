package net.blerg.oracleToMysql.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PatternReplace {

	public static String replace(String string, String pattern, String replacement) {
		Map<Character, Match> matches = new HashMap<>();
		matches.put('*', new MatchAll());
		return replace(string, pattern, replacement, matches);
	}

	public static String replace(String string, String pattern, String replacement, Map<Character, Match> matches) {
		return replace(string, pattern, replacement, matches, '\\', true, true);
	}

	public static String replace(String string, String pattern, String replacement, Map<Character, Match> matches, char escapeChar, boolean ignoreCase, boolean loop) {

		// tokenise against the pattern
		List<String> tokenisedPattern = tokenise(pattern, matches, escapeChar, true);
		List<String> tokenisedReplacement = tokenise(replacement, matches, escapeChar, true);

		List<String> replacements;
		try {
			replacements = replacements(string, tokenisedPattern, matches, ignoreCase, loop);
		} catch (MatchFailed e) {
			// return string without carrying out the replacements
			return string;
		}

		return replaceTokens(tokenisedReplacement, replacements, matches);
	}

	private static String replaceTokens(List<String> tokenisedReplacement, List<String> replacements, Map<Character, Match> matches) {
		StringBuffer result = new StringBuffer();
		int i = 0;

		for (String replacementPattern : tokenisedReplacement) {
			char c = replacementPattern.charAt(0);
			if (replacementPattern.length() == 1 && matches.containsKey(c) && replacements.size() > i) {
				result.append(replacements.get(i));
				i++;
			} else {
				result.append(replacementPattern);
			}
		}

		return result.toString();
	}

	private static List<String> replacements(String string, List<String> pattern, Map<Character, Match> matches, boolean ignoreCase, boolean loop) throws MatchFailed {
		
		List<String> result = replacements(string, pattern, matches, new LinkedList<String>(), ignoreCase);
		int previousResultSize=0;
		
		while(loop&&result.size()>previousResultSize&&result.size()>1) {
			result.addAll(replacements(result.get(result.size()-1), pattern, matches, new LinkedList<String>(), ignoreCase));
			previousResultSize=result.size();
		}
		
		return result;
	}

	private static List<String> replacements(String string, List<String> pattern, Map<Character, Match> wildcards, List<String> replacements, boolean ignoreCase) throws MatchFailed {

		if (pattern.size() > 0 && string.length() > 0) {
			String patternElement = pattern.get(0);
			char c = patternElement.charAt(0);
			if (patternElement.length() == 1 && wildcards.containsKey(c)) {
				//this is one of the wildcard chars
				String replacementString = "";
				if (pattern.size() > 1) {
					// there are more items remaining in the pattern, so need to take an indexof to the next match
					int index = string.indexOf(pattern.get(1));
					replacementString = string.substring(0, index);
					string = string.substring(index);
				} else {
					replacementString = string;
					string = "";
				}

				replacements.add(replacementString);

				if (!wildcards.get(c).matches(replacementString)) {
					// could not match
					throw new MatchFailed();
				}

			} else if ((ignoreCase && patternElement.equalsIgnoreCase(string.substring(0, patternElement.length()))) || patternElement.equals(string.substring(0, patternElement.length()))) {
				string = string.substring(patternElement.length());
			} else {
				throw new IllegalArgumentException();
			}

			pattern = pattern.subList(1, pattern.size());

			replacements = replacements(string, pattern, wildcards, replacements, ignoreCase);
		}

		return replacements;
	}

	private static List<String> tokenise(String string, Map<Character, Match> matches, char escapeChar, boolean includeSplitCharAsToken) {
		List<String> tokenised = new LinkedList<>();
		StringBuffer sb = new StringBuffer();
		boolean escape = false;

		for (char c : string.toCharArray()) {
			if (!escape && matches.containsKey(c)) {
				// split here
				if (sb.length() > 0) {
					tokenised.add(sb.toString());
					sb = new StringBuffer();
				}
				if (includeSplitCharAsToken) {
					tokenised.add(String.valueOf(c));
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
