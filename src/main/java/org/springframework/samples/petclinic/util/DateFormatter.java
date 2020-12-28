package org.springframework.samples.petclinic.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

public class DateFormatter {
	
	private static final Logger log = LoggerFactory.logger(DateFormatter.class);

	public static LocalDate StringToLocalDate(String source) {
		LocalDate result;
		try {
			String[] parts = source.split(" ");
			String date = parts[2]+"-"+parts[1]+"-"+parts[3];
			log.info("Date: "+date);
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd-MMM-yyyy").toFormatter(Locale.ENGLISH);
			result = LocalDate.parse(date, formatter);
		}catch(Exception e) {
			result = null;
		}
		return result;
	}
	
}
