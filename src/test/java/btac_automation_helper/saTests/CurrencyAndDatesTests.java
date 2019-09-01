/*
 * Date: October 12th 2015
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 * License Type: MIT
 */

package btac_automation_helper.saTests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import automationHelper.sa.CurrencyDateTime;
import automationHelper.sa.FilesAndFolders;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyAndDatesTests.
 */
public class CurrencyAndDatesTests{
	
	@Test
	public void isDateValid() throws Exception
	{
		Assert.assertTrue(CurrencyDateTime.isDateValid("28-02-2016"),"Invalid Date!!");
		Assert.assertFalse(CurrencyDateTime.isDateValid("29-02-2017"),"Invalid Date!!");
		Assert.assertFalse(CurrencyDateTime.isDateValid("299-02-2016"),"Invalid Date!!");
	}

	@Test
	public void isDateValidWay2() throws Exception
	{
		Assert.assertTrue(CurrencyDateTime.isDateValid(2016,02,29),"Invalid Date!!");
		Assert.assertFalse(CurrencyDateTime.isDateValid(2017,02,29),"Invalid Date!!");
		Assert.assertFalse(CurrencyDateTime.isDateValid(2016,02,299),"Invalid Date!!");
	}
	
	@Test
	public void getCurrentDateTimeFromNetwork() throws Exception
	{
		CurrencyDateTime.getCurrentDateTimeFromNetwork();
	}
	
