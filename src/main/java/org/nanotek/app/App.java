package org.nanotek.app;

public class App {

	public static void main(String[] args) throws Exception{
		
		
		String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		System.out.println(input);
		appRunner(input).run();
//		String input = "AB4, BC4, AC9 , CD8, DC8, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3";
//		String input = "AB2, BD1, AC9 , CD8, DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3";
//		String input = "AA2, AB2, BD1, AC9, CD8, DC3, DE6, AD5, CE2, EB3, AE7, AF9, EF1, CF3, FB3, FD51";
//		String input = "AB2, AC1, AC5, BC1, CA3, AD3, CD2, BA4, DC1, CA2";
    }
	
	private static AppRunner appRunner(String input) {
		return new AppRunner(input);
	}

//	public static AppRunner appRunner() { 
//		return new AppRunner() ;
//	}
	
}
