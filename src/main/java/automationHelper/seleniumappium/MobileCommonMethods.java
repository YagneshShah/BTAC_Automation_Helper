/*
 * Date: September 1st 2014
 * Architect: Yagnesh Shah
 * Contributor: Yagnesh Shah, Adil Imroz
 * Twitter handle: @YagneshHShah
 * Contact: yash.shah.g@gmail.com / yagnesh23.wordpress.com 
 */

package automationHelper.seleniumappium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.AWTException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import createBruteForce.createBruteForce;


// TODO: Auto-generated Javadoc
/**
 * The Class MobileCommonMethods.
 */
public class MobileCommonMethods
{
	/** The driver. */
	public static AppiumDriver driver;

	/** The sleep time min. */
	public static int sleepTimeMin;

	/** The sleep time min2. */
	public static int sleepTimeMin2;

	/** The sleep time average. */
	public static int sleepTimeAverage;

	/** The sleep time average2. */
	public static int sleepTimeAverage2;

	/** The sleep time max. */
	public static int sleepTimeMax;

	/** The sleep time max2. */
	public static int sleepTimeMax2;

//	public MobileCommonMethods(AppiumDriver driver){
//		this.driver=driver;
//	}

	/**
	 * Launch app based on appName. This will assist to launch multiple app with different appPackage/appActivity capabilities within same test script scope<br><br>
	 * Ex:"flipkart". This App name passed will concat with AppPackage/AppActivity string. The resulting string variable value is retrieved from config.properties file and will automatically launch the app/driver session 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param appName the app name. Ex:"flipkart" or"fk" or anything which is exactly same as variable name in config.properties file
	 * @throws Exception the exception
	 */
	public static void launchRealAndroidApp(String appName) throws Exception 
	{ //Update this method based on your app context. This is just for code reference
		
		System.out.println("*****Launching the app*****");

		System.out.println("appName:" + appName);
		String appPack = StringManipulation.concatString(appName, "AppPackage");
		String appAct = StringManipulation.concatString(appName, "AppActivity");
		String appPackage = FilesAndFolders.getPropValue(appPack);
		String appActivity = FilesAndFolders.getPropValue(appAct);
		System.out.println("appPackage: " + appPackage);
		System.out.println("appActivity: " + appActivity);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("appPackage",appPackage);
		capabilities.setCapability("appActivity",appActivity);

		//capabilities.setCapability("sessionOverride", true);
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		callingImplicitSleep();
	}

	
	/**
	 * Launch emulator and installs android app via local system APK path and then launches the App directly.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param localSysApkPath the local sys apk path.<br>
	 * Ex for windows: "D:/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk"<br>
	 * Ex for mac: "/Users/username/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk"
	 * @throws Exception the exception
	 */
	public static void launchEmulatorAndroidAppViaLocalAPKPath(String localSysApkPath) throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Native App******");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
		capabilities.setCapability(MobileCapabilityType.APP, localSysApkPath);//Ex: D:/Softwares/workspacePersonal/com.flipkart.android-4.2-APK4Fun.com.apk
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		callingImplicitSleep();
	}
	
	
	/**
	 * Launch emulator android app. This will assist to launch multiple app with different appPackage/appActivity capabilities within same test script scope<br><br>
	 * Ex:"flipkart". This App name passed will concat with AppPackage/AppActivity string. The resulting string "fkAppPackage/fkAppActivity" variable value is retrieved from config.properties file and will automatically launch the app/driver session
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param appName the app name. Ex:"flipkart" or"fk" or anything which is exactly same as variable name in config.properties file
	 * @throws Exception the exception
	 */
	public static void launchEmulatorAndroidApp(String appName) throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Native App******");

		String appPack = StringManipulation.concatString(appName, "AppPackage");
		String appAct = StringManipulation.concatString(appName, "AppActivity");
		String appPackage = FilesAndFolders.getPropValue(appPack);
		String appActivity = FilesAndFolders.getPropValue(appAct);
		System.out.println("appPackage: " + appPackage);
		System.out.println("appActivity: " + appActivity);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "4.4"); 
		capabilities.setCapability("appPackage",appPackage);
		capabilities.setCapability("appActivity",appActivity);
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		callingImplicitSleep();
	}
	
	/**
	 * Launch emulator android browser.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void launchEmulatorAndroidBrowser() throws Exception 
	{
		System.out.println("*****Launching Android Emulator - Browser******");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android Emulator");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "4.4"); 
		capabilities.setCapability("browserName", "Chrome");
		driver = new AppiumDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities); // initializing the driver
		Thread.sleep(4000);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(FilesAndFolders.getPropValue("pageLoadTimeout")), TimeUnit.SECONDS);
		callingImplicitSleep();
	}

	/**
	 * Screenshot mobile.<br>
	 * Stored within "failure_Screenshot" folder with File name format: methodName_dd_MM_yyyy_hh_mm_ss.png<br>
	 * Ex: loginCheck_28_10_2015_07_10_10.png
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param methodName the method name
	 * @throws Exception the exception
	 */
	public static void screenshotMobile(String methodName) throws Exception 
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");                   
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		String screenshotLocationWeb = FilesAndFolders.getPropValue("screenshotLocationWeb");
		try {
			FileUtils.copyFile(scrFile, new
					File((screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime())+".png")));
			Reporter.log("<a href='" +
					screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime()) + ".png'> <imgsrc='" + screenshotLocationWeb + methodName + "_" + formater.format(calendar.getTime()) + ".png' /> </a>");
			Reporter.setCurrentTestResult(null);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * Wait seconds.
	 * 
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param seconds the seconds
	 * @throws Exception the exception
	 */
	public static void waitSeconds(int seconds) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, seconds);
	}


	/**
	 * Waits for desired seconds and then checks if the element is visible or not.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @throws Exception the exception
	 */
	public static void waitingForTheElementToLoad(By by, int seconds) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Waits for desired Seconds and then checks for the element to be Selected. 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @throws Exception the exception
	 */
	public static void waitForElementToBeSelected(By by, int seconds) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeSelected(by));
	}

	/**
	 * Waits for desired Seconds for the element to be Clickable. 
	 * <br>Sometimes an element/button is inactive/greyed out and can't be interacted with it. So, it waits for specified seconds and checks again if element is clickable or not.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @throws Exception the exception
	 */
	public static void waitForElementToBeClickable(By by, int seconds) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}


	/**
	 * Webdriver implicit sleep. Waits for seconds specified in "implicitWaitTime" key from config.properties file
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @throws Exception the exception
	 */
	public static void callingImplicitSleep() throws Exception
	{
		String implicitWaitTime = FilesAndFolders.getPropValue("implicitWaitTime");

		int implicitWait = Integer.parseInt(implicitWaitTime);
		driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
	}


	/**
	 * Initialize all sleep timings variables. Method is used for Project internal purpose.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void initializeSleepTimings() throws IOException, Exception
	{
		FileReader reader = new FileReader("./config.properties"); //Reading configuration file
		Properties prop = new Properties();
		prop.load(reader);
		sleepTimeMin = Integer.parseInt(prop.getProperty("sleepTimeMin"));
		//System.out.println("sleepTimeMin:" + sleepTimeMin);
		sleepTimeMin2 = Integer.parseInt(prop.getProperty("sleepTimeMin2"));
		//System.out.println("sleepTimeMin2:" + sleepTimeMin2);
		sleepTimeAverage = Integer.parseInt(prop.getProperty("sleepTimeAverage"));
		//System.out.println("sleepTimeAverage:" + sleepTimeAverage);
		sleepTimeAverage2 = Integer.parseInt(prop.getProperty("sleepTimeAverage2"));
		//System.out.println("sleepTimeAverage2:" + sleepTimeAverage2);
		sleepTimeMax = Integer.parseInt(prop.getProperty("sleepTimeMax"));
		//System.out.println("sleepTimeMax:" + sleepTimeMax);
		sleepTimeMax2 = Integer.parseInt(prop.getProperty("sleepTimeMax2"));
		//System.out.println("sleepTimeMax2:" + sleepTimeMax2);
	}

	/**
	 * Checks if element is present on the current screen. Returns true/false if element is present/not present
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param driver the driver
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @return True, if is elements present. False, if not present
	 * @throws Exception the exception
	 */
	public static boolean isElementPresent(WebDriver driver, By by) throws Exception
	{
		try
		{
			return driver.findElements(by).size() > 0;
		}
		catch(Exception e) //NoSuchElementException
		{
			System.out.println("No such element found on current screen, for the locator: " + by);
			return false;
		}
	}
	
    /**
     * Checks if element is displayed or not.
     *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
     * @param byElementLocator the by element locator
     * @return the boolean
     */
    public static Boolean isElementDisplayed(By byElementLocator)
    {
    	Boolean isDisplayed;
    	try
    	{
    		isDisplayed = driver.findElement(byElementLocator).isDisplayed();
    	}
    	catch(NoSuchElementException e1)
    	{
    		isDisplayed = false;
    	}
    	return isDisplayed;
    }

	/**
	 * Checks if element is present in current screen of mobile device for the locator passed. Returns the count of such elements for that same locator.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param driver the driver
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @return the size of elements(in int) found for the locator passed. Returns zero if no such element found.
	 * @throws Exception the exception
	 */
	public static int countElementsPresentWithSameLocator(WebDriver driver, By by) throws Exception
	{
		if(driver.findElements(by).size() > 0)
		{
			List<WebElement> elms = driver.findElements(by);
			System.out.println("Total elements with different Index value for same locator: " + by + " is: " + elms.size());
			return elms.size();
		}
		else
		{
			System.out.println("No such element found on current screen, for the locator: " + by);
			return 0;
		}
	}

	/**
	 * Gets all the elements present for the locator passed.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param driver the driver
	 * @param by Pass the element as By.xpath / By.Id / By.Name or any other locator type supported with By
	 * @return all the elements present for the locator, which can be used later to interact via loop or individual index value
	 * @throws Exception 
	 */
	public static List<WebElement> getElementsPresentForSameLocator(WebDriver driver, By by) throws Exception
	{
		if(driver.findElements(by).size() > 0)
		{
			List<WebElement> elms = driver.findElements(by);
			System.out.println("Total elements with different Index value for same locator: " + by + " is: " + elms.size());
			return elms;
		}
		else
		{
			System.out.println("No such element found on current screen, for the locator: " + by);
			return null;
		}
	}

	/**
	 * Elements at different index with same locator is clicked to check what exactly it opens. 
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param elementLocator the element locator which is ambiguous on BasePage
	 * @param expectedPageTitleLocator the expected BasePage title locator. Checks if control is still on same BasePage after clicking.
	 * @param basePageUniqueElementLocator the base page unique element locator for assert purpose. Ex:'By.name(hamburgerName)'
	 * @throws Exception the exception
	 */
	public static void clickElementsPresentWithSameLocatorAtDiffIndex(By elementLocator, By expectedPageTitleLocator,By basePageUniqueElementLocator) throws Exception
	{
		Thread.sleep(3000);
		
		List <WebElement> elms = MobileCommonMethods.getElementsPresentForSameLocator(driver, elementLocator);
		for(int i=0;i<elms.size();i++)
		{
			elms.get(i).click();
			MobileCommonMethods.waitSeconds(MobileCommonMethods.sleepTimeMin);
			//if(MobileCommonMethods.waitingForTheElementToLoad(by))
				
			if(driver.getTitle().equalsIgnoreCase(driver.findElement(expectedPageTitleLocator).getText()))
			{
				//do nothing
			}
			else //navigate back to previous page
			{
				MobileCommonMethods.sendKeyEvent("deviceBackKeyEvent");
				MobileCommonMethods.waitSeconds(MobileCommonMethods.sleepTimeMin);
				MobileCommonMethods.waitingForTheElementToLoad(basePageUniqueElementLocator,60);//confirms that previous page is loaded
			}
		}
	} 
	
	/**
	 * Select an element at specific index and clicks on it.
	 * <br>Locator value might be ambiguous on the page(Array of elements with same locator) and hence element can be identified via unique Index value.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param byElementLocator the by element locator."By.className(String className)"
	 * @param index the index
	 * @throws Exception the exception
	 */
	public static void clickElementAtIndex(By byElementLocator, int index) throws Exception
	{
		List<WebElement> elms = driver.findElements(byElementLocator);
		System.out.println(elms.size());
		elms.get(index).click();
		MobileCommonMethods.waitSeconds(MobileCommonMethods.sleepTimeMin);
	}
	
