package org.nanotek.app.test.graph;


import org.junit.Assert;
import org.junit.Test;
import org.nanotek.app.util.InputParser;

public class InputParserTest {

	Object[]expected={"AA2","AB2","BD1","AC9","CD8","DC3","DE6","AD5","CE2","EB3","AE7","AF9","EF1","CF3","FB3","FD51"};
	
	@Test
	public void testCompute() {
		String graphInput = "AA2, AB2, BD1, AC9, CD8, DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3, FB3, FD51";
		executeAssertion(graphInput);
	}

	@Test
	public void graphInputSpaced() {
		String graphInput = "AA2,  AB2, BD1,  AC9,  CD8,  DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3, FB3, FD51";
		executeAssertion(graphInput);
	}

	private void executeAssertion(String graphInput) {
		InputParser parser = new InputParser();
		Object[] result = parser.parse(graphInput,"(\\w+\\d+)");
		Assert.assertArrayEquals(expected, result);
	}
	
}
