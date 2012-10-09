package net.blerg.oracleToMysql;

import java.io.File;

/**
 * Converts a given file
 * @author aembleton
 *
 */
public class ConvertFile {

	private static final String MYSQL_EXTENSION=".mysql.sql";
	
	public static File fromOracleToMySql(File oracle) {
		File mysql = new File(oracle.getPath()+MYSQL_EXTENSION);
		
		return mysql;
	}
}
