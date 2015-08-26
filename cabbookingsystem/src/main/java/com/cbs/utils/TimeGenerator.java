/**
 * 
 */
package com.cbs.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cbs.constants.Constants;

/**
 * @author Nishant
 *
 */
public class TimeGenerator {
	
	public static Date getDate(String dateString) {
		
		Date date = null;
		DateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		try {
			
			date = sdf.parse(dateString);
						
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
		
	}
	
	
	public static Date getCurrentTime(){
		
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static int getTimeDifference(Date d1,Date d2){
		
		int timeDifferenceInMinutes = 0;
		timeDifferenceInMinutes =  (int) ((d1.getTime()- d2.getTime())/(1000 * 60));
		return timeDifferenceInMinutes;
		
	}
	
	public static Date getDifferentTime(int minutes){
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	public static Date getDifferentTime(Date date,int minutes){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}
	
	public static boolean checkSameDayRequest(Date pickUpTime) {
		boolean isSameDay = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(pickUpTime);
		isSameDay = (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) && (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE));
		return isSameDay;
	}
	
	public static boolean isCurrentTimeGreater(Date d1,Date d2) {
		boolean isGreater = false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		isGreater = cal1.after(cal2);
		return isGreater;
	}
	
}
