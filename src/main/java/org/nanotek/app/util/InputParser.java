package org.nanotek.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * Simple class to parse the graph in the format proposed by the problem.
 * 
 */
public class InputParser implements Parser<String[]>{

	//
	@Override
	public String[] parse(String graphInput,String regex) {
		List<String> ar = new ArrayList<>();
		Matcher matcher =  Pattern.compile(regex).matcher(graphInput);
		int i=0;
		matcher.matches();
		while(matcher.find()) {
            i++;
            ar.add(matcher.group(1));
            System.out.println("found: " + i + " : "
                    + matcher.start() + " - " + matcher.end());
            System.out.println("found: " + matcher.group(1));
        }
		return ar.toArray(new String[ar.size()]);
	}
	
}
