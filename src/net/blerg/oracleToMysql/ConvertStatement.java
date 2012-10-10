package net.blerg.oracleToMysql;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import net.blerg.oracleToMysql.util.PatternReplace;

/**
 * Class for carrying out the conversion statement by statement
 * @author Arthur Embleton
 *
 */
public class ConvertStatement {
	
	/**
	 * Converts a statement from Oracle to MySql
	 * @param statement The Oracle statement to convert
	 * @return The statement as Mysql
	 */
	public static String fromOracleToMySql(String statement) {
		Map<String,String> replacements=new HashMap<String, String>();
		replacements.put("CREATE USER * IDENTIFIED BY *;", "CREATE USER '*' IDENTIFIED BY '*';");
		replacements.put("GRANT CREATE ANY VIEW TO *;", "GRANT CREATE VIEW ON \\*.\\* TO *;");
		replacements.put("* varchar2(*", "* varchar(*");
		
		String mysqlStatement=statement;
		
		for (Entry<String, String> replacement:replacements.entrySet()) {
			
			try {
			mysqlStatement=PatternReplace.replace(mysqlStatement, replacement.getKey(), replacement.getValue());
			}catch (Exception e) {
				//continue to next line
			}
		}
		
		if (mysqlStatement.equals(statement)) {
			mysqlStatement="";
		}
		
		return mysqlStatement;
	}
}
