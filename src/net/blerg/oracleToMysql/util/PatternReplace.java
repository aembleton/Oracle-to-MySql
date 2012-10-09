package net.blerg.oracleToMysql.util;

import java.util.LinkedList;
import java.util.List;

public class PatternReplace {

	private static enum STATE {normal,};
	
	public static String replace (String string, String pattern, String replacement, char wildcard, char escapeChar, boolean ignoreCase) {
	
		StringBuffer result = new StringBuffer();
		
		//tokenise against the pattern
		List<String> tokenisedPattern = tokenise(pattern, wildcard, escapeChar, true);
		List<String> tokenisedReplacement = tokenise(replacement,wildcard,escapeChar,true);
		
		
		
		return result.toString();
	}
	
	private static List<String> replacements(String string,List<String> pattern, char wildcard){
		List<String> replacements = new LinkedList<>();
		
		for (String )
		
		return replacements;
	}
	
	private static List<String> tokenise(String string, char splitOn, char escapeChar, boolean includeSplitCharAsToken){
		List<String> tokenised = new LinkedList<>();
		StringBuffer sb = new StringBuffer();
		boolean escape = false;
		
		for (char c:string.toCharArray()) {
			if (c==splitOn && !escape) {
				//split here
				if (sb.length()>0) {
					tokenised.add(sb.toString());
					sb=new StringBuffer();
				}
				if(includeSplitCharAsToken) {
					tokenised.add(String.valueOf(splitOn));
				}
			} else if(c==escapeChar){
				escape = true;
			} else {
				sb.append(c);
			}
		}
		
		if (sb.length()>0) {
			tokenised.add(sb.toString());
		}
		
		return tokenised;
	}
	
}
