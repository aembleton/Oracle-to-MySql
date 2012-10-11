package net.blerg.oracleToMysql;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Multimap;

import net.blerg.oracleToMysql.util.MatchNumber;
import net.blerg.oracleToMysql.util.PatternReplace;

/**
 * Class for carrying out the conversion statement by statement
 * 
 * @author Arthur Embleton
 * 
 */
public class ConvertStatement {

	/**
	 * Converts a statement from Oracle to MySql
	 * 
	 * @param statement
	 *            The Oracle statement to convert
	 * @return The statement as Mysql
	 */
	public static String fromOracleToMySql(String statement) {
		Set<PatternBean> replacements = new HashSet<>();
		replacements.add(new PatternBean("CREATE USER * IDENTIFIED BY *;", "CREATE USER '*' IDENTIFIED BY '*';"));
		replacements.add(new PatternBean("GRANT CREATE ANY VIEW TO *;", "GRANT CREATE VIEW ON \\*.\\* TO *;"));
		replacements.add(new PatternBean("* varchar2(*", "* varchar(*"));
		replacements.add(new PatternBean("* number(?,0)*", "* INT(?)*", '?', new MatchNumber(7, 21)));
		replacements.add(new PatternBean("* clob*", "* LONGTEXT*"));

		String mysqlStatement = statement;

		for (PatternBean replacement : replacements) {

			try {
				mysqlStatement = PatternReplace.replace(mysqlStatement, replacement.getPattern(), replacement.getReplacement(), replacement.getMatches());
			} catch (Exception e) {
				// continue to next line
			}
		}

		if (mysqlStatement.equals(statement)) {
			mysqlStatement = "";
		}

		return mysqlStatement;
	}
}
