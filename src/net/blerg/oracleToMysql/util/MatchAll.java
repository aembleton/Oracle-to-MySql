package net.blerg.oracleToMysql.util;

public class MatchAll implements Match {
	
	@Override
	public boolean matches(String string) {
		return true;
	}

}