	@Test
	public void getCurrentDateFromNetwork() throws Exception
	{
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy"," ","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy"," ","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMMM","yyyy"," ",""));
		System.out.println();
		
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","-","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","-","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","-","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMMM","yyyy","-",""));
		System.out.println();

		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","/","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","/","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MM","yyyy","/","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDateFromNetwork("dd","MMMM","yyyy","/",""));
		System.out.println();
	}
	
	@Test
	public void getCurrentDateFromNetwork2() throws Exception
	{
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
		CurrencyDateTime.getCurrentDateFromNetwork2();
	}
	
	@Test
	public void getCurrentDate() throws Exception
	{
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy"," ","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy"," ","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMMM","yyyy"," ",""));
		System.out.println();
		
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","-","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","-","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","-","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMMM","yyyy","-",""));
		System.out.println();

		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","/","dmy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","/","ymd"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MM","yyyy","/","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getCurrentDate: " + CurrencyDateTime.getCurrentDate("dd","MMMM","yyyy","/",""));
		System.out.println();
	}
	
	@Test
	public void getCurrentDateTimeStamp() throws Exception
	{
		System.out.println("dateTime Stamp:" + CurrencyDateTime.getCurrentDateTimeStamp());
	}
	
	/**
	 * Gets the tomorrows date.
	 *
	 * @return the tomorrows date
	 * @throws Exception 
	 */
	@Test
	public void getTomorrowsDate() throws Exception
	{
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy"," ","dmy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy"," ","ymd"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy"," ","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMMM","yyyy"," ",""));
		System.out.println();
		
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","-","dmy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","-","ymd"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","-","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMMM","yyyy","-",""));
		System.out.println();

		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","/","dmy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","/","ymd"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MM","yyyy","/","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getTomorrowsDate: " + CurrencyDateTime.getTomorrowsDate("dd","MMMM","yyyy","/",""));
		System.out.println();
	}
	
	/**
	 * Gets the yesterdays date.
	 *
	 * @return the yesterdays date
	 * @throws Exception 
	 */
	@Test
	public void getYesterdaysDate() throws Exception
	{
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy"," ","dmy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy"," ","ymd"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy"," ","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMMM","yyyy"," ",""));
		System.out.println();
		
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","-","dmy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","-","ymd"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","-","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMMM","yyyy","-",""));
		System.out.println();

		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","/","dmy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","/","ymd"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MM","yyyy","/","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getYesterdaysDate: " + CurrencyDateTime.getYesterdaysDate("dd","MMMM","yyyy","/",""));
		System.out.println();
	}
	
	/**
	 * Gets the next week same day.
	 *
	 * @return the next week same day
	 * @throws Exception 
	 */
	@Test
	public void getNextWeekSameDay() throws Exception
	{
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy"," ","dmy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy"," ","ymd"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy"," ","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMMM","yyyy"," ",""));
		System.out.println();
		
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","-","dmy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","-","ymd"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","-","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMMM","yyyy","-",""));
		System.out.println();

		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","/","dmy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","/","ymd"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MM","yyyy","/","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMM","yyyy"," ","mdy"));
		System.out.println("getNextWeekSameDay: " + CurrencyDateTime.getNextWeekSameDay("dd","MMMM","yyyy","/",""));
		System.out.println();
	}
	
	/**
	 * Gets the date from past days.
	 *
	 * @return the date from past days
	 * @throws Exception 
	 */
	@Test
	public void getDateFromPastDays() throws Exception
	{
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy"," ","dmy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy"," ","ymd",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMMM","yyyy"," ","",2));
		System.out.println();
		
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","-","dmy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","-","ymd",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","-","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMMM","yyyy","-","",2));
		System.out.println();

		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","/","dmy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","/","ymd",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MM","yyyy","/","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastDays: " + CurrencyDateTime.getDateFromPastDays("dd","MMMM","yyyy","/","",2));
		System.out.println();
	}
	
	
	/**
	 * Gets the date from past years.
	 *
	 * @return the date from past years
	 * @throws Exception 
	 */
	@Test
	public void getDateFromPastYears() throws Exception
	{
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy"," ","dmy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy"," ","ymd",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMMM","yyyy"," ","",2));
		System.out.println();
		
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","-","dmy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","-","ymd",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","-","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMMM","yyyy","-","",2));
		System.out.println();

		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","/","dmy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","/","ymd",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MM","yyyy","/","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateFromPastYears: " + CurrencyDateTime.getDateFromPastYears("dd","MMMM","yyyy","/","",2));
		System.out.println();
	}
	
	
	/**
	 * Gets the date of future days.
	 *
	 * @return the date of future days
	 * @throws Exception 
	 */
	@Test
	public void getDateOfFutureDays() throws Exception
	{
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy"," ","dmy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy"," ","ymd",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMMM","yyyy"," ","",2));
		System.out.println();
		
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","-","dmy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","-","ymd",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","-","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMMM","yyyy","-","",2));
		System.out.println();

		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","/","dmy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","/","ymd",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MM","yyyy","/","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureDays: " + CurrencyDateTime.getDateOfFutureDays("dd","MMMM","yyyy","/","",2));
		System.out.println();
	}
	
	
	
	/**
	 * Gets the date of future years.
	 *
	 * @return the date of future years
	 * @throws Exception 
	 */
	@Test
	public void getDateOfFutureYears() throws Exception
	{
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy"," ","dmy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy"," ","ymd",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMMM","yyyy"," ","",2));
		System.out.println();
		
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","-","dmy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","-","ymd",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","-","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMMM","yyyy","-","",2));
		System.out.println();

		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","/","dmy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","/","ymd",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MM","yyyy","/","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMM","yyyy"," ","mdy",2));
		System.out.println("getDateOfFutureYears: " + CurrencyDateTime.getDateOfFutureYears("dd","MMMM","yyyy","/","",2));
		System.out.println();
	}
	
	/**
	 * Subtract date from date return days.
	 * @throws Exception 
	 */
	@Test
	public void subtractDateFromDateReturnDays() throws Exception
	{
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("2015 10 14","2015 10 20",""));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("2015 10 14","2015 10 20","ymd"));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("10 14 2015","10 20 2015","mdy"));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("14 10 2015","20 10 2015","dmy"));
		System.out.println();

		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("2015 10 20","2015 10 14",""));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("2015 10 20","2015 10 14","ymd"));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("10 20 2015","10 14 2015","mdy"));
		System.out.println("subtractDateFromDate: " + CurrencyDateTime.subtractDateFromDateReturnDays("20 10 2015","14 10 2015","dmy"));
		System.out.println();
	}
	
	@Test
	public static void addHoursToDate() throws Exception
	{
		System.out.println("addHoursToDate(1 hr): " + CurrencyDateTime.addHoursToCurrentDate(1));
		System.out.println("addHoursToDate(12 hr): " + CurrencyDateTime.addHoursToCurrentDate(12));
	}
	
	
	
}