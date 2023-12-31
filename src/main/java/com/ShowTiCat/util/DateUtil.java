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
	
	public static Date getSysdate() {
		java.util.Date d = new java.util.Date();
		String sdf = new SimpleDateFormat("yyyy-MM-dd").format(d);
		Date date = convertToDate(sdf);
		
		return date;
	}
	
	public static Date dayAfter(Date date) {
		Calendar cal = Calendar.getInstance();
		java.util.Date d = new java.util.Date();
		
		cal.setTime(date);
		cal.add(Calendar.DATE, +1);

		d = cal.getTime();
		String sdf = new SimpleDateFormat("yyyy-MM-dd").format(d);
		Date date2 = convertToDate(sdf);

		return date2;
	}
	
	public static List<Date> getWeekDate() {
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = convertToDate(sdf.format(d)); 
		
		List<Date> dateList = new ArrayList<>();
		dateList.add(today);
		
		Calendar cal = Calendar.getInstance();
		
		for(int i=0;i<6;i++) {
			cal.setTime(d);
			cal.add(Calendar.DATE, +1);

			d = cal.getTime();
			dateList.add(convertToDate(sdf.format(d)));
		}
		
		return dateList;
	}
}
