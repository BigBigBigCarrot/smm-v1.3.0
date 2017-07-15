package com.david.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConvertor implements Converter<String, Date> {
	public StringToDateConvertor() {
	}

	public Date convert(String arg0) {
		if (StringUtils.isNotBlank(arg0)) {
			String format = "yyyy-MM-dd HH:mm:ss";
			if (arg0.length() == 10) {
				format = "yyyy-MM-dd";
			} else if (arg0.length() == 13) {
				format = "yyyy-MM-dd HH";
			} else if (arg0.length() == 16) {
				format = "yyyy-MM-dd HH:mm";
			} else {
				format = "yyyy-MM-dd HH:mm:ss";
			}
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(arg0);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}