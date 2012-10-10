package net.blerg.oracleToMysql.test;

import static org.junit.Assert.assertEquals;
import net.blerg.oracleToMysql.util.PatternReplace;

import org.junit.Test;

public class PatternReplaceTest {

	@Test
	public void basicReplace() {
		assertEquals("Hello 'World'", PatternReplace.replace("Hello World", "Hello *", "Hello '*'", '*', '\\', true));
	}
	
	@Test
	public void usingEscape() {
		assertEquals("Hello * 'World'", PatternReplace.replace("Hello World", "Hello *", "Hello \\* '*'"));
	}
}