//	public static void selectMenuItem(String menu, String menuItem) throws Exception
//	{--
//	
//	}
	
	/**
	 * Clear field content.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param by the by. Ex: "By.className(String className)"
	 * @throws Exception 
	 */
	public static void clearField(By by) throws Exception
	{
		int flag;
		if(isElementPresent(driver,by))
		{
			while(driver.findElement(by).getText().length()!=0)
			{
				sendKeyEvent("backspaceKeyEvent");
			}
			flag=1;
		}
		else
		{
			flag=0;
		}
		Assert.assertEquals(flag==1, "Unable to clear field...No such element found on current screen, for the locator: " + by );
	}


	/**
	 * Customized Appium Send key event<br>
	 * Pass the exact parameter name which exists in config.properties file within "Android Key Event properties" section
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param keyEvent the key event. Ex: "backspaceKeyEvent" variable which exists in config.properties file
	 * @throws Exception 
	 */
	public static void sendKeyEvent(String keyEvent) throws Exception
	{
		driver.sendKeyEvent(Integer.parseInt(FilesAndFolders.getPropValue("keyEvent")));//device back key event
	}
	
    /**
     * Navigate back: Navigates to previous page.
     *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
     * @throws Exception the exception
     */
    public static void navigateBack() throws Exception
    {
		MobileCommonMethods.sendKeyEvent("deviceBackKeyEvent");
    }


	/**
	 * Gets the center.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param byElementLocator the by element locator. Ex: "By.className(String className)"
	 * @return the center(Datatype - Point)
	 * @throws Exception the exception
	 */
	public static Point getCenter(By byElementLocator) throws Exception 
	{
		Point upperLeft = driver.findElement(byElementLocator).getLocation();
		Dimension dimensions = driver.findElement(byElementLocator).getSize();
		return new Point(upperLeft.getX() + dimensions.getWidth()/2, upperLeft.getY() + dimensions.getHeight()/2);
	}



	/**
	 * Custom Swipe method for Appium. Helps for Device fragmentation when screen resolutions are different.
	 * <br> Keeps swiping the screen from start to end location for N times 
	 * 
	 * @author Yagnesh Shah: yash.shah.g@gmail.com/yagnesh23.wordpress.com, Adil Imroz
	 * @param xStart the x start coordinates
	 * @param yStart the y start coordinates
	 * @param xEnd the x end coordinates
	 * @param yEnd the y end coordinates
	 * @param swipeTime the sweep time
	 * @param totalSwipes the total swipes
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public static void swipe(double xStart, double yStart, double xEnd, double yEnd, int swipeTime, int totalSwipes) throws IOException, Exception
	{
		//		FileReader reader = new FileReader("../RBL/config.properties"); //Reading configuration file
		//		Properties prop = new Properties();
		//		prop.load(reader); 
		//		String totalHorizontalPixels = prop.getProperty("totalHorizontalPixels");
		//		String totalVerticalPixels = prop.getProperty("totalVerticalPixels");

		Dimension size = driver.manage().window().getSize(); 

		int HorizontalPixels = size.getWidth();
		int VerticalPixels = size.getHeight();

		String totalHorizontalPixels = Integer.toString(HorizontalPixels);
		String totalVerticalPixels = Integer.toString(VerticalPixels);

		double totalHorizontalPixelCount = Double.parseDouble(totalHorizontalPixels);
		double totalVerticalPixelCount = Double.parseDouble(totalVerticalPixels);

		double X_start = Math.round(xStart * totalHorizontalPixelCount);
		double X_end = Math.round(xEnd * totalHorizontalPixelCount);
		double Y_start = Math.round(yStart * totalVerticalPixelCount);
		double Y_end = Math.round(yEnd * totalVerticalPixelCount);

		int startX = (int)(X_start);
		int endX = (int)(X_end);
		int startY = (int)(Y_start);
		int endY = (int)(Y_end);

		for(int i = 0; i<totalSwipes; i++)
		{		
			driver.swipe(startX,startY,endX,endY, swipeTime);
		}
	}	

	/**
	 * Swipe up.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param swipeTimeInMilliSecs the swipe time in milli secs
	 * @param swipeRepeat the number of times 'swipe up' repeat
	 * @throws InterruptedException the interrupted exception
	 */
	public static void swipeUp(int swipeTimeInMilliSecs, int swipeRepeat) throws InterruptedException
    {
        Dimension size = driver.manage().window().getSize();
        
        int x=size.width/2;
        int y_start=(int)(size.height*0.85);//60% of height
        int y_end=(int)(size.height*0.15);//30% of height
        
//        System.out.println("size width:" + size.width);
//        System.out.println("size height:" + size.height);
//        System.out.println("X Dimention:" + x);
//        System.out.println("Y Start:" + y_start);
//        System.out.println("Y End:" + y_end);
        
        for(int i=0; i<swipeRepeat; i++)
        {
        	System.out.println("Swipe:" + i);
            driver.swipe(x,y_start,x,y_end,swipeTimeInMilliSecs);
            Thread.sleep(1000);
        }
    }
	
	/**
	 * Swipe down.
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param swipeTimeInMilliSecs the swipe time in milli secs
	 * @param swipeRepeat the number of times 'swipe down' repeats
	 * @throws InterruptedException the interrupted exception
	 */
	public static void swipeDown(int swipeTimeInMilliSecs, int swipeRepeat) throws InterruptedException
    {
        Dimension size = driver.manage().window().getSize();

        int x=size.width/2;
        int y_start=(int)(size.height*0.15);//30% of height
        int y_end=(int)(size.height*0.85);//60% of height
        
//        System.out.println("size width:" + size.width);
//        System.out.println("size height:" + size.height);
//        System.out.println("X Dimention:" + x);
//        System.out.println("Y Start:" + y_start);
//        System.out.println("Y End:" + y_end);

        for(int i=0; i<swipeRepeat; i++)
        {
        	System.out.println("Swipe:" + i);
            driver.swipe(x,y_start,x,y_end,swipeTimeInMilliSecs);
            Thread.sleep(1000);
        }
    }
	
    /**
     * Scroll to and click on element.
     * <br> Faster than 'scrollFindAndClickOnElement()' but doesn't work if Element locator is not of type 'text'/'name'.
     *
     * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
     * @param elementName the element name
     * @throws Exception the exception
     */
    public static void scrollToAndClickOnElement(String elementName)throws Exception
    {    	
        driver.scrollTo(elementName);
        driver.findElement(By.name(elementName)).click();
        //Reporter.log("Element '" + elementName + "' not found and hence unable to click it!");
    }
	
    /**
     * Find and click on element. 
     * <br>Step 1: If Element is not found then do 'Swipe Up' and try again to find element. 
     * <br>Step 2: Repeat step 1 process until element is found. Worst case, repeat 'Swipe and Find' process for limit specified by 'repeatSwipe'
     * <br><br> As compared to 'scrollToAndClickOnElement()' works for all kinds of By Locator. But, starts swiping screen from current till end of page. Hence, if the element is above current screen then it won't find the element.
     *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
     * @param byElementLocator the by element locator. Ex: "By.className(String className)"
     * @param repeatSwipe Total swipe to be done. Tries to find an element after Swipe is done each time.
     * @throws Exception the exception
     */
    public static void scrollFindAndClickOnElement(By byElementLocator, int repeatSwipe)throws Exception
    {    	
        for(int i=0;i<repeatSwipe;i++)
        {
        	//System.out.println("loop:" +i);
            Thread.sleep(2000);
            if (isElementDisplayed(byElementLocator))
            {
            	//System.out.println("inside if");
                driver.findElement(byElementLocator).click();
                break;
            }
            else
            {
            	//System.out.println("inside else");
                swipeUp(3000,1);
            }
        }
        Reporter.log("Element '" + byElementLocator + "' not found and hence unable to click it!");
    }
	

	/**
	 * Quits app + driver + takes screenshot of the page where failure occurred(if any).<br> 
	 * Screenshot is saved @..projectRootDirectory/failure_screenshots/
	 *
	 * @author Yagnesh Shah - yash.shah.g@gmail.com  /  yagnesh23.wordpress.com
	 * @param result the result. Ex: "ITestResult result". This needs TestNG Jar to work.
	 * @throws Exception 
	 */
	public static void quitApp(ITestResult result) throws Exception
	{
		String methodName = result.getName();
		if(!result.isSuccess()){            
			MobileCommonMethods.screenshotMobile(methodName);
		}
		driver.quit();
	}



	
	
	
	
	
	
	
	
	





























