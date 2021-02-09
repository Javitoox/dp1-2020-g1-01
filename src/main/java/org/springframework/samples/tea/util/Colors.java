package org.springframework.samples.tea.util;

public class Colors {

	public static String generateRandomColor(){
		String[] letters = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		String color = "#";
		for (int i = 0; i < 6; i++ ) {
		    color += letters[(int) Math.round(Math.random() * 15)];
		}
		return color;
	}

}
