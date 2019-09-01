/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.sa;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.time.TimeTCPClient;
import org.openqa.selenium.WebDriver;


/**
 * The Class CurrencyAndDates...
 */
public class CurrencyDateTime{

	/** The driver. */
	public static WebDriver driver;	

	/**
	 * Instantiates a new currency and dates.
	 *
	 * @param driver the driver
	 */
	public CurrencyDateTime(WebDriver driver){
		this.driver=driver;
	}


	/**
	 * Checks if the date passed is valid or not. It can also checks for Leap year dates...
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param date. Format should be "dd-MM-yyyy". Ex: 28-02-2016
	 * @return Boolean value is returned for valid/invalid date
	 * @throws Exception 
	 */
	public static boolean isDateValid(String date) throws Exception 
	{
		String DATE_FORMAT = "dd-MM-yyyy";
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false); //lenient - when true, date/time parsing is lenient
			df.parse(date); //Parses text from the beginning of the given string to produce a date
			return true;
		} catch (ParseException e) {
			return false;
		}
	}


	/**
	 * Checks if the date passed is valid or not. It can also checks for Leap year dates...
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param year. Ex: 2016
	 * @param month. Ex: 02
	 * @param day. Ex: 28
	 * @return Boolean value is returned for valid/invalid date
	 * @throws Exception 
	 */
	public static boolean isDateValid(int year, int month, int day) throws Exception 
	{
		boolean dateIsValid = true;
		try {
			LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;
	}

	
	/**
	 * Gets the current date and time from global internet server. Output Ex:"Fri Apr 22 20:23:53 IST 2016"
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the current date time from global internet server
	 * @throws Exception the exception
	 */
	public static Date getCurrentDateTimeFromNetwork() throws Exception {
		String TIME_SERVER = "time-a.nist.gov";
		
		// We want to timeout if a response takes longer than 60 seconds
		//timeClient.setDefaultTimeout(10000);
        // Connecting to time server
        // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
        // Make sure that your program NEVER queries a server more frequently than once every 4 seconds

	    NTPUDPClient timeClient = new NTPUDPClient();
	    InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
	    TimeInfo timeInfo = timeClient.getTime(inetAddress);
	    //long returnTime = timeInfo.getReturnTime();   //local device time
	    long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

	    Date time = new Date(returnTime);
	    System.out.println("Time from " + TIME_SERVER + ": " + time);

	    return time;
	}
	
	/**
	 * Returns Current Date from global internet server in custom format and order. 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @return the string
	 * @throws Exception 
	 */
	public static String getCurrentDateFromNetwork(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder) throws Exception {
		String TIME_SERVER = "time-a.nist.gov";

		Thread.sleep(10000);
        // Connecting to time server
        // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
        // Make sure that your program NEVER queries a server more frequently than once every 4 seconds

		NTPUDPClient timeClient = new NTPUDPClient();
	    InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
	    TimeInfo timeInfo = timeClient.getTime(inetAddress);
	    //long returnTime = timeInfo.getReturnTime();   //local device time
	    long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

	    Date time = new Date(returnTime);
	    
	    SimpleDateFormat dt; 
	    switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			dt = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			dt = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			dt = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			dt = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}

	    String date = dt.format(time);
	    //System.out.println(date);
	    
	    return date;
	}
	
	/**
	 * Gets the current date from global internet server. Output Ex: Fri Apr 22 20:26:47 IST 2016
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the current date from network2
	 * @throws Exception the exception
	 */
	public static Date getCurrentDateFromNetwork2() throws Exception
	{
		TimeTCPClient client = new TimeTCPClient();
		try
		{
			Thread.sleep(10000);
			String host = "time-a.nist.gov";//nist.time.nosc.us---, time-a.nist.gov
			// We want to timeout if a response takes longer than 10 seconds
			client.setDefaultTimeout(10000);
			// Connecting to time server
			// Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
			// Make sure that your program NEVER queries a server more frequently than once every 4 seconds
			client.connect(host);
			System.out.println(client.getDate());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			client.disconnect();
		}
		return client.getDate();
		
		
		//way2
//		try{
//	        HttpClient httpclient = new HttpClient();
//	        HttpResponse response = httpclient.executeMethod(new HttpGet("https://google.com/"));
//	        StatusLine statusLine = response.getStatusLine();
//	        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
//	            String dateStr = response.getFirstHeader("Date").getValue();
//	            //Here I do something with the Date String
//	            System.out.println(dateStr);
//
//	        } else{
//	            //Closes the connection.
//	            response.getEntity().getContent().close();
//	            throw new IOException(statusLine.getReasonPhrase());
//	        }
//	    }catch (ClientProtocolException e) {
//	        Log.d("Response", e.getMessage());
//	    }catch (IOException e) {
//	        Log.d("Response", e.getMessage());
//	    }

	}

	/**
	 * Returns Current Date from local system in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @return the string
	 * @throws Exception 
	 */
	public static String getCurrentDate(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}

		Date today = Calendar.getInstance().getTime();  
		String reportDate = df.format(today);
		return reportDate;
	}

	/**
	 * Returns Current Date and Time from local system in format(yyyyMMdd_HHmmss)
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String getCurrentDateTimeStamp() throws Exception
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		return timeStamp;
	}

	/**
	 * Returns tomorrow's Date in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @return the string
	 * @throws Exception 
	 */
	public static String getTomorrowsDate(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns yesterday's Date in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @return the string
	 * @throws Exception 
	 */
	public static String getYesterdaysDate(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns nextWeekSameDay in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @return the string
	 * @throws Exception 
	 */
	public static String getNextWeekSameDay(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns date of Past(specify difference in Days) from today in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @param days the days as int. Ex: 2
	 * @return the string
	 * @throws Exception 
	 */
	public static String getDateFromPastDays(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder, int days) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -days);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns date of Future(specify difference in Days) from today in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @param days the days as int. Ex: 2
	 * @return the string
	 * @throws Exception 
	 */
	public static String getDateOfFutureDays(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder, int days) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +days);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns date from Past(specify difference in Years) from today in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @param years the years as int. Ex: 2
	 * @return the string
	 * @throws Exception 
	 */
	public static String getDateFromPastYears(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder, int years) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -years);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Returns date of Future(specify difference in Years) from today in custom format and order.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param dateFormat the date format. Ex: "dd"
	 * @param monthFormat the month format. Ex: "MM" or "MMM" or "MMMM"
	 * @param yearFormat the year format. Ex: "yyyy"
	 * @param divider the divider. Ex: " " or "-" or "/"
	 * @param dateMonthYearOrder the date month year order. Ex: "dmy" or "ymd" or "myd", Also by default the order is "ymd"
	 * @param years the years as int. Ex: 2
	 * @return the string
	 * @throws Exception 
	 */
	public static String getDateOfFutureYears(String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder, int years) throws Exception
	{
		DateFormat df;
		switch(dateMonthYearOrder.toLowerCase())
		{
		case "dmy":
			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
			break;

		case "ymd": 
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;

		case "mdy": 
			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
			break;

		default: //default = dmy
			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
			break;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, +years);    
		String reportDate = df.format(cal.getTime());
		return reportDate;
	}

	/**
	 * Subtract date from date. "date2 - date1". Returns the difference in days.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param date1 the date1 in format "yyyy MM dd". Ex: 2015 10 14
	 * @param date2 the date2 in format "yyyy MM dd". Ex: 2015 10 20
	 * @param dateMonthYearOrder the date month year order for input/result date. Ex: "ymd" or "mdy" or "dmy", Also by default the order is "ymd"
	 * @return the difference in days
	 * @throws Exception 
	 */
	public static long subtractDateFromDateReturnDays(String date1, String date2, String dateMonthYearOrder) throws Exception
	{
		SimpleDateFormat format;
		try
		{
			switch(dateMonthYearOrder.toLowerCase())
			{
			case "ymd":
				format = new SimpleDateFormat("yyyy MM dd");
				break;

			case "mdy":
				format = new SimpleDateFormat("MM dd yyyy");
				break;

			case "dmy":
				format = new SimpleDateFormat("dd MM yyyy");
				break;

			default: // default = ymd
				format = new SimpleDateFormat("yyyy MM dd");
				break;
			}

			java.util.Date d1=null;
			java.util.Date d2=null;
			d1=format.parse(date1);
			d2=format.parse(date2);
			long diffDays = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
			//System.out.println("diffDays: " + diffDays);
			return diffDays;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Add hours to current date and time. Returns the date with added hours.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param hoursToAdd the hours to add
	 * @return the date
	 * @throws Exception 
	 */
	public static Date addHoursToCurrentDate(int hoursToAdd) throws Exception
	{
		DateFormat df;

		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, hoursToAdd); // adds one hour
		return cal.getTime(); // returns new date object, one hour in the future
	}


	//	public static String addHoursToCustomDate(int hoursToAdd, String dateFormat, String monthFormat, String yearFormat, String divider, String dateMonthYearOrder){
	//		DateFormat df;
	//		switch(dateMonthYearOrder.toLowerCase())
	//		{
	//		case "dmy":
	//			df = new SimpleDateFormat(dateFormat + divider + monthFormat + divider + yearFormat);
	//			break;
	//
	//		case "ymd": 
	//			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
	//			break;
	//
	//		case "mdy": 
	//			df = new SimpleDateFormat(monthFormat + divider + dateFormat + divider + yearFormat);
	//			break;
	//
	//		default: //default = dmy
	//			df = new SimpleDateFormat(yearFormat + divider + monthFormat + divider + dateFormat);
	//			break;
	//		}
	//		Calendar cal = Calendar.getInstance();
	//		cal.add(Calendar.HOUR_OF_DAY, hoursToAdd); // adds one hour
	//		String reportDate = df.format(cal.getTime());
	//		return reportDate;
	//	}

}