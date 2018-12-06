package org.nanotek.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/*
 * Simple class to parse the graph in the format proposed by the problem.
 * 
 */
public class InputParser implements Parser<String[]>{

	//
	@Override
	public String[] parse(String graphInput,String regex) throws ParseException{
		List<String> ar = new ArrayList<>();
		try  {
			Matcher matcher =  Pattern.compile(regex).matcher(graphInput);
			matcher.matches();
			while(matcher.find()) {
	            ar.add(matcher.group(1));
	        }
		}catch(PatternSyntaxException | IndexOutOfBoundsException pe ) { 
			throw new ParseException("Not possible execute operation");
		}
		return ar.toArray(new String[ar.size()]);
	}
	
}
