package net.blerg.oracleToMysql.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import net.blerg.oracleToMysql.ConvertFile;

import org.junit.Test;

public class CreateUsersTest {

	private static final String ORACLE_TEST = "testResource"+File.separator+"createUsers.sql";
	
	@Test
	public void normal() {
		File oracle = new File(ORACLE_TEST);
		File mysql;
		try {
			mysql = ConvertFile.fromOracleToMySql(oracle);
			assertNotNull(mysql);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

		
	}

}
