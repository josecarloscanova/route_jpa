package org.nanotek.app;

public class App {

	public static void main(String[] args) throws Exception{
		appRunner().run();
    }
	
	public static AppRunner appRunner() { 
		return new AppRunner() ;
	}
	
}
