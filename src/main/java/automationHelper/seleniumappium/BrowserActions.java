/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import rest.impl.IronWasp;
// TODO: Auto-generated Javadoc
//import jxl.read.biff.BiffException;

/**
 * The Class BrowserActions.
 */
public class BrowserActions{

	/** The driver. */
	public static WebDriver driver;	

	/**
	 * Instantiates a new browser actions.
	 *
	 * @param driver the driver
	 */
	public BrowserActions(WebDriver driver){
		this.driver=driver;
	}


	/** The wrk1. */
	static Workbook wrk1;

	/** The sheet1. */
	static Sheet sheet1;
	//static Cell colRow;


	/**
	 * windowMax - Selenium code to maximize window size.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void windowMax() throws Exception {
		driver.manage().window().maximize();
		WebCommonMethods.implicitSleep();
	}

	/**
	 * deleteCookies - Delete all cookies in the browser session.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void deleteCookies() throws Exception 
	{
		String browser = FilesAndFolders.getPropValue("driverName");
		System.out.println("driverName: " + browser);
		if(browser.equalsIgnoreCase("htmlunitdriver"))//htmlUnitDriver
		{
			//do nothing
		}
		else//webdriver
		{
			driver.manage().deleteAllCookies(); 	
		}
	}

	/**
	 * a. Open Url based on 'DbDomain' parameter value in 'config.properties' file <br>
	 * Possible values of DbDomain:<br>
	 * jenkins, qa, automation, production <br><br>
	 * 
	 * b. Initiate IronWasp session if driverName=firefoxIronWasp in 'config.properties'
	 *  
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void openURLBasedOnDbDomain() throws Exception
	{
		String DbDomain = FilesAndFolders.getPropValue("DbDomain");
		String url;
		int flag;

		deleteCookies(); 
		//IronWasp Connection logic for Security Testing
		String browser = FilesAndFolders.getPropValue("driverName");
		if (browser.contentEquals("firefoxIronWasp"))
		{
			IronWasp.workflowStart();	
			//			try
			//			{
			//				IronWasp.workflowStart();	
			//			}
			//			catch(ConnectException e)
			//			{
			//				Reporter.log("IronWasp Server has not been started...Ignore this error if you don't wish to track your test flow traffic & requests for IronWasp...",true);
			//			}			
		}

		switch(DbDomain)
		{
		case "jenkins":
			url = FilesAndFolders.getPropValue("urlJenkinsServer");
			System.out.println("url: " + url);
			driver.get(url);
			System.out.println("URL loaded successfully:" + url);
			flag=1;
			break;

		case "qa":
			url = FilesAndFolders.getPropValue("urlQaServer");
			System.out.println("url: " + url);
			driver.get(url);
			System.out.println("URL loaded successfully:" + url);
			flag=1;
			break;
		case "automation":
			url = FilesAndFolders.getPropValue("urlAutomationServer");
			System.out.println("url: " + url);
			driver.get(url);
			System.out.println("URL loaded successfully:" + url);
			flag=1;
			break;
		case "production":
			url = FilesAndFolders.getPropValue("urlProductionServer");
			System.out.println("url: " + url);
			driver.get(url);
			System.out.println("URL loaded successfully:" + url);
			flag=1;
			break;

		default:
			flag=0;
			Assert.assertTrue(flag==1, "URL from config file is a mismatch with available switch cases...");
			break;
		}
		//handling ssl certification
		try{
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
		catch(Exception e){}

		windowMax();
		WebCommonMethods.implicitSleep();
	}

	
	/**
	 * Open's a url in the browser session. 
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param url the url
	 * @throws Exception the exception
	 */
	public static void openURL(String url) throws Exception
	{
		//deleteCookies();

		//IronWasp Connection logic for Security Testing
		String browser = FilesAndFolders.getPropValue("driverName");
		if (browser.contentEquals("firefoxIronWasp"))
		{
			IronWasp.workflowStart();	
			//			try
			//			{
			//				IronWasp.workflowStart();	
			//			}
			//			catch(ConnectException e)
			//			{
			//				Reporter.log("IronWasp Server has not been started...Ignore this error if you don't wish to track your test flow traffic & requests for IronWasp...",true);
			//			}			
		}


		System.out.println("url: " + url);
		driver.get(url);
		System.out.println("URL loaded successfully:" + url);

		//handling ssl certification
		try{
			driver.navigate().to("javascript:document.getElementById('overridelink').click()");
		}
		catch(Exception e){}

		windowMax();
		WebCommonMethods.implicitSleep();
	}


	/**
	 * Close IronWasp session(if enabled during openUrl()) and also quit current browser session.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception 
	 */
	public static void quit() throws Exception
	{
		//Close IronWasp Connection
		String browser = FilesAndFolders.getPropValue("driverName");
		if (browser.contentEquals("firefoxIronWasp"))
		{
			IronWasp.workflowEnd();			
		}

		// Closing Browser instance
		driver.quit(); 
	}

}