//	public static void selectDropdownItemBootStrap(WebElement dropdown, String itemName) throws IOException, InterruptedException, AWTException
//	{
//		Thread.sleep(WebCommonMethods.sleepTimeMin);
//		dropdown.click();
//		Thread.sleep(WebCommonMethods.sleepTimeMin);
//		dropdown.findElement(By.xpath("../div/ul/li[text()='"+itemName+"']")).click();
//
//		System.out.println("Clicked on "+  itemName);
//	}
//
//	public void webView() throws InterruptedException {
//		WebElement button = driver.findElement(By.id("buttonStartWebview"));
//		button.click();
//		Thread.sleep(6000);
//		Set<String> contextNames = driver.getContextHandles();
//		for (String contextName : contextNames) {
//			System.out.println(contextName);
//			if (contextName.contains("WEBVIEW")){
//				driver.context(contextName);
//			}
//		}
//		WebElement inputField = driver.findElement(By.id("name_input"));
//		inputField.sendKeys("Some name");
//		inputField.submit();
//	}
//
//	public void openMenuPosition(int index) 
//	{
//		WebElement row;
//
//		MobileElement table = (MobileElement)driver.findElementByClassName("UIATableView");
//		row = table.findElementsByClassName("UIATableCell").get(index);
//		row.click();
//	}
//
//	public void testScroll() {
//		//scroll menu
//		//get initial third row location
//		MobileElement row =(MobileElement)driver.findElementsByClassName("UIATableCell").get(2);
//		Point location1 = row.getLocation();
//		Point center = getCenter(row);
//		//perform swipe gesture
//		driver.swipe(center.getX(), center.getY(), center.getX(), center.getY()-20, 1);
//		//get new row coordinates
//		Point location2 = row.getLocation();
//		Assert.assertEquals(location1.getX(), location2.getX());
//		Assert.assertNotSame(location1.getY(), location2.getY());
//	}
//	public void testSlider() {
//		//go to controls
//		openMenuPosition(1);
//		//get the slider
//		WebElement slider = driver.findElementByClassName("UIASlider");
//		Assert.assertEquals("50%", slider.getAttribute("value"));
//		Point sliderLocation = getCenter(slider);
//		driver.swipe(sliderLocation.getX(), sliderLocation.getY(), sliderLocation.getX()-100, sliderLocation.getY(), 1);
//		Assert.assertEquals("0%", slider.getAttribute("value"));
//	}
//
//	public void testSessions() throws Exception {
//		HttpGet request = new HttpGet("http://localhost:4723/wd/hub/sessions");
//		@SuppressWarnings("resource")
//		HttpClient httpClient = new HttpClient();
//		HttpResponse response = httpClient.execute(request);
//		HttpEntity entity = response.getEntity();
//		JSONObject jsonObject = (JSONObject) new JSONParser().parse(EntityUtils.toString(entity));
//
//		String sessionId = driver.getSessionId().toString();
//		Assert.assertEquals(jsonObject.get("sessionId"), sessionId);
//	}
//	public void testSource() {
//		//get main view soruce
//		String source_main = driver.getPageSource();
//		Assert.assertTrue(source_main.contains("UIATableView"));
//		Assert.assertTrue(source_main.contains("TextFields, Uses of UITextField"));
//
//		//got to text fields section
//		openMenuPosition(2);
//		String source_textfields = driver.getPageSource();
//		Assert.assertTrue(source_textfields.contains("UIAStaticText"));
//		Assert.assertTrue(source_textfields.contains("TextFields"));
//
//		Assert.assertNotSame(source_main, source_textfields);
//	}






	
	
	
	
	
	
	
	





	// Method for Agent sigin
	//	public static void appSignin(String username) throws InterruptedException, BiffException, IOException {//Update this method based on your app context. This is just for code reference
	//
	//		// Text with signin button :: Sign in
	//		Cell[] record = FilesAndFolders.mobileReadExcel("validLogin",username); // read from excel
	//		String mobileNumber = record[1].getContents();
	//		//update below comments based on your app context and then you may use this method		
	//		driver.findElementByName(SigninPageObjects.textField_NovopaySignIn).sendKeys(mobileNumber);
	//		driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click();
	//		MobileCommonMethods.mPinEntry(username); // method to enter the mpin
	//		driver.findElementByName(SigninPageObjects.button_TermsOfServiceAccept).click();//No content desc for this button
	//		MobileCommonMethods.implicitSleep();
	//		System.out.println("App sign in success...");
	//		
	//	}
	//
	//	public static void attack(String username) throws InterruptedException, BiffException, IOException 
	//	{
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("validLogin",username); // read from excel
	//		String mobileNumber = record[1].getContents();
	//		//update below comments based on your app context and then you may use this method		
	//		driver.findElementByName(SigninPageObjects.textField_NovopaySignIn).sendKeys(mobileNumber);
	//		driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click();
	//		//started at 
	//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//		Date Startdate = new Date();
	//		System.out.println("Test Started At : " +dateFormat.format(Startdate));
	//		
	//
	//		//String[] allValues= createBruteForce.getAllBruteForceCombinations(0,9,4);
	//		String[] allValues2= createBruteForce.useGivenCombinations(5340,5555);
	//		for(int i=0;i<allValues2.length;i++)
	//		{ 
	//		    	String pin=String.valueOf(allValues2[i]);
	//		    	int a=Integer.parseInt(pin);
	//    			String mPin_btn4=String.valueOf(a % 10); 
	//    			a = a / 10;
	//    			String mPin_btn3=String.valueOf(a % 10);
	// 
	//    			a = a / 10;
	//    			String mPin_btn2=String.valueOf(a % 10);
	// 
	//    			a = a / 10;
	//		    	String mPin_btn1=String.valueOf(a % 10);
	//		    	    	
	//		        System.out.println("Checking for "+pin);
	//				driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn1).click();	//testing server password
	//				driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn2).click();	//testing server password
	//				driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn3).click();	//testing server password
	//				driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn4).click(); //testing server password
	//				driver.findElementByName("layout_mpin_entry_btnDone").click();//tap on tick button	
	//
	//				Date enddate = new Date();
	//		
	//		    	driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click(); 
	//		}	
	//	}
	//	public static void invalidAppSignin(String username) throws InterruptedException, BiffException, IOException {//Update this method based on your app context. This is just for code reference
	//
	//		// Text with signin button :: Sign in
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("InvalidLogin",username); // read from excel
	//		String mobileNumber = record[1].getContents();
	//		//		update below comments based on your app context and then you may use this method		
	//		driver.findElementByName(SigninPageObjects.textField_NovopaySignIn).sendKeys(mobileNumber);
	//		driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click();
	//		MobileCommonMethods.invalidmPinEntry(username); // method to enter the mpin
	//	}
	//
	//	public static void appSigninWithInvalid5DigitMobileNumber(String username) throws InterruptedException, BiffException, IOException {//Update this method based on your app context. This is just for code reference
	//
	//		// Text with signin button :: Sign in
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("InvalidLogin",username); // read from excel
	//		String mobileNumber = record[1].getContents();
	//		//		update below comments based on your app context and then you may use this method		
	//		driver.findElementByName(SigninPageObjects.textField_NovopaySignIn).sendKeys(mobileNumber);
	//		driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click();
	//	}
	//
	//
	//	public static void appSigninWithoutMobileNumber(String username) throws InterruptedException, BiffException, IOException {//Update this method based on your app context. This is just for code reference
	//
	//		// Text with signin button :: Sign in
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("InvalidLogin",username); // read from excel
	//		String mobileNumber = record[1].getContents();
	//		//		update below comments based on your app context and then you may use this method		
	//		//driver.findElementByName(SigninPageObjects.textField_NovopaySignIn).sendKeys(mobileNumber);
	//		driver.findElementByName(SigninPageObjects.button_NovopaySignIn).click();
	//	}
	//
	//
	//	public static void mPinEntry(String username) throws BiffException, IOException{//Update this method based on your app context. This is just for code reference
	//
	//		// Fetching the mPin of the mentioned user from the excel
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("validLogin",username);
	//		String mPin_btn1 = record[2].getContents().substring(0, 1);
	//		String mPin_btn2 = record[2].getContents().substring(1, 2);
	//		String mPin_btn3 = record[2].getContents().substring(2, 3);
	//		String mPin_btn4 = record[2].getContents().substring(3, 4);
	//
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn1).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn2).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn3).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn4).click(); //testing server password
	//		driver.findElementByName("layout_mpin_entry_btnDone").click();//tap on tick button
	//	}
	//
	//
	//
	//	public static void invalidmPinEntry(String username) throws BiffException, IOException{//Update this method based on your app context. This is just for code reference
	//
	//		// Fetching the mPin of the mentioned user from the excel
	//		Cell[] record = MobileCommonMethods.mobileReadExcel("InvalidLogin",username);
	//		String mPin_btn1 = record[2].getContents().substring(0, 1);
	//		String mPin_btn2 = record[2].getContents().substring(1, 2);
	//		String mPin_btn3 = record[2].getContents().substring(2, 3);
	//		String mPin_btn4 = record[2].getContents().substring(3, 4);
	//
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn1).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn2).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn3).click();	//testing server password
	//		driver.findElementByName("layout_mpin_entry_btnNum"+mPin_btn4).click(); //testing server password
	//		driver.findElementByName("layout_mpin_entry_btnDone").click();//tap on tick button
	//	}
	//
	//	public static void launchRBL() throws BiffException, IOException, InterruptedException {
	//		System.out.println("Launching RBL....");
	//		
	//		WebDriverWait wait = new WebDriverWait(driver, 35); 
	//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fragment_welcome_textViewWelcomeScreenGreeting")));
	//		driver.swipe(0, 300, 700, 300, 300); // To swipe the screen horizontally
	//	//	Thread.sleep(3000);
	//	//	MobileCommonMethods.swipe(0.4, 0.8, 0.4, 0.4, 700, 1);
	//		driver.findElementByName(SigninPageObjects.button_RBL).click(); //select partner
	//		//driver.findElementByName(SigninPageObjects.button_RBL_MoneyTransfer).click(); //select partner
	//		System.out.println("Launched RBL....");
	//	}
	//	





}


