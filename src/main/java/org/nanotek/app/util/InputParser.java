package org.nanotek.app.util;

import java.util.regex.Pattern;
/*
 * Simple class to parse the graph in the format proposed by the problem.
 * 
 */
public class InputParser implements Parser<String,String[]>{

	@Override
	public String[] parse(String graphInput) {
		return  Pattern.compile(",\\s*").split(graphInput);
	}

}
