package net.blerg.oracleToMysql;

import java.util.HashMap;
import java.util.Map;

import net.blerg.oracleToMysql.util.Match;
import net.blerg.oracleToMysql.util.MatchAll;

public class PatternBean {

	private String pattern;
	private String replacement;
	private Map<Character, Match> matches = new HashMap<>();

	public PatternBean(String pattern, String replacement, Map<Character, Match> matches) {
		this.pattern = pattern;
		this.replacement = replacement;
		this.matches = matches;
	}

	public PatternBean(String pattern, String replacement, char matchChar, Match match) {
		Map<Character, Match> matches = new HashMap<>();
		matches.put('*', new MatchAll());
		matches.put(matchChar, match);
		this.pattern = pattern;
		this.replacement = replacement;
		this.matches = matches;
	}
	
	public PatternBean(String pattern, String replacement) {
		Map<Character, Match> matches = new HashMap<>();
		matches.put('*', new MatchAll());
		this.pattern = pattern;
		this.replacement = replacement;
		this.matches = matches;
	}

	public String getPattern() {
		return pattern;
	}

	public String getReplacement() {
		return replacement;
	}

	public Map<Character, Match> getMatches() {
		return matches;
	}

}
