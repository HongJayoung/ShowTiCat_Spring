package com.ShowTiCat.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DateUtil {
	public static Date convertToDate(String strdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d;
		Date d2 = null;
		try {
			d = sdf.parse(strdate);
			d2 = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d2;
	}
	public static Date convertToDateTime(String strdate) {
		//strdate = 2022-06-23T00:26
		
		int index = strdate.indexOf("T");
		String dstr  = strdate.substring(0,index);
		String tstr = strdate.substring(index+1,16);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		java.util.Date d;
		Date d2 = null;
		try {
			d = sdf.parse(dstr + " " + tstr);
			d2 = new Date(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d2;
	}
}
