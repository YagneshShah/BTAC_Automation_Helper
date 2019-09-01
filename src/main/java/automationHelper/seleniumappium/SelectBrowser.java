/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;

import rest.impl.IronWasp;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectBrowser.
 */
public class SelectBrowser {

	/**
	 * Instantiates a new select browser.
	 */
	public SelectBrowser()
	{
		super();
	}
	

 


	/**
	 * Initiates a new fresh browser session based on the value of "driverName" key in config.properties file
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the browser
	 * @throws Exception the exception
	 */
	public static WebDriver getBrowser() throws Exception 
	{
		WebDriver driver = null;
		HtmlUnitDriver htmlDriver = null;

		String browser = FilesAndFolders.getPropValue("driverName");
		System.out.println("Browser name is :" + browser);


		//Select Browser logic
		//HtmlUnitDriver
		//		if(browser.equalsIgnoreCase("htmlunitdriver"))
		//		{
		//			htmlDriver = new HtmlUnitDriver();//use this if u dont wish JS to be enabled
		//			//htmlDriver = new HtmlUnitDriver(true);//use this if u wish JS to be enabled. By default it will emulate IE's Javascript 
		//			//htmlDriver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);//use this if u wish JS to be enabled. By default it will emulate FF24's Javascript
		//			System.out.println("Browser session initiated...");
		//		}



		//Firefox with GeckoDriver. Supported for Webdriver 3.x from Firefox 48 onwards...
		if(browser.equalsIgnoreCase("FFGeckoWin64")) //supported for webdriver 3.x
		{
			String FFGeckoWin64 = FilesAndFolders.getPropValue("FFGeckoWin64");	
			System.setProperty("webdriver.gecko.driver", FFGeckoWin64); // setting path of the Gecko Driver
			//set browser preferences
			driver = new FirefoxDriver(FirefoxDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		else if(browser.equalsIgnoreCase("FFGeckoWin64Firebug"))
		{		
			String FFGeckoWin64 = FilesAndFolders.getPropValue("FFGeckoWin64");	
			System.setProperty("webdriver.gecko.driver", FFGeckoWin64); // setting path of the Gecko Driver

			//set browser preferences
			driver = new FirefoxDriver(FirefoxDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		//Firefox-Mac
		else if(browser.equalsIgnoreCase("FFGeckoMac64")){	
			String FFGeckoMac64 = FilesAndFolders.getPropValue("FFGeckoMac64");	
			System.setProperty("webdriver.gecko.driver", FFGeckoMac64); // setting path of the Gecko Driver
			
			driver = new FirefoxDriver(FirefoxDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		//Firefox without GeckoDriver. Supported for Webdriver 2.x and untill Firefox 47. From FF48 onwards, use GEckodriver along with webdriver 3.x
		else if(browser.equalsIgnoreCase("firefox")) //supported for webdriver 2.x
		{
			driver = new FirefoxDriver(FirefoxDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		else if(browser.equalsIgnoreCase("firefoxFirebug")){		
			driver = new FirefoxDriver(FirefoxDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		else if(browser.equalsIgnoreCase("firefoxIronWasp"))
		{
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 1);
			profile.setPreference("network.proxy.http", IronWasp.ipAddress);
			profile.setPreference("network.proxy.http_port", IronWasp.portNumber);
			profile.setPreference("network.proxy.ssl", IronWasp.ipAddress);
			profile.setPreference("network.proxy.ssl_port", IronWasp.portNumber);
			profile.setPreference("network.proxy.no_proxy_on","");
			driver = new FirefoxDriver(profile);
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		else if(browser.equalsIgnoreCase("firefoxWithZap")){	
			String PROXY = "localhost:8080";

			org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
			proxy.setHttpProxy(PROXY)
			.setFtpProxy(PROXY)
			.setSslProxy(PROXY);
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(CapabilityType.PROXY, proxy);

			driver = new FirefoxDriver(cap);
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		else if(browser.equalsIgnoreCase("firefox_WP")){
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.link.open_newwindow", 3);
			driver = new FirefoxDriver(profile);
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		else if(browser.equalsIgnoreCase("firefoxUbuntu64"))
		{
			String Xport = System.getProperty("lmportal.xvfb.id",":22");
			final File firefoxPath = new File(System.getProperty("lmportal.deploy.firefox.path","/usr/bin/firefox"));
			FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);
			firefoxBinary.setEnvironmentProperty("DISPLAY",Xport);

			driver = new FirefoxDriver();
			//			driver = new FirefoxDriver(firefoxBinary,null);
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		//IE
		else if(browser.equalsIgnoreCase("ieWinx32"))
		{
			String ieWinx32 = FilesAndFolders.getPropValue("ieWinx32"); 
			System.setProperty("webdriver.ie.driver", ieWinx32); // setting path of the IEDriver
			
			driver = new InternetExplorerDriver(IEProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		else if(browser.equalsIgnoreCase("ieWinx64"))
		{
			String ieWinx64 = FilesAndFolders.getPropValue("ieWinx64");
			System.setProperty("webdriver.ie.driver", ieWinx64); // setting path of the IEDriver

			driver = new InternetExplorerDriver(IEProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		//Safari
		else if(browser.equalsIgnoreCase("safari")){
			driver = new SafariDriver();
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		//Chrome
		else if(browser.equalsIgnoreCase("chromeWinx32")){
			String chromeWinx32 = FilesAndFolders.getPropValue("chromeWinx32");
			System.setProperty("webdriver.chrome.driver", chromeWinx32); // setting path of the ChromeDriver
			driver = new ChromeDriver();
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		//Chrome Mac
		else if(browser.equalsIgnoreCase("chromeMac64")){
			String chromeMac64 = FilesAndFolders.getPropValue("chromeMac64");	
			System.setProperty("webdriver.chrome.driver", chromeMac64); // setting path of the ChromeDriver
			
			driver = new ChromeDriver(chromeDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}

		else if(browser.equalsIgnoreCase("chromeMac32")){
			String chromeMac32 = FilesAndFolders.getPropValue("chromeMac32");
			System.setProperty("webdriver.chrome.driver", chromeMac32); // setting path of the ChromeDriver
			
			driver = new ChromeDriver(chromeDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		else if(browser.equalsIgnoreCase("chromeLinux64")){
			String chromeLinux64 = FilesAndFolders.getPropValue("chromeLinux64");
			System.setProperty("webdriver.chrome.driver", chromeLinux64); // setting path of the ChromeDriver
			
			driver = new ChromeDriver(chromeDriverProfile());
			System.out.println("Browser session initiated...");
			
			Reporter.log(getBrowserAndVersion(driver),true);
		}
		return driver;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Gets the browser name and version number for the browser session which is initialized.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param driver the driver
	 * @return the browser name and version number as a String
	 * @throws Exception 
	 */
	public static String getBrowserAndVersion(WebDriver driver) throws Exception 
	{
		String browser_version = null;
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		
		String browser_platform = cap.getPlatform().toString();
		String browser_name = cap.getBrowserName();

		// This block to find out IE Version number
		if (browser_name.equalsIgnoreCase("internet explorer") ) {
			String uAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
			System.out.println(uAgent);
			//uAgent return as "MSIE 8.0 Windows" for IE8
			if (uAgent.contains("MSIE") && uAgent.contains("Windows")) {
				browser_version = uAgent.substring(uAgent.indexOf("MSIE")+5, uAgent.indexOf("Windows")-2);
			} else if (uAgent.contains("Trident/7.0")) {
				browser_version = "11.0";
			} else {
				browser_version = "0.0";
			}
		} else
		{
			//Browser version for Firefox and Chrome
			browser_version = cap.getVersion().toString();// .split(".")[0];
		}
		//String browserversion = browser_version.substring(0, browser_version.indexOf("."));
		String browserversion = browser_version;

		return "OS_Browser Details: " + browser_platform + "_" + browser_name + "_v" + browserversion;
		//return "Browser Details: "+ browser_name + "_v" + browserversion;

	}
	
	/**
	 * Desired Capabilities preferences settings for Chrome driver profile.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the desired capabilities
	 * @throws Exception the exception
	 */
	//Set chrome profile to locate particular download folder and other desired capabilities
	public static DesiredCapabilities chromeDriverProfile() throws Exception{
		String downloadFilepath = System.getProperty("user.dir") + "\\downloads";
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		
		DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
		chromeCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return chromeCapabilities;
	}

	/**
	 * Desired capabilities and Preferences settings for Firefox driver profile.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the firefox profile
	 * @throws Exception the exception
	 */
	//Set firefox profile to located particular download folder and other preferences
	private static FirefoxProfile FirefoxDriverProfile() throws Exception 
	{
		FirefoxProfile profile = new FirefoxProfile();
		
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", System.getProperty("user.dir") + "\\Downloads" );

		/*
		 * MIME for most common files are :
		 * Text File (.txt) text/plain
		 * PDF File (.pdf) application/pdf
		 * CSV File (.csv) text/csv
		 * MS Excel File (.xlsx) application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		 * MS word File (.docx) application/vnd.openxmlformats-officedocument.wordprocessingml.document
		 * 
		 * MIME types complete list is here: https://www.sitepoint.com/web-foundations/mime-types-complete-list/
		 */
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xml,application/pdf");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xml,application/pdf");

		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);

		String browser = FilesAndFolders.getPropValue("driverName"); //---debug for webdriver3.x
		if(browser.equalsIgnoreCase("FFGeckoWin64Firebug") || browser.equalsIgnoreCase("firefoxFirebug"))
		{
			String firebugAddon = FilesAndFolders.getPropValue("firebugAddon");
			String firepathAddon = FilesAndFolders.getPropValue("firepathAddon");

			File firebug = new File(firebugAddon);
			File firepath = new File(firepathAddon);
			profile.addExtension(firebug);
			profile.setPreference("extensions.firebug.currentVersion", "2.0");
			profile.addExtension(firepath);			
		}
		return profile;
	}
	
	/**
	 * Desired capabilities preferences settings for IE profile.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @return the desired capabilities
	 * @throws Exception the exception
	 */
	//Set IE profile to located particular download folder
	public static DesiredCapabilities IEProfile() throws Exception 
	{
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		
		return ieCapabilities;
	}
}
