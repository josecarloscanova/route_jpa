package org.nanotek.app.collection;

/**
 * Objective - given an input (the graph on some format)
 * the program shall hold the input provide a method to access the input 
 * which shall be immutable.
 * 
 * @param input - the input provided by the main method.
 * (the argument provided by the program).
 * 
 * 
 */
public class InputHolder {
	
	private final String input;
	
	public InputHolder(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}

}
