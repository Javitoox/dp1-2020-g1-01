package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

	public static LocalDate StringToLocalDate(String source) {
		String[] parts = source.split("%");
		String date = parts[2]+parts[1]+parts[3];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		return LocalDate.parse(date, formatter);
	}
	
}
