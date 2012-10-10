package net.blerg.oracleToMysql;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import net.blerg.oracleToMysql.util.PatternReplace;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Converts a given file
 * 
 * @author Arthur Embleton
 * 
 */
public class ConvertFile {

	private static final String MYSQL_EXTENSION = ".mysql.sql";
	private static final Charset CHARSET = Charsets.UTF_8;
	private static final String NEW_LINE = "\r\n";

	public static File fromOracleToMySql(File oracle) throws IOException {
		File mysql = new File(oracle.getPath() + MYSQL_EXTENSION);

		List<String> lines = Files.readLines(oracle, CHARSET);
		List<String> mysqlLines = new LinkedList<>();
		StringBuffer statement = new StringBuffer();

		for (String line : lines) {
			if (line.startsWith("--")) {
				// this is a comment
				mysqlLines.add(PatternReplace.replace(line, "--*", "-- *"));
			} else {
				line = line.trim();
				statement.append(line);
				if (line.endsWith(";")) {
					mysqlLines.add(ConvertStatement.fromOracleToMySql(statement.toString()));
					statement = new StringBuffer();
				}
			}
		}

		// write the lines into a file
		writeLinesToFile(mysqlLines, mysql);

		return mysql;
	}

	private static void writeLinesToFile(List<String> lines, File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		boolean previousLineWasEmpty = false;

		for (String line : lines) {
			if (!previousLineWasEmpty || !line.trim().equals("")) {
				sb.append(NEW_LINE).append(line);
			}

			previousLineWasEmpty = line.trim().equals("");
		}
		if (sb.length() > 0) {
			sb.substring(1);// drop the new line char
		}

		Files.write(sb.toString().getBytes(), file);
	}
}
