package net.blerg.oracleToMysql.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import net.blerg.oracleToMysql.ConvertFile;

import org.junit.Test;

public class CreateUsersTest {

	private static final String ORACLE_TEST = "testResources"+File.separator+"createUsers.sql";
	
	@Test
	public void normal() {
		File oracle = new File(ORACLE_TEST);
		File mysql =ConvertFile.fromOracleToMySql(oracle);

		assertNotNull(mysql);
	}

}
