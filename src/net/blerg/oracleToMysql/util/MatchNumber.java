package net.blerg.oracleToMysql.util;

public class MatchNumber implements Match {

	private int min;
	private int max;
	
	public MatchNumber(int min, int max) {
		this.min=min;
		this.max=max;
	}
	
	@Override
	public boolean matches(String string) {
		int number=0;
		
		try {
			number = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			//this is not a number
			return false;
		}
		
		return number >= min && number <= max;
	}

}
